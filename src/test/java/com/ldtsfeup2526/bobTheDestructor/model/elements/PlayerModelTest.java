package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerModelTest {
    private PlayerModel player;

    @BeforeEach
    void setUp() {
        player = new PlayerModel(new Position(1,2));
    }

    @Test
    void playerGetPositionTest() {
        assert player.getPosition().getX() == 1;
        assert player.getPosition().getY() == 2;
    }

    @Test
    void playerSetPositionTest() {
        player.setPosition(new Position(3,4));
        assert player.getPosition().getX() == 3;
        assert player.getPosition().getY() == 4;
    }

    @Test
    void playerMoveLeftTest() {
        player.moveLeft();
        assert player.getPosition().getX() == 0;
    }

    @Test
    void playerMoveRightTest() {
        player.moveRight();
        assert player.getPosition().getX() == 2;
    }

    @Test
    void playerMoveUpTest() {
        player.moveUp();
        assert player.getPosition().getY() == 1;
    }

    @Test
    void playerMoveDownTest() {
        player.moveDown();
        assert player.getPosition().getY() == 3;
    }
}
