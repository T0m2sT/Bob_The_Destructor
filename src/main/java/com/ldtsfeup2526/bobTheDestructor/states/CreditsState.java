package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.credits.CreditsController;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.CreditsViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;

import java.io.IOException;

public class CreditsState extends State<Credits> {
    public CreditsState(Credits model, SpriteLoader spriteLoader, SoundManager soundManager) throws IOException {
        super(model, spriteLoader, soundManager);
    }

    @Override
    public ScreenViewer<Credits> createScreenViewer(ViewerProvider viewerProvider) throws IOException {
        return new CreditsViewer(getModel(), viewerProvider);
    }

    @Override
    public Controller<Credits> createController() throws IOException {
        return new CreditsController(getModel());
    }
}
