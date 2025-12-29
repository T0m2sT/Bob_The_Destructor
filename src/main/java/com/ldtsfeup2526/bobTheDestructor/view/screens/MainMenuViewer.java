package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class MainMenuViewer extends ScreenViewer<MainMenu>{

    private final ButtonViewer buttonViewer;
    private final WallpaperViewer wallpaperViewer;

    public MainMenuViewer(MainMenu model, ViewerProvider viewerProvider) {
        super(model);
        this.buttonViewer = viewerProvider.getButtonViewer();
        this.wallpaperViewer = viewerProvider.getWallpaperViewer();
    }

    public void draw(GUI gui, double deltaTime) throws IOException {
        //gui.clear();

        gui.drawBackground(new TextColor.RGB(57, 53, 74));
        wallpaperViewer.draw(gui);
        drawElements(gui, getModel().getWidgets(), buttonViewer, deltaTime);

        gui.refresh();
    }

}
