package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.MinerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinerModelTest {
    private MinerModel miner;

    @BeforeEach
    void setUp() {
        miner = new MinerModel(new Position(1,2));
    }

    @Test
    void minerGetPositionTest() {
        assert miner.getPosition().getX() == 1;
        assert miner.getPosition().getY() == 2;
    }

    @Test
    void minerSetPositionTest() {
        miner.setPosition(new Position(3,4));
        assert miner.getPosition().getX() == 3;
        assert miner.getPosition().getY() == 4;
    }

    @Test
    void minerMoveLeftTest() {
        miner.moveLeft();
        assert miner.getPosition().getX() == 0;
    }

    @Test
    void minerMoveRightTest() {
        miner.moveRight();
        assert miner.getPosition().getX() == 2;
    }

    @Test
    void minerMoveUpTest() {
        miner.moveUp();
        assert miner.getPosition().getY() == 3;
    }

    @Test
    void minerMoveDownTest() {
        miner.moveDown();
        assert miner.getPosition().getY() == 1;
    }
}
