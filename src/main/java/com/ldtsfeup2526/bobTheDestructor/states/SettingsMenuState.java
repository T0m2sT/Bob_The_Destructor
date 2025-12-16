package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.SettingsMenuController;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.SettingsMenuViewer;

import java.io.IOException;

public class SettingsMenuState extends State<SettingsMenu> {
    public SettingsMenuState(SettingsMenu model, com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader spriteLoader) throws IOException {
        super(model, spriteLoader);
    }

    @Override
    public ScreenViewer<SettingsMenu> createScreenViewer(ViewerProvider viewerProvider) {
        return new SettingsMenuViewer(getModel(), viewerProvider);
    }

    @Override
    public Controller<SettingsMenu> createController() {
        return new SettingsMenuController(getModel());
    }
}
