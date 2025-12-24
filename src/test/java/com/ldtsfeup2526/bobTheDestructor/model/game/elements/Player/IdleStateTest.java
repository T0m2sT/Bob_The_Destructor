package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IdleStateTest {
    private PlayerModel player;
    private RigidBody rb;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        rb = mock(RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getVelocity()).thenReturn(new Vector(0, 0));
        when(rb.getAcceleration()).thenReturn(new Vector(0, 0));
        when(rb.getGravity()).thenReturn(0.4f);
    }

    @Test
    void testGetNextStateStillIdle() {
        IdleState state = new IdleState(player);
        when(player.isGrounded()).thenReturn(true);

        PlayerState next = state.getNextState();
        assertSame(state, next);
    }

    @Test
    void testGetNextStateToWalking() {
        IdleState state = new IdleState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0));

        PlayerState next = state.getNextState();
        assertInstanceOf(WalkingState.class, next);
    }

    @Test
    void testGetNextStateToJumping() {
        IdleState state = new IdleState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0, -1));

        PlayerState next = state.getNextState();
        assertInstanceOf(JumpingState.class, next);
    }

    @Test
    void testGetNextStateToWalkingBoundary() {
        IdleState state = new IdleState(player);
        // boundary > 0
        when(rb.getVelocity()).thenReturn(new Vector(0.0001f, 0));
        assertInstanceOf(WalkingState.class, state.getNextState());
        
        when(rb.getVelocity()).thenReturn(new Vector(-0.0001f, 0));
        assertInstanceOf(WalkingState.class, state.getNextState());
    }

    @Test
    void testGetNextStateToJumpingBoundary() {
        IdleState state = new IdleState(player);
        // boundary < 0
        when(rb.getVelocity()).thenReturn(new Vector(0, -0.0001f));
        assertInstanceOf(JumpingState.class, state.getNextState());
        
        when(rb.getVelocity()).thenReturn(new Vector(0, 0)); // Still idle
        when(player.isGrounded()).thenReturn(true);
        assertSame(state, state.getNextState());
    }

    @Test
    void testGetMineral() {
        IdleState state = new IdleState(player);
        assertNull(state.getMineral());
    }
}
