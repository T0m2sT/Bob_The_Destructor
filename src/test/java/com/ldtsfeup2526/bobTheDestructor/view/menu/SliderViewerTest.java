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

import java.io.IOException;

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
        Sprite letterSprite = mock(Sprite.class);
        
        when(localLoader.get(argThat(s -> s != null && s.contains("arrow")))).thenReturn(arrowSprite);
        when(localLoader.get(argThat(s -> s != null && s.contains("letters")))).thenReturn(letterSprite);
        
        SliderViewer localViewer = new SliderViewer(localLoader);
        
        // Master Volume (13 chars: "MASTER VOLUME")
        // halfWidth = 13 * 5 / 2 = 32
        // startTextPos = (100 - 32, 50) = (68, 50)
        // leftArrowPos = (100 - 32 - 7, 50 + 2) = (61, 52)
        // rightArrowPos = (100 + 32 + 2, 50 + 2) = (134, 52)
        
        // Value 50 (2 chars: "50")
        // halfValueTextWidth = 2 * 5 / 2 = 5
        // startValueTextPos = (100 - 5, 50 + 7) = (95, 57)
        
        GameSettings.getInstance().setMasterVolume(0.5f);
        Widget wMaster = new Widget(WidgetType.MASTER_VOLUME, WidgetState.SELECTED, widgetPos);
        localViewer.draw(wMaster, gui, 0.1);
        
        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 68 && p.getY() == 50), eq(gui));
        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 95 && p.getY() == 57), eq(gui));
        verify(letterSprite, atLeastOnce()).setOffset(any());
        verify(arrowSprite).draw(argThat(p -> p.getX() == 61 && p.getY() == 52), eq(gui));
        verify(arrowSprite).drawFlipX(argThat(p -> p.getX() == 134 && p.getY() == 52), eq(gui));
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

        // Music Volume 0.75 -> 75
        GameSettings.getInstance().setMusicVolume(0.75f);
        Widget wMusic = new Widget(WidgetType.MUSIC_VOLUME, WidgetState.UNSELECTED, widgetPos);
        localViewer.draw(wMusic, gui, 0.1);
        // Verify rounding: 75 has 2 digits
        // Music Volume (12 chars: "MUSIC VOLUME")
        // halfWidth = 12 * 5 / 2 = 30
        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 100 - 30 && p.getY() == 50), eq(gui));

        // SFX Volume 0.123 -> 12
        GameSettings.getInstance().setSfxVolume(0.123f);
        Widget wSfx = new Widget(WidgetType.SFX_VOLUME, WidgetState.UNSELECTED, widgetPos);
        localViewer.draw(wSfx, gui, 0.1);
        // SFX Volume (10 chars: "SFX VOLUME")
        // halfWidth = 10 * 5 / 2 = 25
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
        
        // value should be -1 -> rounded to -100
        // "-100" has 4 characters
        // halfValueTextWidth = 4 * 5 / 2 = 10
        // startValueTextPos = 100 - 10 = 90
        verify(letterSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 90 && p.getY() == 57), eq(gui));
    }
}
