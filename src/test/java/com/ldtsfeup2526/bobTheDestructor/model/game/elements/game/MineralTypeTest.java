package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralTypeTest {
    @Test
    void testEnumValues() {
        assertEquals(3, MineralType.values().length);
        assertEquals(MineralType.PINK, MineralType.valueOf("PINK"));
        assertEquals(MineralType.BLUE, MineralType.valueOf("BLUE"));
        assertEquals(MineralType.YELLOW, MineralType.valueOf("YELLOW"));
    }
}
