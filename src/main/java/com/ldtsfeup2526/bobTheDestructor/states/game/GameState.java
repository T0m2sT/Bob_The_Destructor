package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.game.SceneController;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.GameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class GameState extends State<SceneManager> {
    public GameState(SceneManager model, SpriteLoader spriteLoader) throws IOException {
        super(model, spriteLoader);
    }

    public ScreenViewer<SceneManager> createScreenViewer(ViewerProvider viewerProvider) {
        return new GameViewer(getModel(), viewerProvider);
    }

    public Controller<SceneManager> createController() {
        return new SceneController(getModel());
    }
}
