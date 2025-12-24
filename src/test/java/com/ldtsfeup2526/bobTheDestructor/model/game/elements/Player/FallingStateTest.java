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

public class FallingStateTest {
    private PlayerModel player;
    private RigidBody rb;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        rb = mock(RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getAcceleration()).thenReturn(new Vector(0, 0));
        when(rb.getGravity()).thenReturn(0.5f);
    }

    @Test
    void testConstructorSetsAcceleration() {
        new FallingState(player);
        verify(rb).setAcceleration(argThat(v -> v.getY() == 0.5f));
    }

    @Test
    void testGetNextStateStillFalling() {
        FallingState state = new FallingState(player);
        when(player.isGrounded()).thenReturn(false);

        PlayerState next = state.getNextState();
        assertSame(state, next);
    }

    @Test
    void testGetNextStateLands() {
        FallingState state = new FallingState(player);
        when(player.isGrounded()).thenReturn(true);

        PlayerState next = state.getNextState();
        assertInstanceOf(IdleState.class, next);
    }

    @Test
    void testGetMineral() {
        FallingState state = new FallingState(player);
        assertNull(state.getMineral());
    }
}
