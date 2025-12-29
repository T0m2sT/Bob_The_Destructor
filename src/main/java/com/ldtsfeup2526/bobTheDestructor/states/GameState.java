package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.game.SceneController;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneBuilder;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.GameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class GameState extends State<SceneManager> {
    public GameState(SceneManager model, SpriteLoader spriteLoader, SoundManager soundManager) throws IOException {
        super(model, spriteLoader, soundManager);
    }

    public ScreenViewer<SceneManager> createScreenViewer(ViewerProvider viewerProvider) throws IOException {
        return new GameViewer(getModel(), viewerProvider);
    }

    public Controller<SceneManager> createController() throws IOException {
        return new SceneController(getModel(), new SceneBuilder(spriteLoader), soundManager);
    }

    @Override
    public void onEnter(Game game) {
        game.getSoundManager().playMusic("sounds/gameSoundtrack.wav");
    }

    @Override
    public void onExit(Game game) {
        game.getSoundManager().stopMusic();
    }
}
