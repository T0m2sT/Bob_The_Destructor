package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneBuilder;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import com.ldtsfeup2526.bobTheDestructor.model.stats.Stats;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.states.EndState;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SceneController extends Controller<SceneManager> implements PickaxeHitEventListener, MineralBreakEventListener {
    private final PlayerController playerController;
    private final SceneBuilder sceneBuilder;
    private final SoundManager soundManager;

    public SceneController(SceneManager sceneManager, SceneBuilder sceneBuilder, SoundManager soundManager) throws IOException {
        super(sceneManager);
        sceneManager.setScene(sceneBuilder.createScene(sceneManager.getNextCavePath(), new PlayerModel(new Position(0, 0))));
        this.sceneBuilder = sceneBuilder;
        this.playerController = new PlayerController(getModel().getScene().getPlayerModel(), soundManager);
        this.soundManager = soundManager;
        getModel().getScene().getPlayerModel().addPickaxeHitEventListener(this);
        addListenersToMinerals();
    }

    @Override
    public void update(Game game, List<Action> actions, double deltaTime) throws IOException {
        updateSceneState(game, actions);

        playerController.positionCorrection(getModel().getScene());
        playerController.update(game, actions, deltaTime);
        playerController.physicsUpdate(getModel().getScene());

        updateMining();

        getModel().updateTime(deltaTime);
    }

    @Override
    public void onPickaxeHit(PlayerModel playerModel) {
        PlayerState state = playerModel.getState();
        if (state.getMineral() != null) {
            state.getMineral().setState(MineralState.DESTROYED);
            getModel().getScene().incrementCurrentMineralsCollected();
        } else if (playerModel.getMineralSelected() != null) {
            playerModel.getMineralSelected().setState(MineralState.DESTROYED);
            getModel().getScene().incrementCurrentMineralsCollected();
        }
        soundManager.playSFX("sounds/soundEffects/mining.wav");
    }

    public void updateMining() {
        getModel().getScene().cleanupMinerals();
        playerController.getModel().updateSelectedMineral(getModel().getScene().getMineralModels());
        getModel().getScene().unselectAllMinerals();
        if (playerController.getModel().getMineralSelected() != null) {
            playerController.getModel().getMineralSelected().setState(MineralState.SELECTED);
        }
    }

    public void updateSceneState(Game game, List<Action> actions) throws IOException {
        if (actions.contains(Action.QUIT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader(), game.getSoundManager()));
        }

        if (getModel().getScene().getPlayerModel().getPosition().getY() > Game.resolution.height()) {
            getModel().updateTotalMineralsCollected();
            String path = getModel().getNextCavePath();

            if (Objects.isNull(path)) {
                game.setState(new EndState(new Stats(getModel()), game.getSpriteLoader(), soundManager));
                return;
            }
            getModel().setScene(sceneBuilder.createScene(path, getModel().getScene().getPlayerModel()));
            addListenersToMinerals();
        }
    }

    @Override
    public void onMineralBreak(MineralModel mineralModel) {
        mineralModel.setState(MineralState.CLEANUP);
    }

    public void addListenersToMinerals() {
        for (MineralModel mineralModel : getModel().getScene().getMineralModels()) {
            mineralModel.addMineralBreakEventListener(this);
        }
    }
}
