package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.text.StringParser;

import java.io.IOException;
import java.util.List;

public class TitleViewer {
    private final StringParser stringParser;

    public TitleViewer(SpriteLoader spriteLoader) throws IOException {
        this.stringParser = new StringParser(spriteLoader, "sprites/ui/letters/", 5);
    }

    public void draw(Position position, String string, GUI gui) {
        List<SpriteInstance> text = stringParser.get(string);
        int halfWidth = text.size() * 5/2;

        Position startTextPos = new Position(position.getX() - halfWidth, position.getY());
        for (SpriteInstance spriteInstance : text) {
            spriteInstance.sprite().setOffset(spriteInstance.offset());
            spriteInstance.sprite().draw(startTextPos, gui);
        }
    }
}
