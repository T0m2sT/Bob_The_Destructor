package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerStateTest {
    private PlayerModel player;
    private PlayerState state;

    private static class TestPlayerState extends PlayerState {
        public TestPlayerState(PlayerModel playerModel) {
            super(playerModel);
        }
        @Override
        public PlayerState getNextState() { return this; }
        @Override
        public MineralModel getMineral() { return null; }
    }

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        RigidBody rb = mock(RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getVelocity()).thenReturn(new Vector(0, 0));
        
        state = new TestPlayerState(player);
    }

    @Test
    void testGetPlayerModel() {
        assertEquals(player, state.getPlayerModel());
    }

    @Test
    void testMovePlayerLeft() {
        state.movePlayerLeft();
        verify(player.getRigidBody()).moveLeft();
        verify(player).setLookRight(false);
    }

    @Test
    void testMovePlayerRight() {
        state.movePlayerRight();
        verify(player.getRigidBody()).moveRight();
        verify(player).setLookRight(true);
    }

    @Test
    void testJumpSuccess() {
        when(player.isGrounded()).thenReturn(true);
        when(player.getJumpForce()).thenReturn(2.6f);

        state.jump();

        verify(player.getRigidBody()).jump(2.6f);
    }

    @Test
    void testJumpFailure() {
        when(player.isGrounded()).thenReturn(false);

        state.jump();

        verify(player.getRigidBody(), never()).jump(anyFloat());
    }

    @Test
    void testApplyFriction() {
        state.applyFriction();
        verify(player.getRigidBody()).applyFriction();
    }
}
