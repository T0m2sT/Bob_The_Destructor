package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(0, sprite.getOffset().getX());
        assertEquals(0, sprite.getOffset().getY());
    }

    @Test
    void setOffsetTest() {
        sprite.setOffset(new Position(1, 2));
        assertEquals(1, sprite.getOffset().getX());
        assertEquals(2, sprite.getOffset().getY());
    }

    @Test
    void drawTest() {
        GUI gui = mock(GUI.class);
        sprite.setOffset(new Position(1, 1));
        Position base = new Position(5, 10);

        sprite.draw(base, gui);

        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        verify(gui, times(3)).drawPixel(posCaptor.capture(), any(TextColor.class));

        List<Position> actual = posCaptor.getAllValues();
        assertEquals(3, actual.size());

        Set<String> coords = new HashSet<>();
        for (Position p : actual) coords.add(p.getX() + "," + p.getY());

        assertTrue(coords.contains("7,11"));
        assertTrue(coords.contains("6,12"));
        assertTrue(coords.contains("7,12"));
    }

    @Test
    void drawFlipXTest() {
        GUI gui = mock(GUI.class);
        sprite.setOffset(new Position(1, 1));
        Position base = new Position(5, 10);

        sprite.drawFlipX(base, gui);

        ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
        verify(gui, times(3)).drawPixel(posCaptor.capture(), any(TextColor.class));

        List<Position> actual = posCaptor.getAllValues();
        assertEquals(3, actual.size());

        Set<String> coords = new HashSet<>();
        for (Position p : actual) coords.add(p.getX() + "," + p.getY());

        assertTrue(coords.contains("6,11"));
        assertTrue(coords.contains("6,12"));
        assertTrue(coords.contains("7,12"));
    }
}
