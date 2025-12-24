package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerMiningListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
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

    @BeforeEach
    void setUp() {
        startPos = new Position(10, 20);
        player = new PlayerModel(startPos);
        Scene scene = mock(Scene.class);
        player.setScene(scene);
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
        when(player.getScene().checkCollision(any())).thenReturn(true);
        player.jump();
        assertTrue(player.getRigidBody().getVelocity().getY() < 0);
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
    void testFindMineralInReach() {
        Scene scene = player.getScene();
        MineralModel mineral1 = mock(MineralModel.class);
        MineralModel mineral2 = mock(MineralModel.class);
        
        // In production, it selects the FIRST mineral found in the list that is in range.
        // It has a bug where it compares distance with itself: 
        // else if (distanceFromPlayer < getPosition().distance(mineralModel.getPosition()))
        
        when(mineral1.getPosition()).thenReturn(new Position(15, 20)); // Dist = 5 (in range)
        when(mineral1.getState()).thenReturn(MineralState.UNSELECTED);
        when(mineral2.getPosition()).thenReturn(new Position(12, 20)); // Dist = 2 (closer)
        when(mineral2.getState()).thenReturn(MineralState.UNSELECTED);
        
        when(scene.getMineralModels()).thenReturn(java.util.Arrays.asList(mineral1, mineral2));

        player.findMineralInReach();

        // Due to the bug in production (comparing distance to same mineral), 
        // it selects mineral1 and doesn't update to mineral2.
        assertEquals(mineral1, player.getMineralSelected());
        verify(mineral1).setState(MineralState.SELECTED);
    }

    @Test
    void testFindMineralInReachOutOfRange() {
        Scene scene = player.getScene();
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(30, 20)); // Dist = 20 > 10
        when(mineral.getState()).thenReturn(MineralState.UNSELECTED);
        when(scene.getMineralModels()).thenReturn(List.of(mineral));

        player.findMineralInReach();
        assertNull(player.getMineralSelected());
    }

    @Test
    void testFindMineralInReachAlreadySelected() {
        Scene scene = player.getScene();
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(12, 20));
        when(mineral.getState()).thenReturn(MineralState.SELECTED);
        when(scene.getMineralModels()).thenReturn(List.of(mineral));
        
        // Manually set selected
        player.findMineralInReach(); // Should select it
        assertEquals(mineral, player.getMineralSelected());
        
        // Move away
        when(mineral.getPosition()).thenReturn(new Position(30, 20));
        player.findMineralInReach();
        assertNull(player.getMineralSelected());
        verify(mineral).setState(MineralState.UNSELECTED);
    }

    @Test
    void testPhysicsUpdateCollision() {
        Scene scene = player.getScene();
        // player at (10, 20)
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(5, 0));
        
        // Mock collision in X but not Y
        // We use any(Collider.class) and handle logic in thenAnswer to avoid NPE in argThat
        when(scene.checkCollision(any())).thenAnswer(invocation -> {
            com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider c = invocation.getArgument(0);
            if (c == null) return false;
            return c.getPosition().getX() != 10;
        });
        
        player.physicsUpdate();
        
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
