package com.ldtsfeup2526.bobTheDestructor.view.sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface SpriteLoader {
    Sprite get(String spriteFilePath) throws IOException;
    BufferedImage getBufferedImage(String spriteFilePath) throws IOException;
}
