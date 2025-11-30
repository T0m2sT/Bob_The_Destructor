package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.MineralModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MineralModelTest {
    private MineralModel mineral;

    @BeforeEach
    void setUp() {
        mineral = new MineralModel(new Position(1,2), MineralModel.Type.COAL);
    }

    @Test
    void mineralGetPositionTest() {
        assert mineral.getPosition().getX() == 1;
        assert mineral.getPosition().getY() == 2;
    }

    @Test
    void mineralSetPositionTest() {
        mineral.setPosition(new Position(3,4));
        assert mineral.getPosition().getX() == 3;
        assert mineral.getPosition().getY() == 4;
    }

    @Test
    void mineralGetType() {
        assert mineral.getType() == MineralModel.Type.COAL;
    }

    @Test
    void mineralGetValue() {
        assert mineral.getValue() == 1;
    }
}
