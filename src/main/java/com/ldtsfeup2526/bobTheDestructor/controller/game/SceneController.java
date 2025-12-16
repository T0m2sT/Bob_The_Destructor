package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.game.elements.MineralsController;
import com.ldtsfeup2526.bobTheDestructor.controller.game.elements.PlayerController;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import javax.sound.sampled.FloatControl;
import java.io.IOException;
import java.util.List;

public class SceneController extends Controller<SceneManager> {
    private final PlayerController playerController;
    private final MineralsController mineralsController;
    int walkTimer = 0;

    public SceneController(SceneManager sceneManager) {
        super(sceneManager);
        this.playerController = new PlayerController(getModel().getScene().getPlayerModel());
        this.mineralsController = new MineralsController(getModel().getScene().getMineralModels());

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
        getModel().update(game);

        if (actions.contains(Action.QUIT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
            if (getModel().getScene().getSoundPlayer().getSound() != null) getModel().getScene().getSoundPlayer().stop();
        }
        if (actions.contains(Action.JUMP)) getModel().getScene().getJumpingSoundPlayer().start();
        if (actions.contains(Action.MINE)) getModel().getScene().getMiningSoundPlayer().start();
        if (actions.contains(Action.LEFT) || actions.contains(Action.RIGHT)) {
            if (walkTimer % 14 == 0) getModel().getScene().getWalkingSoundPlayer().start();
            walkTimer++;
        }
        playerController.update(game, actions);

        mineralsController.update(getModel().getScene().getPlayerModel().getPosition(), actions);
    }
}
