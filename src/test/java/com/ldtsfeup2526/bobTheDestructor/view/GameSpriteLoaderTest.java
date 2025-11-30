package com.ldtsfeup2526.bobTheDestructor.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GameSpriteLoaderTest {
    private GameSpriteLoader loader;
    private String p1;
    private String p2;

    @BeforeEach
    void setUp() {
        loader = new GameSpriteLoader();

        p1 = "sprites/player/player1.png";
        p2 = "sprites/tiles/tile1.png";
    }

    @Test
    void getTest() throws IOException {
        Sprite s1 = loader.get(p1);
        Sprite s2 = loader.get(p1);

        assert s1 != null;
        assert s1 == s2;

        // testing that the map contains only one entry
        assert loader.spriteMap.size() == 1;
        assert loader.spriteMap.containsKey(p1);
    }

    @Test
    void differentPathsTest() throws IOException {
        Sprite s1 = loader.get(p1);
        Sprite s2 = loader.get(p2);

        assert s1 != s2;

        assert loader.spriteMap.size() >= 2;
        assert loader.spriteMap.containsKey(p1);
        assert loader.spriteMap.containsKey(p2);
    }

    @Test
    void missingResourceThrows() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        boolean threw = false;
        try {
            loader.get("sprites/does/not/exist.png");
        } catch (NullPointerException e) {
            threw = true; // requireNonNull(resource) should throw
        }
        assert threw;
    }
}
