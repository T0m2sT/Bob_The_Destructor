package com.ldtsfeup2526.bobTheDestructor.controller.end;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.stats.Stats;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.io.IOException;
import java.util.List;

public class EndController extends Controller<Stats> {
    public EndController(Stats model) {
        super(model);
    }

    @Override
    public void update(Game game, List<Action> actions, double deltaTime) throws IOException {
        if (actions.contains(Action.QUIT)) {
            game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader(), game.getSoundManager()));
        }
    }
}
