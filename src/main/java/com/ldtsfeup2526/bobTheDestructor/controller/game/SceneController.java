package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import javax.sound.sampled.FloatControl;
import java.io.IOException;
import java.util.List;

public class SceneController extends Controller<SceneManager> implements PlayerMiningListener {
    private final PlayerController playerController;

    public SceneController(SceneManager sceneManager) {
        super(sceneManager);
        this.playerController = new PlayerController(getModel().getScene().getPlayerModel());
        getModel().getScene().getPlayerModel().addMiningListener(this);

        /* Not Working
        if (getModel().getScene().getSoundPlayer().getSound() != null) {
            if (getModel().getScene().getSoundPlayer().getSound().isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) getModel().getScene().getSoundPlayer().getSound().getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-5.0f + GameSettings.getInstance().getMasterGain());
            } else {
                System.err.println("VOLUME control not supported on this Clip.");
            }
            getModel().getScene().getSoundPlayer().start();
        }*/
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        getModel().update(game);

        if (actions.contains(Action.QUIT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
            if (getModel().getScene().getSoundPlayer().getSound() != null) getModel().getScene().getSoundPlayer().stop();
        }
        playerController.update(game, actions);

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
}
