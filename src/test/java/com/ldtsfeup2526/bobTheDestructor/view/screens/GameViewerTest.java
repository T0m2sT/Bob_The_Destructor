package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class GameViewerTest {
    private GameViewer gameViewer;
    private Scene scene;
    private ViewerProvider viewerProvider;
    private PlayerViewer playerViewer;

    @BeforeEach
    void setUp() {
        scene = mock(Scene.class);
        viewerProvider = mock(ViewerProvider.class);
        playerViewer = mock(PlayerViewer.class);

        when(viewerProvider.getPlayerViewer()).thenReturn(playerViewer);

        gameViewer = new GameViewer(scene, viewerProvider);
    }

    @Test
    void drawTest() throws IOException {
        GUI gui = mock(GUI.class);

        gameViewer.draw(gui);

        verify(gui, times(1)).clear();
        
        verify(playerViewer, times(1)).draw(, new Position(50, 50), gui);
        
        verify(gui, times(1)).refresh();
    }
}
