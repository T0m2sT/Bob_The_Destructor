package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;

import java.io.IOException;

public class ViewerProvider {
    private final PlayerViewer playerViewer;

    public ViewerProvider(SpriteLoader spriteLoader) throws IOException {
        this.playerViewer = new PlayerViewer(spriteLoader);
    }

    public PlayerViewer getPlayerViewer() {
        return playerViewer;
    }
}
