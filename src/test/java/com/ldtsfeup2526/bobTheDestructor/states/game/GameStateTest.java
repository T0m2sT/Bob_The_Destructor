package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.game.SceneController;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.game.MineralViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.GameViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GameStateTest {
    private GameState gameState;
    private SceneManager sceneManager;
    private Scene scene;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        sceneManager = mock(SceneManager.class);
        scene = mock(Scene.class);

        when(sceneManager.getScene()).thenReturn(scene);

        when(scene.getSoundPlayer()).thenReturn(new NullSoundPlayer());

        when(scene.getMineralModels()).thenReturn(List.of());
        when(scene.getPlayerModel()).thenReturn(mock(PlayerModel.class));

        spriteLoader = mock(SpriteLoader.class);

        when(spriteLoader.get(anyString()))
                .thenAnswer(inv -> new Sprite(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));

        gameState = new GameState(sceneManager, spriteLoader);
    }

    @Test
    void createScreenViewerTest() throws IOException {
        ViewerProvider viewerProvider = mock(ViewerProvider.class);

        SceneViewer sceneViewer = mock(SceneViewer.class);
        doNothing().when(sceneViewer).retrieveCaves(any());

        when(viewerProvider.getPlayerViewer()).thenReturn(mock(PlayerViewer.class));
        when(viewerProvider.getSceneViewer()).thenReturn(sceneViewer);
        when(viewerProvider.getMineralViewer()).thenReturn(mock(MineralViewer.class));

        ScreenViewer<SceneManager> viewer = gameState.createScreenViewer(viewerProvider);

        assertNotNull(viewer);
        assertInstanceOf(GameViewer.class, viewer);
        assertEquals(sceneManager, viewer.getModel());
    }

    @org.junit.jupiter.api.Test
    void createControllerTest() throws IOException {
        Controller<SceneManager> controller = gameState.createController();

        assertNotNull(controller);
        assertInstanceOf(SceneController.class, controller);
        assertSame(sceneManager, controller.getModel());
    }
}
