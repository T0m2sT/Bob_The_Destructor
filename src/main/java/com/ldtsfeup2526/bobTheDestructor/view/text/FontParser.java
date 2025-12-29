package com.ldtsfeup2526.bobTheDestructor.view.text;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontParser {
    private final Map<Character, Sprite> spriteMap = new HashMap<>();
    private final int distanceBetweenChars;
    private final SpriteLoader spriteLoader;

    public FontParser(SpriteLoader spriteLoader, String fontPath, String characters, int distanceBetweenChars) throws IOException {
        this.distanceBetweenChars = distanceBetweenChars;
        this.spriteLoader = spriteLoader;

        for (int i = 0; i < characters.length(); i++) {
            char c = characters.charAt(i);
            spriteMap.put(c, spriteLoader.get(fontPath + c + ".png"));
        }
    }

    public List<SpriteInstance> get(String string) {
        List<SpriteInstance> sprites = new ArrayList<>();
        for(int i = 0; i < string.length(); i++) {
            char c = string.toLowerCase().charAt(i);
            Sprite sprite = spriteMap.get(c);
            sprites.add(new SpriteInstance(sprite, new Position(distanceBetweenChars * i, 0)));
        }
        return sprites;
    }

    public void add(char character, String spriteFilePath) throws IOException {
        spriteMap.put(character, spriteLoader.get(spriteFilePath));
    }
}
