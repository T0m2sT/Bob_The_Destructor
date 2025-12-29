package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class CreditsViewer extends ScreenViewer<Credits> {
    private final TitleViewer titleViewer;
    private final WallpaperViewer wallpaperViewer;

    public CreditsViewer(Credits model, ViewerProvider viewerProvider) {
        super(model);
        titleViewer = viewerProvider.getTitleViewer();
        wallpaperViewer = viewerProvider.getWallpaperViewer();
    }

    @Override
    public void draw(GUI gui, double deltaTime) throws IOException {

        gui.drawBackground(new TextColor.RGB(57, 53, 74));

        wallpaperViewer.draw(gui);

        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, 10), "credits", gui);

        titleViewer.drawAtTextStart(new Position(10, Game.resolution.height()/2 - 25), getModel().getCredits()[0], gui);
        titleViewer.drawAtTextStart(new Position(35, Game.resolution.height()/2 - 15), getModel().getCredits()[1], gui);
        titleViewer.drawAtTextStart(new Position(35, Game.resolution.height()/2 - 5), getModel().getCredits()[2], gui);
        titleViewer.drawAtTextStart(new Position(35, Game.resolution.height()/2 + 5), getModel().getCredits()[3], gui);
        titleViewer.drawAtTextStart(new Position(10, Game.resolution.height()/2 + 15), getModel().getCredits()[4], gui);
        titleViewer.drawAtTextStart(new Position(35, Game.resolution.height()/2 + 25), getModel().getCredits()[5], gui);

        gui.refresh();
    }
}
