package com.ldtsfeup2526.bobTheDestructor.view.text;

import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;

public class StringParser extends FontParser {
    public StringParser(SpriteLoader spriteLoader, String font, int distanceBetweenChars) throws IOException {
        super(spriteLoader, font, "_-:!?.()[]|+=/0123456789abcdefghijklmnopqrstuvwxyz", distanceBetweenChars);
    }
}
