package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;
import java.util.List;

public class MainMenuViewer extends ScreenViewer<MainMenu>{

    private final ButtonViewer buttonViewer;
    private final WallpaperViewer wallpaperViewer;

    public MainMenuViewer(MainMenu model, ViewerProvider viewerProvider) {
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

    private <T extends ElementModel> void drawElement(GUI gui, T element, ElementViewer<T> viewer) {
        viewer.draw(element, gui);
    }

    private <T extends ElementModel> void drawElements(GUI gui, List<T> elements, ElementViewer<T> viewer) {
        for (T element: elements)
            drawElement(gui, element, viewer);
    }
}
