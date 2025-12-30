package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    void testConstructorAndGetters() {
        Position pos = new Position(10, 20);
        assertEquals(10, pos.getX());
        assertEquals(20, pos.getY());
    }

    @Test
    void testCopyConstructor() {
        Position pos1 = new Position(10, 20);
        Position pos2 = new Position(pos1);
        assertEquals(10, pos2.getX());
        assertEquals(20, pos2.getY());
    }

    @Test
    void testMagnitude() {
        Position pos = new Position(3, 4);
        assertEquals(5.0, pos.magnitude(), 0.0001);
    }

    @Test
    void testPrint() {
        Position pos = new Position(10, 20);
        pos.print();
    }

    @Test
    void testConstructorWithNumberTypes() {
        Position pos1 = new Position(10.5, 20.7);
        assertEquals(10, pos1.getX());
        assertEquals(20, pos1.getY());
        
        Position pos2 = new Position(10L, 20L);
        assertEquals(10, pos2.getX());
        assertEquals(20, pos2.getY());
    }
}
