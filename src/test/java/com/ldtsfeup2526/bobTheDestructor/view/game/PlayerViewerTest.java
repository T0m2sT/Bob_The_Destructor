package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlayerViewerTest {
    private PlayerViewer playerViewer;
    private SpriteLoader spriteLoader;
    private Sprite sprite;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        sprite = mock(Sprite.class);

        when(spriteLoader.get(anyString())).thenReturn(sprite);

        playerViewer = new PlayerViewer(spriteLoader);
    }

    @Test
    void drawTest() {
        GUI gui = mock(GUI.class);
        Position position = new Position(10, 20);
        PlayerModel player = new PlayerModel(position);

        Scene scene = new Scene("caves/cave0/", player, List.of());
        scene.setBlockColliders(List.of());

        playerViewer.draw(player, gui, 0.016);

        verify(sprite, atLeastOnce()).draw(eq(position), eq(gui));
    }

    @Test
    void initializationLoadsSpritesTest() throws IOException {
        verify(spriteLoader, atLeastOnce()).get("sprites/player/player1.png");
    }
}
