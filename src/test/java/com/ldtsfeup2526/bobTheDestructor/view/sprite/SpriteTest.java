package com.ldtsfeup2526.bobTheDestructor.view.sprite;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpriteTest {
    private boolean compareColors(TextColor color, int r, int g, int b) {
        if (!(color instanceof TextColor.RGB)) return false;
        TextColor.RGB rgb = (TextColor.RGB) color;
        return rgb.getRed() == r && rgb.getGreen() == g && rgb.getBlue() == b;
    }
    @Test
    void testOffsetAndSize() {
        BufferedImage image = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
        Sprite sprite = new Sprite(image);
        
        assertEquals(10, sprite.getSize().getX());
        assertEquals(20, sprite.getSize().getY());
        
        sprite.setOffset(new Position(5, 5));
        assertEquals(5, sprite.getOffset().getX());
        
        sprite.center();
        assertEquals(-5, sprite.getOffset().getX());
        assertEquals(-10, sprite.getOffset().getY());

        assertEquals(image, sprite.getImage());
    }

    @Test
    void testDraw() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 10 && pos.getY() == 10), any());
        
        image.setRGB(0, 0, 0x00FF0000);
        reset(gui);
        sprite.draw(new Position(10, 10), gui);
        verify(gui, never()).drawPixel(any(), any());
    }

    @Test
    void testDrawFlipX() {
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 0, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawFlipX(new Position(0, 0), gui);

        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 0, 255, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 1 && pos.getY() == 0), argThat(color -> compareColors(color, 255, 0, 0)));
    }

    @Test
    void testDrawFlipY() {
        BufferedImage image = new BufferedImage(1, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(0, 1, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawFlipY(new Position(0, 0), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 0, 255, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 1), argThat(color -> compareColors(color, 255, 0, 0)));
    }

    @Test
    void testDrawRotRight() {
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 0, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawRotRight(new Position(0, 0), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 255, 0, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 1), argThat(color -> compareColors(color, 0, 255, 0)));
    }

    @Test
    void testDrawRotLeft() {
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 0, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawRotLeft(new Position(0, 0), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 1), argThat(color -> compareColors(color, 255, 0, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 0, 255, 0)));
    }

    @Test
    void testDrawWithOffset() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 12 && pos.getY() == 13), any());
    }

    @Test
    void testDrawWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 1, 0xFFFF0000); 
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawFlipXWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 1, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawFlipX(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawFlipYWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 0, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawFlipY(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawRotRightWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 1, 0xFFFF0000); 
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawRotRight(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawRotRightDetailed() {
        // 3x2 image
        // (0,0): R, (1,0): G, (2,0): B
        // (0,1): C, (1,1): M, (2,1): Y
        BufferedImage image = new BufferedImage(3, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000); // R
        image.setRGB(1, 0, 0xFF00FF00); // G
        image.setRGB(2, 0, 0xFF0000FF); // B
        image.setRGB(0, 1, 0xFF00FFFF); // C
        image.setRGB(1, 1, 0xFFFF00FF); // M
        image.setRGB(2, 1, 0xFFFFFF00); // Y
        
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);
        sprite.drawRotRight(new Position(10, 10), gui);
        
        // height = 2, width = 3, heightMinusOne = 1
        // (x,y) -> (10 + 1 - y, 10 + x)
        // (0,0) -> (11, 10) R
        // (1,0) -> (11, 11) G
        // (2,0) -> (11, 12) B
        // (0,1) -> (10, 10) C
        // (1,1) -> (10, 11) M
        // (2,1) -> (10, 12) Y
        
        verify(gui).drawPixel(argThat(p -> p.getX() == 11 && p.getY() == 10), argThat(c -> compareColors(c, 255, 0, 0)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 11 && p.getY() == 11), argThat(c -> compareColors(c, 0, 255, 0)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 11 && p.getY() == 12), argThat(c -> compareColors(c, 0, 0, 255)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 10 && p.getY() == 10), argThat(c -> compareColors(c, 0, 255, 255)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 10 && p.getY() == 11), argThat(c -> compareColors(c, 255, 0, 255)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 10 && p.getY() == 12), argThat(c -> compareColors(c, 255, 255, 0)));
    }

    @Test
    void testDrawRotLeftDetailed() {
        // 3x2 image
        BufferedImage image = new BufferedImage(3, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000); // R
        image.setRGB(1, 0, 0xFF00FF00); // G
        image.setRGB(2, 0, 0xFF0000FF); // B
        image.setRGB(0, 1, 0xFF00FFFF); // C
        image.setRGB(1, 1, 0xFFFF00FF); // M
        image.setRGB(2, 1, 0xFFFFFF00); // Y
        
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);
        sprite.drawRotLeft(new Position(10, 10), gui);
        
        // width = 3, height = 2, widthMinusOne = 2
        // (x,y) -> (10 + y, 10 + 2 - x)
        // (0,0) -> (10, 12) R
        // (1,0) -> (10, 11) G
        // (2,0) -> (10, 10) B
        // (0,1) -> (11, 12) C
        // (1,1) -> (11, 11) M
        // (2,1) -> (11, 10) Y
        
        verify(gui).drawPixel(argThat(p -> p.getX() == 10 && p.getY() == 12), argThat(c -> compareColors(c, 255, 0, 0)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 10 && p.getY() == 11), argThat(c -> compareColors(c, 0, 255, 0)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 10 && p.getY() == 10), argThat(c -> compareColors(c, 0, 0, 255)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 11 && p.getY() == 12), argThat(c -> compareColors(c, 0, 255, 255)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 11 && p.getY() == 11), argThat(c -> compareColors(c, 255, 0, 255)));
        verify(gui).drawPixel(argThat(p -> p.getX() == 11 && p.getY() == 10), argThat(c -> compareColors(c, 255, 255, 0)));
    }
    @Test
    void testDrawWithTransparency() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        // Set one pixel to transparent (alpha 0)
        image.setRGB(0, 0, 0x00FFFFFF);
        // Set other pixel to opaque
        image.setRGB(1, 1, 0xFF0000FF);
        
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(0, 0), gui);
        verify(gui, never()).drawPixel(argThat(p -> p.getX() == 0 && p.getY() == 0), any());
        verify(gui).drawPixel(argThat(p -> p.getX() == 1 && p.getY() == 1), any());
        
        reset(gui);
        sprite.drawFlipX(new Position(0, 0), gui);
        // (1,1) flipped X is (0,1)
        verify(gui).drawPixel(argThat(p -> p.getX() == 0 && p.getY() == 1), any());
        
        reset(gui);
        sprite.drawFlipY(new Position(0, 0), gui);
        // (1,1) flipped Y is (1,0)
        verify(gui).drawPixel(argThat(p -> p.getX() == 1 && p.getY() == 0), any());
        
        reset(gui);
        sprite.drawRotRight(new Position(0, 0), gui);
        // (1,1) rotated right 90 deg: height-1-y, x = 2-1-1, 1 = 0, 1
        verify(gui).drawPixel(argThat(p -> p.getX() == 0 && p.getY() == 1), any());
        
        reset(gui);
        sprite.drawRotLeft(new Position(0, 0), gui);
        // (1,1) rotated left 90 deg: y, width-1-x = 1, 2-1-1 = 1, 0
        verify(gui).drawPixel(argThat(p -> p.getX() == 1 && p.getY() == 0), any());
    }
}
