package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ViewerProviderTest {
    private ViewerProvider viewerProvider;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        
        when(spriteLoader.get(anyString())).thenReturn(mock(Sprite.class));

        viewerProvider = new ViewerProvider(spriteLoader);
    }

    @Test
    void getPlayerViewerTest() {
        PlayerViewer playerViewer = viewerProvider.getPlayerViewer();
        assertNotNull(playerViewer);
    }
}
