package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.io.IOException;
import java.util.List;

public class SettingsMenuController extends MenuController<SettingsMenu> {

    public SettingsMenuController(SettingsMenu menu, WidgetController widgetController) {
        super(menu, widgetController);
    }

    @Override
    protected void onQuit(Game game) throws IOException {
        game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader(), game.getSoundManager()));
    }
}
