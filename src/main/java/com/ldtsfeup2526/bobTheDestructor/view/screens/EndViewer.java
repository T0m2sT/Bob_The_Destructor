package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.stats.Stats;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class EndViewer extends ScreenViewer<Stats> {
    private final WallpaperViewer wallpaperViewer;
    private final TitleViewer titleViewer;

    public EndViewer(Stats model, ViewerProvider viewerProvider) {
        super(model);
        wallpaperViewer = viewerProvider.getWallpaperViewer();
        titleViewer = viewerProvider.getTitleViewer();
    }

    @Override
    public void draw(GUI gui, double deltaTime) throws IOException {
        gui.drawBackground(new TextColor.RGB(57, 53, 74));

        wallpaperViewer.draw(gui);

        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, 10), "Thank you for playing!", gui);

        gui.refresh();
    }
}
