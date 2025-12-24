package com.ldtsfeup2526.bobTheDestructor.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolutionTest {
    @Test
    void testGetters() {
        Resolution res = new Resolution(100, 200);
        assertEquals(100, res.width());
        assertEquals(200, res.height());
    }
}
