package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.GameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class GameState extends State<Scene> {
    public GameState(Scene model, SpriteLoader spriteLoader) throws IOException {
        super(model, spriteLoader);
    }

    public ScreenViewer<Scene> createScreenViewer(ViewerProvider viewerProvider) {
        return new GameViewer(getModel(), viewerProvider);
    }

    public Controller<Scene> createController() {
        return null;
    }
}
