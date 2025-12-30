package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.game.MineralViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.OverlayViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameViewerTest {
    private GameViewer viewer;
    private SceneManager sceneManager;
    private ViewerProvider viewerProvider;

    @BeforeEach
    void setUp() throws IOException {
        sceneManager = mock(SceneManager.class);
        Scene scene = mock(Scene.class);
        when(sceneManager.getScene()).thenReturn(scene);
        
        List<com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel> minerals = new ArrayList<>();
        minerals.add(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel.class));
        when(scene.getMineralModels()).thenReturn(minerals);
        
        when(scene.getPlayerModel()).thenReturn(mock(PlayerModel.class));
        
        viewerProvider = mock(ViewerProvider.class);
        when(viewerProvider.getPlayerViewer()).thenReturn(mock(PlayerViewer.class));
        when(viewerProvider.getSceneViewer()).thenReturn(mock(SceneViewer.class));
        when(viewerProvider.getMineralViewer()).thenReturn(mock(MineralViewer.class));
        when(viewerProvider.getOverlayViewer()).thenReturn(mock(OverlayViewer.class));
        
        viewer = new GameViewer(sceneManager, viewerProvider);
    }

    @Test
    void testDraw() throws IOException {
        GUI gui = mock(GUI.class);
        viewer.draw(gui, 0.1);
        
        verify(gui).drawBackground(any());
        verify(viewerProvider.getSceneViewer()).draw(any(), eq(gui), anyDouble());
        verify(viewerProvider.getMineralViewer()).draw(any(), eq(gui), anyDouble());
        verify(viewerProvider.getPlayerViewer()).draw(any(), eq(gui), anyDouble());
        verify(viewerProvider.getOverlayViewer()).draw(any(), eq(gui), anyDouble());
        verify(gui).refresh();
    }

    @Test
    void testConstructorCallsRetrieveCaves() throws IOException {
        verify(viewerProvider.getSceneViewer()).retrieveCaves(anyList());
    }
    @Test
    void testDrawWithMineralsCleanup() throws IOException {
        GUI gui = mock(GUI.class);
        Scene scene = sceneManager.getScene();
        List<com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel> minerals = new ArrayList<>();
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel m1 = mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel.class);
        when(m1.getState()).thenReturn(com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState.CLEANUP);
        minerals.add(m1);
        when(scene.getMineralModels()).thenReturn(minerals);

        viewer.draw(gui, 0.1);

        verify(viewerProvider.getMineralViewer()).draw(eq(m1), eq(gui), anyDouble());
    }

    @Test
    void testDrawWithMineralsEmpty() throws IOException {
        GUI gui = mock(GUI.class);
        Scene scene = sceneManager.getScene();
        when(scene.getMineralModels()).thenReturn(new ArrayList<>());
        
        viewer.draw(gui, 0.1);
        verify(viewerProvider.getMineralViewer(), never()).draw(any(), any(), anyDouble());
    }
}
