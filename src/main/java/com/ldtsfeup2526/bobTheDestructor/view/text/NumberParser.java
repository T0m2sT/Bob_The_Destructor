package com.ldtsfeup2526.bobTheDestructor.view.text;

import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;

public class NumberParser extends FontParser {

    public NumberParser(SpriteLoader spriteLoader, String font, int distanceBetweenChars) throws IOException {
        super(spriteLoader, font, "0123456789", distanceBetweenChars);
    }
}
