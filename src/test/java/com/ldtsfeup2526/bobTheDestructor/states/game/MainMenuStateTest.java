package com.ldtsfeup2526.bobTheDestructor.states.game;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.menu.MainMenuController;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.MainMenuViewer;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
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

        when(mainMenu.getSoundPlayer()).thenReturn(new NullSoundPlayer());

        spriteLoader = mock(SpriteLoader.class);

        when(spriteLoader.get(anyString()))
                .thenAnswer(inv -> new Sprite(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));

        mainMenuState = new MainMenuState(mainMenu, spriteLoader);
    }

    @Test
    void getModelTest() {
        assertEquals(mainMenu, mainMenuState.getModel());
    }

    @Test
    void createScreenViewerTest() {
        ViewerProvider viewerProvider = mock(ViewerProvider.class);
        when(viewerProvider.getButtonViewer()).thenReturn(mock(ButtonViewer.class));
        when(viewerProvider.getWallpaperViewer()).thenReturn(mock(WallpaperViewer.class));

        ScreenViewer<MainMenu> viewer = mainMenuState.createScreenViewer(viewerProvider);

        assertNotNull(viewer);
        assertInstanceOf(MainMenuViewer.class, viewer);
        assertEquals(mainMenu, viewer.getModel());
    }

    @Test
    void createControllerTest() {
        Controller<MainMenu> controller = mainMenuState.createController();

        assertNotNull(controller);
        assertInstanceOf(MainMenuController.class, controller);
        assertSame(mainMenu, controller.getModel());
    }
}
