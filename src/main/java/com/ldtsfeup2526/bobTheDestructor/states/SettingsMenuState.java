package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.SettingsMenuController;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.WidgetController;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.SettingsMenuViewer;

import java.io.IOException;

public class SettingsMenuState extends State<SettingsMenu> {
    public SettingsMenuState(SettingsMenu model, SpriteLoader spriteLoader, SoundManager soundManager) throws IOException {
        super(model, spriteLoader, soundManager);
    }

    @Override
    public ScreenViewer<SettingsMenu> createScreenViewer(ViewerProvider viewerProvider) {
        return new SettingsMenuViewer(getModel(), viewerProvider);
    }

    @Override
    public Controller<SettingsMenu> createController() {
        return new SettingsMenuController(getModel(), new WidgetController(getModel(), soundManager));
    }

    @Override
    public void onEnter(Game game) {
        game.getSoundManager().playMusic("sounds/mainMenuSoundtrack.wav");
    }

    @Override
    public void onExit(Game game) {
        game.getSoundManager().stopMusic();
    }
}
