package com.ldtsfeup2526.bobTheDestructor.view.text;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberParser {
    private final Map<Character, Sprite> spriteMap = new HashMap<>();
    private final int distanceBetweenChars;

    public NumberParser(SpriteLoader spriteLoader, String font) throws IOException {
        this(spriteLoader, font, 5);
    }

    public NumberParser(SpriteLoader spriteLoader, String font, int distanceBetweenChars) throws IOException {
        this.distanceBetweenChars = distanceBetweenChars;
        for (int i = 0; i < 10; i++) {
            spriteMap.put(String.valueOf(i).charAt(0), spriteLoader.get(font + "num" + String.valueOf(i) + ".png"));
        }
    }

    public List<SpriteInstance> get(String string) {
        List<SpriteInstance> sprites = new ArrayList<>();
        for(int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            Sprite sprite = spriteMap.get(c);
            sprites.add(new SpriteInstance(sprite, new Position(distanceBetweenChars * i, 0)));
        }
        return sprites;
    }
}
