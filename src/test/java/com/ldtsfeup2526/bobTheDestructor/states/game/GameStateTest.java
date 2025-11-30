package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.GameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GameStateTest {
    private GameState gameState;
    private Scene scene;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        scene = mock(Scene.class);
        spriteLoader = mock(SpriteLoader.class);

        when(spriteLoader.get(anyString())).thenReturn(mock(Sprite.class));

        gameState = new GameState(scene, spriteLoader);
    }

    @Test
    void createScreenViewerTest() {
        ViewerProvider viewerProvider = mock(ViewerProvider.class);

        when(viewerProvider.getPlayerViewer()).thenReturn(mock(PlayerViewer.class));

        ScreenViewer<Scene> viewer = gameState.createScreenViewer(viewerProvider);

        assertNotNull(viewer);
        assertInstanceOf(GameViewer.class, viewer);
        assertEquals(scene, viewer.getModel());
    }

    @Test
    void createControllerTest() {
        Controller<Scene> controller = gameState.createController();

        assertNull(controller);
    }
}
