package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;

public class WallpaperViewer {
    private final Sprite sprite;

    public WallpaperViewer(SpriteLoader spriteLoader) throws IOException {
        this.sprite = spriteLoader.get("sprites/ui/main_menu_bg.png");
        this.sprite.center();
    }

    public void draw(GUI gui) {
        sprite.draw(new Position(Game.resolution.width()/2, Game.resolution.height()/2), gui);
    }
}
