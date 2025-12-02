package com.ldtsfeup2526.bobTheDestructor.view.elements;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlayerViewerTest {
    private PlayerViewer playerViewer;
    private SpriteLoader spriteLoader;
    private Sprite playerSprite;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        playerSprite = mock(Sprite.class);

        when(spriteLoader.get("sprites/player/player1.png")).thenReturn(playerSprite);

        playerViewer = new PlayerViewer(spriteLoader);
    }

    @Test
    void drawTest() {
        GUI gui = mock(GUI.class);
        Position position = new Position(10, 20);

        playerViewer.draw(, position, gui);

        verify(playerSprite, times(1)).draw(eq(gui), eq(position));
    }
    
    @Test
    void initializationLoadsSpriteTest() throws IOException {
        verify(spriteLoader, times(1)).get("sprites/player/player1.png");
    }
}
