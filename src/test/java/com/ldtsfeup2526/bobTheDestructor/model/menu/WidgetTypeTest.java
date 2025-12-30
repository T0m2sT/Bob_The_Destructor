package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WidgetTypeTest {
    @Test
    void testEnumValues() {
        assertEquals(8, WidgetType.values().length);
        assertNotNull(WidgetType.valueOf("PLAY"));
        assertNotNull(WidgetType.valueOf("CONFIG"));
        assertNotNull(WidgetType.valueOf("CREDITS"));
        assertNotNull(WidgetType.valueOf("EXIT"));
        assertNotNull(WidgetType.valueOf("MASTER_VOLUME"));
        assertNotNull(WidgetType.valueOf("MUSIC_VOLUME"));
        assertNotNull(WidgetType.valueOf("SFX_VOLUME"));
        assertNotNull(WidgetType.valueOf("NONE"));
    }
}