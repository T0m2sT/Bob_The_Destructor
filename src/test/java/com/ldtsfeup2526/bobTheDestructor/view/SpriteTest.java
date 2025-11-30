package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SpriteTest {
    private Sprite sprite;

    @BeforeEach
    void setUp() {
        BufferedImage image2x2 = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);

        // (0,0) transparent
        image2x2.setRGB(0, 0, 0x00000000);
        // (1,0) opaque red
        image2x2.setRGB(1, 0, 0xFFFF0000);
        // (0,1) opaque green
        image2x2.setRGB(0, 1, 0xFF00FF00);
        // (1,1) opaque blue
        image2x2.setRGB(1, 1, 0xFF0000FF);

        sprite = new Sprite(image2x2);
    }

    @Test
    void getOffsetTest() {
        assert sprite.getOffset().getX() == 0;
        assert sprite.getOffset().getY() == 0;
    }

    @Test
    void setOffsetTest() {
        sprite.setOffset(new Position(1, 2));
        assert sprite.getOffset().getX() == 1;
        assert sprite.getOffset().getY() == 2;
    }

    @Test
    void drawTest() {
        GUI gui = mock(GUI.class);
        sprite.setOffset(new Position(1, 1));
        Position base = new Position(5, 10);

        sprite.draw(gui, base);

        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        verify(gui, times(3)).drawPixel(posCaptor.capture(), any(TextColor.class));

        List<Position> expected = new ArrayList<>();
        expected.add(new Position(7, 11));
        expected.add(new Position(6, 12));
        expected.add(new Position(7, 12));

        List<Position> actual = posCaptor.getAllValues();
        assert actual.size() == 3;
        for (Position pos : expected) {
            assert actual.contains(pos);
        }
    }

    @Test
    void drawFlipXTest() {
        GUI gui = mock(GUI.class);
        sprite.setOffset(new Position(1, 1));
        Position base = new Position(5, 10);

        sprite.drawFlipX(gui, base);

        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        verify(gui, times(3)).drawPixel(posCaptor.capture(), any(TextColor.class));

        List<Position> expected = new ArrayList<>();
        expected.add(new Position(6, 11));
        expected.add(new Position(6, 12));
        expected.add(new Position(7, 12));

        List<Position> actual = posCaptor.getAllValues();
        assert actual.size() == 3;
        for (Position pos : expected) {
            assert actual.contains(pos);
        }
    }
}
