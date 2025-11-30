package com.ldtsfeup2526.bobTheDestructor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private Position p;

    @BeforeEach
    void setUp() {
        p = new Position(1, 2);
    }

    @Test
    void getTest() {
        assertEquals(1, p.getX());
        assertEquals(2, p.getY());
    }

    @Test
    void getLeftTest() {
        assertEquals(0, p.getLeft().getX());
        assertEquals(2, p.getLeft().getY());
    }

    @Test
    void getRightTest() {
        assertEquals(2, p.getRight().getX());
        assertEquals(2, p.getRight().getY());
    }

    @Test
    void getUpTest() {
        assertEquals(1, p.getUp().getX());
        assertEquals(1, p.getUp().getY());
    }

    @Test
    void getDownTest() {
        assertEquals(1, p.getDown().getX());
        assertEquals(3, p.getDown().getY());
    }

    @Test
    void equalsTest() {
        Position first_position = new Position(2, 3);
        Position second_position = new Position(1, 2);

        assertEquals(p, p);
        assertNotEquals(p, first_position);
        assertEquals(p, second_position);
        assertNotEquals(null, p);
    }
}
