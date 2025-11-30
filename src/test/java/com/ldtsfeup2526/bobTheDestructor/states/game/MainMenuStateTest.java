package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MainMenuStateTest {
    private MainMenuState mainMenuState;
    private MainMenu mainMenu;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        mainMenu = mock(MainMenu.class);
        spriteLoader = mock(SpriteLoader.class);

        when(spriteLoader.get(anyString())).thenReturn(mock(Sprite.class));

        mainMenuState = new MainMenuState(mainMenu, spriteLoader);
    }

    @Test
    void getModelTest() {
        assertEquals(mainMenu, mainMenuState.getModel());
    }

    @Test
    void createScreenViewerTest() {
        ViewerProvider viewerProvider = mock(ViewerProvider.class);
        ScreenViewer<MainMenu> viewer = mainMenuState.createScreenViewer(viewerProvider);

        assertNull(viewer);
    }

    @Test
    void createControllerTest() {
        Controller<MainMenu> controller = mainMenuState.createController();

        assertNull(controller);
    }
}
