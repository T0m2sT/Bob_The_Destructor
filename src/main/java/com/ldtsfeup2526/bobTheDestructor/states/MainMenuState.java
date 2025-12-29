package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.MainMenuController;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.WidgetController;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.MainMenuViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class MainMenuState extends State<MainMenu> {
    public MainMenuState(MainMenu model, SpriteLoader spriteLoader, SoundManager soundManager) throws IOException {
        super(model, spriteLoader, soundManager);
    }

    public ScreenViewer<MainMenu> createScreenViewer(ViewerProvider viewerProvider) {
        return new MainMenuViewer(getModel(), viewerProvider);
    }

    public Controller<MainMenu> createController() {
        return new MainMenuController(getModel(), new WidgetController(getModel(), soundManager));
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
