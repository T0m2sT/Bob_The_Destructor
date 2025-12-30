package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SettingsMenuTest {
    private SettingsMenu settingsMenu;

    @BeforeEach
    void setUp() {
        settingsMenu = new SettingsMenu();
    }

    @Test
    void testCreateWidgets() {
        List<Widget> widgets = settingsMenu.getWidgets();
        assertEquals(3, widgets.size());
        assertEquals(WidgetType.MASTER_VOLUME, widgets.get(0).getWidgetType());
        assertEquals(WidgetState.SELECTED, widgets.get(0).getWidgetState());
    }

    @Test
    void testMoveDown() {
        settingsMenu.moveDown();
        assertEquals(WidgetType.MUSIC_VOLUME, settingsMenu.getCurrentWidget().getWidgetType());
    }
}
