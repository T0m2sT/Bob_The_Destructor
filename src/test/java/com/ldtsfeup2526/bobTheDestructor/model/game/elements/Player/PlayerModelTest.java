package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PickaxeHitEventListener;
import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerStateListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
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
    void testMovement() {
        player.moveRight();
        assertTrue(player.isLookingRight());
        
        player.moveLeft();
        assertFalse(player.isLookingRight());
    }

    @Test
    void testJump() {
        player.jump();
        assertInstanceOf(IdleState.class, player.getState());
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
        MineralModel m1 = mock(MineralModel.class);
        when(m1.getPosition()).thenReturn(new Position(12, 20));
        when(m1.getState()).thenReturn(MineralState.UNSELECTED);
        
        MineralModel m2 = mock(MineralModel.class);
        when(m2.getPosition()).thenReturn(new Position(100, 100));
        when(m2.getState()).thenReturn(MineralState.UNSELECTED);
        
        player.updateSelectedMineral(List.of(m1, m2));
        assertEquals(m1, player.getMineralSelected());
    }

    @Test
    void testListeners() {
        PickaxeHitEventListener pickaxeListener = mock(PickaxeHitEventListener.class);
        player.addPickaxeHitEventListener(pickaxeListener);
        player.notifyWhenPickaxeHit();
        verify(pickaxeListener).onPickaxeHit(player);
        
        player.removePickaxeHitEventListener(pickaxeListener);
        player.notifyWhenPickaxeHit();
        verify(pickaxeListener, times(1)).onPickaxeHit(player);
        
        PlayerStateListener stateListener = mock(PlayerStateListener.class);
        player.addPlayerStateListener(stateListener);
        player.setState(new WalkingState(player));
        verify(stateListener).onPlayerStateExit(any());
        verify(stateListener).onPlayerStateEnter(any());
    }

    @Test
    void testStateTransitions() {
        // Idle to Walking
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 0));
        player.updateState();
        assertInstanceOf(WalkingState.class, player.getState());

        // Walking to Jumping
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, -1));
        player.updateState();
        assertInstanceOf(JumpingState.class, player.getState());

        // Jumping to Falling
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 1));
        player.updateState();
        assertInstanceOf(FallingState.class, player.getState());

        // Falling to Idle
        player.setGrounded(true);
        player.updateState();
        assertInstanceOf(IdleState.class, player.getState());
        
        // Idle to Falling
        player.setGrounded(false);
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        player.updateState();
        assertInstanceOf(FallingState.class, player.getState());
        
        // Walking to Idle
        player.setGrounded(true);
        player.setState(new WalkingState(player));
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0.1, 0));
        player.updateState();
        assertInstanceOf(IdleState.class, player.getState());
        
        // Mining stays Mining
        player.setState(new MiningState(player, null));
        player.updateState();
        assertInstanceOf(MiningState.class, player.getState());
    }

    @Test
    void testStateActions() {
        player.setState(new MiningState(player, null));
        player.moveLeft();
        player.moveRight();
        player.jump();
        // Should not change velocity/acceleration in MiningState (beyond constructor setting them to 0)
        assertEquals(0, player.getRigidBody().getVelocity().getX());
        
        player.setState(new IdleState(player));
        player.moveLeft();
        assertFalse(player.isLookingRight());
        player.moveRight();
        assertTrue(player.isLookingRight());
        
        player.setGrounded(true);
        player.jump();
        assertTrue(player.getRigidBody().getVelocity().getY() < 0);
    }
    @Test
    void testUpdate() {
        PlayerState state = mock(PlayerState.class);
        player.setState(state);
        player.update();
        verify(state).getNextState();
    }

    @Test
    void testApplyFriction() {
        PlayerState state = mock(PlayerState.class);
        player.setState(state);
        player.applyFriction();
        verify(state).applyFriction();
    }

    @Test
    void testLastValidPos() {
        Position pos = new Position(5, 5);
        player.setPosition(pos);
        player.updateLastValidPos();
        assertEquals(startPos, player.getLastValidPos()); // First is still startPos

        for (int i = 0; i < 11; i++) {
            player.setPosition(new Position(i, i));
            player.updateLastValidPos();
        }
        // After 10 updates, the first one (startPos) should have been removed
        assertNotEquals(startPos, player.getLastValidPos());
    }

    @Test
    void testRemovePlayerStateListener() {
        PlayerStateListener stateListener = mock(PlayerStateListener.class);
        player.addPlayerStateListener(stateListener);
        player.removePlayerStateListener(stateListener);
        player.setState(new WalkingState(player));
        verify(stateListener, never()).onPlayerStateExit(any());
    }

    @Test
    void testLastValidPosQueue() {
        Position p0 = startPos;
        assertEquals(p0.getX(), player.getLastValidPos().getX());
        assertEquals(p0.getY(), player.getLastValidPos().getY());
        
        for (int i = 1; i <= 9; i++) {
            player.setPosition(new Position(10 + i, 20 + i));
            player.updateLastValidPos();
        }
        assertEquals(p0.getX(), player.getLastValidPos().getX());
        assertEquals(p0.getY(), player.getLastValidPos().getY());
        
        player.setPosition(new Position(20, 30));
        player.updateLastValidPos();

        assertEquals(11, player.getLastValidPos().getX());
        assertEquals(21, player.getLastValidPos().getY());
    }

    @Test
    void testUpdateSelectedMineralDestroyed() {
        MineralModel m1 = mock(MineralModel.class);
        when(m1.getPosition()).thenReturn(new Position(12, 20));
        when(m1.getState()).thenReturn(MineralState.DESTROYED);
        
        player.updateSelectedMineral(List.of(m1));
        assertNull(player.getMineralSelected());
    }

    @Test
    void testNotifyWhenPickaxeHitMultiple() {
        PickaxeHitEventListener l1 = mock(PickaxeHitEventListener.class);
        PickaxeHitEventListener l2 = mock(PickaxeHitEventListener.class);
        player.addPickaxeHitEventListener(l1);
        player.addPickaxeHitEventListener(l2);
        
        player.notifyWhenPickaxeHit();
        verify(l1).onPickaxeHit(player);
        verify(l2).onPickaxeHit(player);
    }
}
