package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.end.EndController;
import com.ldtsfeup2526.bobTheDestructor.model.stats.Stats;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.EndViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class EndState extends State<Stats> {
    public EndState(Stats model, SpriteLoader spriteLoader, SoundManager soundManager) throws IOException {
        super(model, spriteLoader, soundManager);
    }

    @Override
    public ScreenViewer<Stats> createScreenViewer(ViewerProvider viewerProvider) throws IOException {
        return new EndViewer(getModel(), viewerProvider);
    }

    @Override
    public Controller<Stats> createController() throws IOException {
        return new EndController(getModel());
    }
}
