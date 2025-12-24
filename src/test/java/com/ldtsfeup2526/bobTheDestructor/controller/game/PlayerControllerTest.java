package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;

public class PlayerControllerTest {
    private PlayerModel player;
    private PlayerController controller;
    private Game game;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        controller = new PlayerController(player);
        game = mock(Game.class);
        when(player.getState()).thenReturn(new IdleState(player));
    }

    @Test
    void testMoveRight() {
        controller.update(game, List.of(Action.RIGHT));
        verify(player).moveRight();
        verify(player).update();
    }

    @Test
    void testMoveLeft() {
        controller.update(game, List.of(Action.LEFT));
        verify(player).moveLeft();
        verify(player).update();
    }

    @Test
    void testJump() {
        controller.update(game, List.of(Action.JUMP));
        verify(player).jump();
    }

    @Test
    void testMine() {
        controller.update(game, List.of(Action.MINE));
        verify(player).mine();
    }

    @Test
    void testJumpUp() {
        controller.update(game, List.of(Action.UP));
        verify(player).jump();
    }

    @Test
    void testMineWhenAlreadyMining() {
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState miningState = mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState.class);
        when(player.getState()).thenReturn(miningState);
        controller.update(game, List.of(Action.MINE));
        verify(player, never()).mine();
    }

    @Test
    void testNoApplyFrictionWhenMoving() {
        controller.update(game, List.of(Action.RIGHT));
        verify(player, never()).applyFriction();
        
        controller.update(game, List.of(Action.LEFT));
        verify(player, never()).applyFriction();
    }

    @Test
    void testApplyFriction() {
        controller.update(game, List.of());
        verify(player).applyFriction();
    }
}
