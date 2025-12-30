package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    @Test
    void testMenuLogic() {
        Menu menu = new Menu() {
            @Override
            protected List<Widget> createWidgets() {
                List<Widget> widgets = new ArrayList<>();
                widgets.add(new Widget(WidgetType.PLAY, WidgetState.SELECTED, null));
                widgets.add(new Widget(WidgetType.EXIT, WidgetState.UNSELECTED, null));
                return widgets;
            }
        };

        assertEquals(2, menu.getNumberOfWidgets());
        assertEquals(WidgetType.PLAY, menu.getCurrentWidget().getWidgetType());
        
        menu.moveDown();
        assertEquals(WidgetType.EXIT, menu.getCurrentWidget().getWidgetType());
        
        menu.moveUp();
        assertEquals(WidgetType.PLAY, menu.getCurrentWidget().getWidgetType());
        
        assertNotNull(menu.getWidgets());
    }
}
