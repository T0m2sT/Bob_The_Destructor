package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneBuilder;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import javax.sound.sampled.FloatControl;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SceneController extends Controller<SceneManager> implements PlayerMiningListener {
    private final PlayerController playerController;
    private final SceneBuilder sceneBuilder;

    public SceneController(SceneManager sceneManager, SceneBuilder sceneBuilder) throws IOException {
        super(sceneManager);
        sceneManager.setScene(sceneBuilder.createScene(sceneManager.getNextCavePath(), new PlayerModel(new Position(0, 0))));
        this.sceneBuilder = sceneBuilder;
        this.playerController = new PlayerController(getModel().getScene().getPlayerModel());
        getModel().getScene().getPlayerModel().addMiningListener(this);


        if (getModel().getScene().getSoundPlayer().getSound() != null) {
            if (getModel().getScene().getSoundPlayer().getSound().isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) getModel().getScene().getSoundPlayer().getSound().getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-5.0f + GameSettings.getInstance().getMasterGain());
            } else {
                System.err.println("VOLUME control not supported on this Clip.");
            }
            getModel().getScene().getSoundPlayer().start();
        }
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        updateSceneState(game, actions);

        playerController.update(game, actions);
        playerController.getModel().physicsUpdate(getModel().getScene());
        updateMining();


    }

    @Override
    public void onMiningFinished(PlayerModel playerModel) {
        PlayerState state = playerModel.getState();
        if (state.getMineral() != null) {
            state.getMineral().setState(MineralState.DESTROYED);
            getModel().getScene().incrementCurrentMineralsCollected();
        } else if (playerModel.getMineralSelected() != null) {
            playerModel.getMineralSelected().setState(MineralState.DESTROYED);
            getModel().getScene().incrementCurrentMineralsCollected();
        }
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
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
            if (getModel().getScene().getSoundPlayer().getSound() != null) getModel().getScene().getSoundPlayer().stop();
        }

        if (getModel().getScene().getPlayerModel().getPosition().getY() > Game.resolution.getHeight()) {
            getModel().updateTotalMineralsCollected();
            String path = getModel().getNextCavePath();

            if (Objects.isNull(path)) {
                game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
                return;
            }
            getModel().setScene(sceneBuilder.createScene(path, getModel().getScene().getPlayerModel()));
        }
    }

}
