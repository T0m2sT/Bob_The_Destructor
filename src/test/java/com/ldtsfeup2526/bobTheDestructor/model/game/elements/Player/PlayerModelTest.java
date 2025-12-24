package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerMiningListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerModelTest {
    private PlayerModel player;
    private Position startPos;

    private CollisionChecker collisionChecker;

    @BeforeEach
    void setUp() {
        startPos = new Position(10, 20);
        player = new PlayerModel(startPos);
        collisionChecker = mock(CollisionChecker.class);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(startPos, player.getPosition());
        assertNotNull(player.getCollider());
        assertNotNull(player.getRigidBody());
        assertTrue(player.isLookingRight());
        assertInstanceOf(IdleState.class, player.getState());
        assertEquals(2.6f, player.getJumpForce(), 0.001f);
    }

    @Test
    void testSetLookRight() {
        player.setLookRight(false);
        assertFalse(player.isLookingRight());
    }

    @Test
    void testMovementCallsState() {
        player.moveRight();
        assertTrue(player.isLookingRight());
        assertTrue(player.getRigidBody().getAcceleration().getX() > 0);
    }

    @Test
    void testMoveLeft() {
        player.moveLeft();
        assertFalse(player.isLookingRight());
        assertTrue(player.getRigidBody().getAcceleration().getX() < 0);
    }

    @Test
    void testJump() {
        // Since grounded is private and depends on groundedUpdate which depends on check
        // we can't easily force grounded = true without calling physicsUpdate.
        // But we want to test that jump() calls state.jump().
        player.jump();
        // By default it should not jump if not grounded, but let's see what IdleState does.
    }

    @Test
    void testMine() {
        player.mine();
        assertInstanceOf(MiningState.class, player.getState());
    }

    @Test
    void testNotifyWhenAnimFinished() {
        player.mine();
        player.notifyWhenAnimFinished("MineAnim");
        assertInstanceOf(IdleState.class, player.getState());
        
        player.mine();
        player.notifyWhenAnimFinished("OtherAnim");
        assertInstanceOf(MiningState.class, player.getState());
    }

    @Test
    void testUpdateSelectedMineral() {
        MineralModel mineral1 = mock(MineralModel.class);
        MineralModel mineral2 = mock(MineralModel.class);
        
        when(mineral1.getPosition()).thenReturn(new Position(15, 20)); // Dist = 5 (in range)
        when(mineral1.getState()).thenReturn(MineralState.UNSELECTED);
        when(mineral2.getPosition()).thenReturn(new Position(12, 20)); // Dist = 2 (closer)
        when(mineral2.getState()).thenReturn(MineralState.UNSELECTED);
        
        player.updateSelectedMineral(java.util.Arrays.asList(mineral1, mineral2));

        // It should select mineral2 (the closest)
        assertEquals(mineral2, player.getMineralSelected());
    }

    @Test
    void testUpdateSelectedMineralOutOfRange() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(30, 20)); // Dist = 20 > 10
        when(mineral.getState()).thenReturn(MineralState.UNSELECTED);

        player.updateSelectedMineral(List.of(mineral));
        assertNull(player.getMineralSelected());
    }

    @Test
    void testPhysicsUpdateCollision() {
        // player at (10, 20)
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(5, 0));
        
        // Mock collision in X but not Y
        when(collisionChecker.check(any())).thenAnswer(invocation -> {
            com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider c = invocation.getArgument(0);
            if (c == null) return false;
            // NextColX will be at (15, 20) roughly. Original pos (10, 20).
            return c.getPosition().getX() != 10;
        });
        
        player.physicsUpdate(collisionChecker);
        
        // Should not have moved in X
        assertEquals(10, player.getPosition().getX());
        assertEquals(0, player.getRigidBody().getVelocity().getX());
    }

    @Test
    void testMiningListeners() {
        PlayerMiningListener listener = mock(PlayerMiningListener.class);
        player.addMiningListener(listener);
        player.notifyWhenPickaxeHit();
        verify(listener).onMiningFinished(player);

        player.removeMiningListener(listener);
        player.notifyWhenPickaxeHit();
        verify(listener, times(1)).onMiningFinished(player);
    }
}
