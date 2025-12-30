package com.ldtsfeup2526.bobTheDestructor.view.sprite;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameSpriteLoaderTest {
    @Test
    void testGetSpriteNotFound() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        assertNull(loader.get("non-existent.png"));
    }

    @Test
    void testGetBufferedImageNotFound() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        assertNull(loader.getBufferedImage("non-existent.png"));
    }

    @Test
    void testGetAndCache() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        String path = "background/bg1.png";
        Sprite sprite1 = loader.get(path);
        assertNotNull(sprite1);
        
        Sprite sprite2 = loader.get(path);
        assertSame(sprite1, sprite2, "Should return cached sprite");
    }

    @Test
    void testCrossMethodCaching() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        String path = "background/bg1.png";
        
        // Load via get() first
        Sprite sprite = loader.get(path);
        assertNotNull(sprite);
        
        // Then getBufferedImage() should return the same image
        BufferedImage img = loader.getBufferedImage(path);
        assertSame(sprite.getImage(), img);
    }
    @Test
    void testGetBufferedImageAndCache() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        String path = "background/bg1.png";
        BufferedImage img1 = loader.getBufferedImage(path);
        assertNotNull(img1);
        
        BufferedImage img2 = loader.getBufferedImage(path);
        assertSame(img1, img2, "Should return cached image");
    }
}
