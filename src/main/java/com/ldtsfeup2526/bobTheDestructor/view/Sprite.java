package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;

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

    public Size getSize() {
        return new Size(image.getWidth(), image.getHeight());
    }

    public void center() {
        setOffset(new Position(-getSize().getX()/2, -getSize().getY()/2));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Position position, GUI gui) {
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

    public void drawFlipX(Position position, GUI gui) {
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

    public void drawFlipY(Position position, GUI gui) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int ARGB = image.getRGB(x, image.getHeight() - y - 1);

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

    public void drawRotRight(Position position, GUI gui) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);

                if (getAlpha(argb) != 0) {
                    Position pixelPosition = new Position(
                            position.getX() + offset.getX() + (height - 1 - y),
                            position.getY() + offset.getY() + x
                    );

                    gui.drawPixel(pixelPosition, getRGB(argb));
                }
            }
        }
    }

    public void drawRotLeft(Position position, GUI gui) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);

                if (getAlpha(argb) != 0) {
                    Position pixelPosition = new Position(
                            position.getX() + offset.getX() + y,
                            position.getY() + offset.getY() + (width - 1 - x)
                    );

                    gui.drawPixel(pixelPosition, getRGB(argb));
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
