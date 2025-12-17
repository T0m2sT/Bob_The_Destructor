package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneBuilder;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Menu;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.game.SettingsMenuState;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

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
                        SpriteLoader spriteLoader = game.getSpriteLoader();
                        game.setState(new GameState(new SceneManager(new SceneBuilder(spriteLoader)), spriteLoader));
                        getModel().getSoundPlayer().stop();
                    }
                    break;
                case CONFIG:
                    if (action == Action.SELECT) {
                        SpriteLoader spriteLoader = game.getSpriteLoader();
                        game.setState(new SettingsMenuState(new SettingsMenu(), spriteLoader));
                        getModel().getSoundPlayer().stop();
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
                case VOLUME_UP:
                    if (action == Action.SELECT) {
                        GameSettings.getInstance().setMasterGain(GameSettings.getInstance().getMasterGain() + 10.0f);
                    }
                    break;
                case VOLUME_DOWN:
                    if (action == Action.SELECT) {
                        GameSettings.getInstance().setMasterGain(GameSettings.getInstance().getMasterGain() - 10.0f);
                    }
                    break;
            }
        }
    }
}
