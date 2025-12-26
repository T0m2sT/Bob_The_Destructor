package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.io.IOException;
import java.util.List;

public class SettingsMenuController extends MenuController<SettingsMenu> {
    public SettingsMenuController(SettingsMenu menu) {
        super(menu);
    }

    @Override
    protected void onQuit(Game game) {
        try {
            game.setState(new MainMenuState(new com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu(), game.getSpriteLoader()));
            getModel().getSoundPlayer().stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
