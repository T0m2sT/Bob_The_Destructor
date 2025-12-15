package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.game.elements.MineralsController;
import com.ldtsfeup2526.bobTheDestructor.controller.game.elements.PlayerController;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.game.MainMenuState;

import java.io.IOException;
import java.util.List;

public class SceneController extends Controller<SceneManager> {
    private final PlayerController playerController;
    //private final MineralsController mineralsController;

    public SceneController(SceneManager sceneManager) {
        super(sceneManager);
        this.playerController = new PlayerController(getModel().getScene().getPlayerModel());
        //this.mineralsController = new MineralsController(getModel().getScene().getMineralsModel());
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        if (actions.contains(Action.QUIT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
            getModel().getScene().getSoundPlayer().stop();
        }
        playerController.update(game, actions);

        //mineralsController.update(getModel().getScene().getPlayerModel().getPosition(), actions);
    }
}
