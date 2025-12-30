package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Widget;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetState;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import java.util.List;
import java.util.Objects;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SliderViewerTest {
    private SpriteLoader spriteLoader;
    private SliderViewer viewer;
    private Sprite mockSprite;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        viewer = new SliderViewer(spriteLoader);
    }

    @Test
    void testDrawPrecisePositions() throws IOException {
        GUI gui = mock(GUI.class);
        Position widgetPos = new Position(100, 50);
        
        SpriteLoader localLoader = mock(SpriteLoader.class);
        Sprite arrowSprite = mock(Sprite.class);
        
        List<Sprite> letterSprites = new java.util.ArrayList<>();
        when(localLoader.get(argThat(s -> s != null && s.contains("letters")))).thenAnswer(invocation -> {
            Sprite s = mock(Sprite.class);
            letterSprites.add(s);
            return s;
        });
        when(localLoader.get(argThat(s -> s != null && s.contains("arrow")))).thenReturn(arrowSprite);
        
        SliderViewer localViewer = new SliderViewer(localLoader);
        
        GameSettings.getInstance().setMasterVolume(0.5f);
        Widget wMaster = new Widget(WidgetType.MASTER_VOLUME, WidgetState.SELECTED, widgetPos);
        localViewer.draw(wMaster, gui, 0.1);
        
        for (Sprite s : letterSprites) {
            boolean drawn = false;
            try {
                verify(s, atLeastOnce()).draw(any(), eq(gui));
                drawn = true;
            } catch (org.mockito.exceptions.verification.WantedButNotInvoked e) {
            }
            
            if (drawn) {
                verify(s, atLeastOnce()).setOffset(any());
            }
        }

        verify(arrowSprite, atLeastOnce()).draw(argThat(p -> p != null && p.getX() == 61 && p.getY() == 52), eq(gui));

        verify(arrowSprite, atLeastOnce()).drawFlipX(argThat(p -> p != null && p.getX() == 134 && p.getY() == 52), eq(gui));
    }

    @Test
    void testDrawAllVolumeTypesRounding() throws IOException {
        GUI gui = mock(GUI.class);
        Position widgetPos = new Position(100, 50);
        
        SpriteLoader localLoader = mock(SpriteLoader.class);
        Sprite arrowSprite = mock(Sprite.class);
        Sprite letterSprite = mock(Sprite.class);
        when(localLoader.get(anyString())).thenReturn(letterSprite);
        when(localLoader.get(contains("arrow"))).thenReturn(arrowSprite);
        
        SliderViewer localViewer = new SliderViewer(localLoader);

        GameSettings.getInstance().setMusicVolume(0.75f);
        Widget wMusic = new Widget(WidgetType.MUSIC_VOLUME, WidgetState.UNSELECTED, widgetPos);
        localViewer.draw(wMusic, gui, 0.1);

        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 100 - 30 && p.getY() == 50), eq(gui));

        GameSettings.getInstance().setSfxVolume(0.123f);
        Widget wSfx = new Widget(WidgetType.SFX_VOLUME, WidgetState.UNSELECTED, widgetPos);
        localViewer.draw(wSfx, gui, 0.1);

        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 100 - 25 && p.getY() == 50), eq(gui));
    }
    @Test
    void testDrawDefaultType() throws IOException {
        GUI gui = mock(GUI.class);
        Position widgetPos = new Position(100, 50);
        
        SpriteLoader localLoader = mock(SpriteLoader.class);
        Sprite arrowSprite = mock(Sprite.class);
        Sprite letterSprite = mock(Sprite.class);
        when(localLoader.get(anyString())).thenReturn(letterSprite);
        when(localLoader.get(contains("arrow"))).thenReturn(arrowSprite);
        
        SliderViewer localViewer = new SliderViewer(localLoader);

        Widget wDefault = new Widget(WidgetType.PLAY, WidgetState.UNSELECTED, widgetPos);
        localViewer.draw(wDefault, gui, 0.1);

        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 90 && p.getY() == 57), eq(gui));
    }
}
