package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class SettingsMenuViewer extends ScreenViewer<SettingsMenu> {
    private final ButtonViewer buttonViewer;
    private final WallpaperViewer wallpaperViewer;

    public SettingsMenuViewer(SettingsMenu model, ViewerProvider viewerProvider) {
        super(model);
        this.buttonViewer = viewerProvider.getButtonViewer();
        this.wallpaperViewer = viewerProvider.getWallpaperViewer();
    }

    public void draw(GUI gui) throws IOException {
        gui.clear();
        wallpaperViewer.draw(gui);
        drawElements(gui, getModel().getButtons(), buttonViewer);
        gui.refresh();
    }
}
