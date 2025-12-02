package com.ldtsfeup2526.bobTheDestructor.view.elements;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class PlayerViewerIdle implements PlayerViewer {
    private final Map<String, Sprite> spriteMap;

    public PlayerViewerIdle(SpriteLoader spriteLoader) throws IOException {
        spriteMap = new HashMap<>();
        spriteMap.put("sprites/player/player1.png", spriteLoader.get("sprites/player/player1.png"));
    }
    @Override public void draw(Position pos, GUI gui) {
        Sprite sprite = spriteMap.get("sprites/player/player1.png");
        sprite.draw(gui, pos);
    }
}
