package com.ldtsfeup2526.bobTheDestructor.view.sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpriteLoaderTest {
    @Test
    void testInterface() {
        SpriteLoader loader = new SpriteLoader() {
            @Override
            public Sprite get(String spriteFilePath) throws IOException { return null; }
            @Override
            public BufferedImage getBufferedImage(String spriteFilePath) throws IOException { return null; }
        };
        assertNotNull(loader);
    }
}
