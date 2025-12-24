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

public class JumpingStateTest {
    private PlayerModel player;
    private RigidBody rb;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        rb = mock(RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getAcceleration()).thenReturn(new Vector(0, 0));
        when(rb.getGravity()).thenReturn(0.5f);
        when(rb.getVelocity()).thenReturn(new Vector(0, -1));
    }

    @Test
    void testConstructorSetsAcceleration() {
        new JumpingState(player);
        verify(rb).setAcceleration(argThat(v -> v.getY() == 0.25f));
    }

    @Test
    void testGetNextStateStillJumping() {
        JumpingState state = new JumpingState(player);
        Collider col = mock(Collider.class);
        when(player.getCollider()).thenReturn(col);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(col.colPosCheck(any())).thenReturn(mock(Collider.class));
        Scene scene = mock(Scene.class);
        when(player.getScene()).thenReturn(scene);
        when(scene.checkCollision(any())).thenReturn(false);
        when(rb.getVelocity()).thenReturn(new Vector(0, -0.5f));

        PlayerState next = state.getNextState();
        assertSame(state, next);
    }

    @Test
    void testGetNextStateToFallingBoundary() {
        JumpingState state = new JumpingState(player);
        // > 0.5
        when(rb.getVelocity()).thenReturn(new Vector(0, 0.501f));
        assertInstanceOf(FallingState.class, state.getNextState());
        
        when(rb.getVelocity()).thenReturn(new Vector(0, 0.499f));
        // Also need to mock collision to not land
        Collider col = mock(Collider.class);
        when(player.getCollider()).thenReturn(col);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(col.colPosCheck(any())).thenReturn(mock(Collider.class));
        Scene scene = mock(Scene.class);
        when(player.getScene()).thenReturn(scene);
        when(scene.checkCollision(any())).thenReturn(false);
        assertSame(state, state.getNextState());
    }

    @Test
    void testGetNextStateLands() {
        JumpingState state = new JumpingState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0, 0.1f));
        Collider col = mock(Collider.class);
        when(player.getCollider()).thenReturn(col);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(col.colPosCheck(any())).thenReturn(mock(Collider.class));
        Scene scene = mock(Scene.class);
        when(player.getScene()).thenReturn(scene);
        when(scene.checkCollision(any())).thenReturn(true);

        PlayerState next = state.getNextState();
        assertInstanceOf(IdleState.class, next);
    }

    @Test
    void testGetMineral() {
        JumpingState state = new JumpingState(player);
        assertNull(state.getMineral());
    }
}
