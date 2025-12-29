package com.ldtsfeup2526.bobTheDestructor.view.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameSpriteLoader implements SpriteLoader{
    private final Map<String, Sprite> spriteMap = new HashMap<>();

    @Override
    public Sprite get(String spriteFilePath) throws IOException {
        if (spriteMap.containsKey(spriteFilePath)) {
            return spriteMap.get(spriteFilePath);
        }

        URL resource = getClass().getClassLoader().getResource(spriteFilePath);
        BufferedImage image = ImageIO.read(Objects.requireNonNull(resource));

        Sprite sprite = new Sprite(image);
        spriteMap.put(spriteFilePath, sprite);
        return sprite;
    }

    public BufferedImage getBufferedImage(String spriteFilePath) throws IOException {
        if (spriteMap.containsKey(spriteFilePath)) {
            return spriteMap.get(spriteFilePath).getImage();
        }

        URL resource = getClass().getClassLoader().getResource(spriteFilePath);
        BufferedImage image = ImageIO.read(Objects.requireNonNull(resource));

        Sprite sprite = new Sprite(image);
        spriteMap.put(spriteFilePath, sprite);
        return image;
    }
}
