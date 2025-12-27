package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.MainMenuController;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.MainMenuViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class MainMenuState extends State<MainMenu> {
    public MainMenuState(MainMenu model, SpriteLoader spriteLoader) throws IOException {
        super(model, spriteLoader);
    }

    public ScreenViewer<MainMenu> createScreenViewer(ViewerProvider viewerProvider) {
        return new MainMenuViewer(getModel(), viewerProvider);
    }

    public Controller<MainMenu> createController() {
        return new MainMenuController(getModel());
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
