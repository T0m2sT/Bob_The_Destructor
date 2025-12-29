package com.ldtsfeup2526.bobTheDestructor.view.sprite;

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
        int posX = position.getX() + offset.getX();
        int posY = position.getY() + offset.getY();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int ARGB = image.getRGB(x, y);

                if (getAlpha(ARGB) != 0) {
                    Position pixelPosition = new Position(posX + x, posY + y);

                    gui.drawPixel(pixelPosition, getRGB(ARGB));
                }

            }
        }
    }

    public void drawFlipX(Position position, GUI gui) {
        int posX = position.getX() + offset.getX();
        int posY = position.getY() + offset.getY();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int ARGB = image.getRGB(image.getWidth()-x-1, y);

                if (getAlpha(ARGB) != 0) {
                    Position pixelPosition = new Position(posX + x, posY + y);

                    gui.drawPixel(pixelPosition, getRGB(ARGB));
                }

            }
        }
    }

    public void drawFlipY(Position position, GUI gui) {
        int posX = position.getX() + offset.getX();
        int posY = position.getY() + offset.getY();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int ARGB = image.getRGB(x, image.getHeight() - y - 1);

                if (getAlpha(ARGB) != 0) {
                    Position pixelPosition = new Position(posX + x, posY + y);

                    gui.drawPixel(pixelPosition, getRGB(ARGB));
                }

            }
        }
    }

    public void drawRotRight(Position position, GUI gui) {
        int width = image.getWidth();
        int height = image.getHeight();
        int posX = position.getX() + offset.getX();
        int posY = position.getY() + offset.getY();

        int heightMinusOne = height - 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);

                if (getAlpha(argb) != 0) {
                    Position pixelPosition = new Position(
                            posX + heightMinusOne - y,
                            posY + x
                    );

                    gui.drawPixel(pixelPosition, getRGB(argb));
                }
            }
        }
    }

    public void drawRotLeft(Position position, GUI gui) {
        int width = image.getWidth();
        int height = image.getHeight();
        int posX = position.getX() + offset.getX();
        int posY = position.getY() + offset.getY();

        int widthMinusOne = width - 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);

                if (getAlpha(argb) != 0) {
                    Position pixelPosition = new Position(
                            posX + y,
                            posY + widthMinusOne - x
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
