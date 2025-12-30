package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralStateTest {
    @Test
    void testEnumValues() {
        assertEquals(4, MineralState.values().length);
        assertEquals(MineralState.SELECTED, MineralState.valueOf("SELECTED"));
        assertEquals(MineralState.UNSELECTED, MineralState.valueOf("UNSELECTED"));
        assertEquals(MineralState.DESTROYED, MineralState.valueOf("DESTROYED"));
        assertEquals(MineralState.CLEANUP, MineralState.valueOf("CLEANUP"));
    }
}
