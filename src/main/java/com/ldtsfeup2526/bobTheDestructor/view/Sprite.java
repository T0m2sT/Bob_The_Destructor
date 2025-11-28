package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.awt.image.BufferedImage;

public class Sprite {
    private final BufferedImage image;
    private Position offset = new Position(0, 0);

    public Sprite(BufferedImage image) {
        this.image = image;
    }

    public Position getOffset() {
        return offset;
    }

    public void setOffset(Position offset) {
        this.offset = offset;
    }

    public void draw(GUI gui, Position position) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int ARGB = image.getRGB(x, y);

                if (getAlpha(ARGB) != 0) {
                    Position pixelPosition = new Position(
                            position.getX() + offset.getX() + x,
                            position.getY() + offset.getY() + y
                        );

                    gui.drawPixel(pixelPosition, getRGB(ARGB));
                }

            }
        }
    }

    public void drawFlipX(GUI gui, Position position) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int ARGB = image.getRGB(image.getWidth()-x-1, y);

                if (getAlpha(ARGB) != 0) {
                    Position pixelPosition = new Position(
                            position.getX() + offset.getX() + x,
                            position.getY() + offset.getY() + y
                    );

                    gui.drawPixel(pixelPosition, getRGB(ARGB));
                }

            }
        }
    }

    private int getAlpha(int ARGB) {
        return ARGB >> 24;
    }

    private TextColor getRGB(int ARGB) {
        int red = ARGB >> 16 & 0xFF;
        int green = ARGB >> 8 & 0xFF;
        int blue = ARGB & 0xFF;
        return new TextColor.RGB(red, green, blue);
    }

}
