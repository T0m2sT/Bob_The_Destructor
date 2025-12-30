package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;

public class LogoViewer {
    private final Sprite sprite;

    public LogoViewer(SpriteLoader spriteLoader) throws IOException {
        this.sprite = spriteLoader.get("");
        this.sprite.center();
    }

    public void draw(Position position, GUI gui) {
        sprite.draw(position, gui);
    }
}
