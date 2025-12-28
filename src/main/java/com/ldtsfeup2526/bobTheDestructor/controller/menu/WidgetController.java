package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Menu;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.SettingsMenuState;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.List;

public class WidgetController extends Controller<Menu> {
    public WidgetController(Menu menu) {
        super(menu);
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        for (Action action : actions) {
            switch (getModel().getCurrentWidget().getWidgetType()) {
                case WidgetType.PLAY:
                    if (action == Action.SELECT) {
                        SpriteLoader spriteLoader = game.getSpriteLoader();
                        game.setState(new GameState(new SceneManager(), spriteLoader, game.getSoundManager()));
                    }
                    break;
                case CONFIG:
                    if (action == Action.SELECT) {
                        SpriteLoader spriteLoader = game.getSpriteLoader();
                        game.setState(new SettingsMenuState(new SettingsMenu(), spriteLoader, game.getSoundManager()));
                    }
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
