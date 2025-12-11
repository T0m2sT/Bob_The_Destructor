package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class ViewerProvider {
    private final PlayerViewer playerViewer;
    private final ButtonViewer buttonViewer;
    private final WallpaperViewer wallpaperViewer;

    public ViewerProvider(SpriteLoader spriteLoader) throws IOException {
        this.playerViewer = new PlayerViewer(spriteLoader);
        this.buttonViewer = new ButtonViewer(spriteLoader);
        this.wallpaperViewer = new WallpaperViewer(spriteLoader);
    }

    public PlayerViewer getPlayerViewer() {
        return playerViewer;
    }

    public ButtonViewer getButtonViewer() {
        return buttonViewer;
    }

    public WallpaperViewer getWallpaperViewer() {
        return wallpaperViewer;
    }
}
