package com.ldtsfeup2526.bobTheDestructor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositionTest {
    Position p;

    @BeforeEach
    void setUp() {
        p = new Position(1, 2);
    }

    @Test
    void getTest() {
        assert p.getX() == 1;
        assert p.getY() == 2;
    }

    @Test
    void getLeftTest() {
        assert p.getLeft().getX() == 0;
    }

    @Test
    void getRightTest() {
        assert p.getRight().getX() == 2;
    }

    @Test
    void equalsTest() {
        Position a = new Position(2, 3);
        // reflexive property
        assert p.equals(p);
        // different coordinates are not equal
        assert !a.equals(p);
        // symmetric property
        assert a.equals(p) == p.equals(a);
        // null test
        assert !p.equals(null);
    }
}
