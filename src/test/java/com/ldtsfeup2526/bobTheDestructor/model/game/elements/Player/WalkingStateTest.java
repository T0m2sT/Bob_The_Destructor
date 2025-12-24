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

public class WalkingStateTest {
    private PlayerModel player;
    private RigidBody rb;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        rb = mock(RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0));
        when(rb.getAcceleration()).thenReturn(new Vector(0, 0));
        when(rb.getGravity()).thenReturn(0.4f);
    }

    @Test
    void testGetNextStateStillWalking() {
        WalkingState state = new WalkingState(player);
        Collider col = mock(Collider.class);
        when(player.getCollider()).thenReturn(col);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(col.colPosCheck(any())).thenReturn(mock(Collider.class));
        Scene scene = mock(Scene.class);
        when(player.getScene()).thenReturn(scene);
        when(scene.checkCollision(any())).thenReturn(true);
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0));

        PlayerState next = state.getNextState();
        assertSame(state, next);
    }

    @Test
    void testGetNextStateToJumping() {
        WalkingState state = new WalkingState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0, -1));

        PlayerState next = state.getNextState();
        assertInstanceOf(JumpingState.class, next);
    }

    @Test
    void testGetNextStateToFalling() {
        WalkingState state = new WalkingState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0));
        Collider col = mock(Collider.class);
        when(player.getCollider()).thenReturn(col);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(col.colPosCheck(any())).thenReturn(mock(Collider.class));
        Scene scene = mock(Scene.class);
        when(player.getScene()).thenReturn(scene);
        when(scene.checkCollision(any())).thenReturn(false);

        PlayerState next = state.getNextState();
        assertInstanceOf(FallingState.class, next);
    }

    @Test
    void testGetNextStateToIdleBoundary() {
        WalkingState state = new WalkingState(player);
        // < 0.2
        when(rb.getVelocity()).thenReturn(new Vector(0.199f, 0));
        Collider col = mock(Collider.class);
        when(player.getCollider()).thenReturn(col);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(col.colPosCheck(any())).thenReturn(mock(Collider.class));
        Scene scene = mock(Scene.class);
        when(player.getScene()).thenReturn(scene);
        when(scene.checkCollision(any())).thenReturn(true);

        assertInstanceOf(IdleState.class, state.getNextState());
        
        when(rb.getVelocity()).thenReturn(new Vector(0.201f, 0));
        assertSame(state, state.getNextState());
    }

    @Test
    void testGetMineral() {
        WalkingState state = new WalkingState(player);
        assertNull(state.getMineral());
    }
}
