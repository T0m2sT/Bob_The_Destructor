package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Menu;
import com.ldtsfeup2526.bobTheDestructor.states.game.GameState;

import java.io.IOException;
import java.util.List;

public class ButtonController extends Controller<Menu> {
    public ButtonController(Menu menu) {
        super(menu);
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        for (Action action : actions) {
            switch (getModel().getCurrentButton().getButtonType()) {
                case ButtonType.PLAY:
                    if (action == Action.SELECT) {
                        game.setState(new GameState(new Scene(), game.getSpriteLoader()));
                    }
                    break;
                case CONFIG:
                    // TODO
                    break;
                case CREDITS:
                    // TODO
                    break;
                case EXIT:
                    if (action == Action.SELECT) {
                        game.setState(null);
                    }
                    break;
            }
        }
    }
}
