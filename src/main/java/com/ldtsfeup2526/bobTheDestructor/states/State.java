package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public abstract class State<T> {
    private final T model;
    private final Controller<T> controller;
    private final ScreenViewer<T> screenViewer;
    protected final SpriteLoader spriteLoader;
    protected final SoundManager soundManager;

    public State(T model, SpriteLoader spriteLoader, SoundManager soundManager) throws IOException {
        this.model = model;
        this.spriteLoader = spriteLoader;
        this.soundManager = soundManager;
        this.screenViewer = createScreenViewer(new ViewerProvider(spriteLoader));
        this.controller = createController();
    }

    public abstract ScreenViewer<T> createScreenViewer(ViewerProvider viewerProvider) throws IOException;
    public abstract  Controller<T> createController() throws IOException;

    public T getModel() {
        return model;
    }

    public void update(Game game, GUI gui, ActionParser actionParser, double deltaTime) throws IOException {
        controller.update(game, actionParser.get(), deltaTime);
        screenViewer.draw(gui, deltaTime);
    }

    public void onEnter(Game game) {}
    public void onExit(Game game) {}
}
