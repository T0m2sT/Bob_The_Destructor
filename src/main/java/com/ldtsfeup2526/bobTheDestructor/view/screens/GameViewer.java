package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;

import java.io.IOException;

public class GameViewer extends ScreenViewer<Scene> {

    private final PlayerViewer playerViewer;

    public GameViewer(Scene model, ViewerProvider viewerProvider) {
        super(model);
        this.playerViewer = viewerProvider.getPlayerViewer();
    }

    @Override
    public void draw(GUI gui) throws IOException {
        gui.clear();
        gui.drawBackground(new TextColor.RGB(30, 30, 46));
        playerViewer.draw(new Position(50, 50), gui);

        gui.refresh();
    }
}
