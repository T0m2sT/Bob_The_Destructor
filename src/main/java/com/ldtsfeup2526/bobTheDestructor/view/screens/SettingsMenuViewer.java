package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.SliderViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class SettingsMenuViewer extends ScreenViewer<SettingsMenu> {
    private final SliderViewer sliderViewer;
    private final WallpaperViewer wallpaperViewer;
    private final TitleViewer titleViewer;

    public SettingsMenuViewer(SettingsMenu model, ViewerProvider viewerProvider) {
        super(model);
        this.sliderViewer = viewerProvider.getSliderViewer();
        this.wallpaperViewer = viewerProvider.getWallpaperViewer();
        this.titleViewer = viewerProvider.getTitleViewer();
    }

    public void draw(GUI gui, double deltaTime) throws IOException {
        //gui.clear();
        gui.drawBackground(new TextColor.RGB(57, 53, 74));
        wallpaperViewer.draw(gui);
        drawElements(gui, getModel().getWidgets(), sliderViewer, deltaTime);
        titleViewer.draw(new Position(Game.resolution.width()/2 + 1, 10), "settings", gui);

        gui.refresh();
    }
}
