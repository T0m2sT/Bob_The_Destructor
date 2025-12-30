package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;

public class PlayerControllerTest {
    private PlayerModel player;
    private PlayerController controller;
    private Game game;
    private SoundManager soundManager;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        soundManager = mock(SoundManager.class);
        controller = new PlayerController(player, soundManager);
        game = mock(Game.class);
        when(player.getState()).thenReturn(new IdleState(player));
    }

    @Test
    void testMoveRight() {
        controller.update(game, List.of(Action.RIGHT), 0.1);
        verify(player).moveRight();
        verify(player).update();
    }

    @Test
    void testMoveLeft() {
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(player).moveLeft();
        verify(player).update();
    }

    @Test
    void testJump() {
        controller.update(game, List.of(Action.JUMP), 0.1);
        verify(player).jump();
    }

    @Test
    void testMine() {
        controller.update(game, List.of(Action.MINE), 0.1);
        verify(player).mine();
    }

    @Test
    void testJumpUp() {
        controller.update(game, List.of(Action.UP), 0.1);
        verify(player).jump();
    }

    @Test
    void testMineWhenAlreadyMining() {
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState miningState = mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState.class);
        when(player.getState()).thenReturn(miningState);
        controller.update(game, List.of(Action.MINE), 0.1);
        verify(player, never()).mine();
    }

    @Test
    void testNoApplyFrictionWhenMoving() {
        controller.update(game, List.of(Action.RIGHT), 0.1);
        verify(player, never()).applyFriction();
        
        controller.update(game, List.of(Action.LEFT), 0.1);
        verify(player, never()).applyFriction();
    }

    @Test
    void testJumpBoth() {
        controller.update(game, List.of(Action.JUMP, Action.UP), 0.1);
        verify(player).jump();
    }

    @Test
    void testNoApplyFrictionWhenMovingBoth() {
        controller.update(game, List.of(Action.LEFT, Action.RIGHT), 0.1);
        verify(player, never()).applyFriction();
    }

    @Test
    void testApplyFriction() {
        controller.update(game, List.of(), 0.1);
        verify(player).applyFriction();
    }

    @Test
    void testStateEnterExit() {
        controller.onPlayerStateEnter(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.JumpingState.class));
        verify(soundManager).playSFX("sounds/soundEffects/jumping.wav");

        controller.onPlayerStateEnter(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.FallingState.class));
        verify(soundManager).playSFX("sounds/soundEffects/falling.wav");

        controller.onPlayerStateEnter(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.WalkingState.class));
        verify(soundManager).playSFXLoop("sounds/soundEffects/walking.wav");

        controller.onPlayerStateExit(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.WalkingState.class));
        verify(soundManager).stopSFX("sounds/soundEffects/walking.wav");
        
        // Coverage for other states that don't trigger anything
        controller.onPlayerStateEnter(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState.class));
        controller.onPlayerStateExit(mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState.class));
    }

    @Test
    void testPositionCorrection() {
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker checker = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider collider = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        when(player.getCollider()).thenReturn(collider);
        
        // No collision
        when(checker.check(collider)).thenReturn(null);
        controller.positionCorrection(checker);
        verify(player).updateLastValidPos();
        
        // Collision
        when(checker.check(collider)).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class));
        com.ldtsfeup2526.bobTheDestructor.model.spatials.Position lastPos = new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(5, 5);
        when(player.getLastValidPos()).thenReturn(lastPos);
        controller.positionCorrection(checker);
        verify(player).setPosition(lastPos);
    }

    @Test
    void testGroundedUpdate() {
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker checker = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider collider = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        
        when(player.getRigidBody()).thenReturn(rb);
        when(player.getCollider()).thenReturn(collider);
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(10, 20));
        when(rb.getNextPos()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(10, 20));
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(10, 20));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider blockUnder = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        when(collider.colPosCheck(argThat(p -> p != null && p.getX() == 10 && p.getY() == 21))).thenReturn(blockUnder);
        
        // Not grounded
        when(checker.check(blockUnder)).thenReturn(null);
        controller.physicsUpdate(checker);
        verify(player).setGrounded(false);
        
        // Grounded
        when(checker.check(blockUnder)).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class));
        controller.physicsUpdate(checker);
        verify(player).setGrounded(true);
    }

    @Test
    void testConstructorAddsListener() {
        PlayerModel model = mock(PlayerModel.class);
        new PlayerController(model, soundManager);
        verify(model).addPlayerStateListener(any());
    }

    @Test
    void testPhysicsUpdate() {
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker checker = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider collider = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        
        when(player.getRigidBody()).thenReturn(rb);
        when(player.getCollider()).thenReturn(collider);
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(10, 20));
        
        when(rb.getNextPos()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(11, 21));
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(10, 20));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 1));
        when(rb.getAcceleration()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        
        when(collider.colPosCheck(any())).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class));
        
        // Test with collisions on both X and Y
        when(checker.check(any())).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class));
        
        controller.physicsUpdate(checker);
        
        verify(rb).update();
        verify(rb).setVelocity(argThat(v -> v.getX() == 0));
        verify(rb).setVelocity(argThat(v -> v.getY() == 0));
        verify(rb).setAcceleration(any());
        
        // Position when both collide should be (10, 20) because we reset to rb.getPosition()
        verify(player).setPosition(argThat(p -> p.getX() == 10 && p.getY() == 20));
        verify(collider).setPosition(any());
        verify(rb).setPosition(any());

        // Verify grounded update uses y+1
        verify(collider, atLeastOnce()).colPosCheck(argThat(p -> p != null && p.getX() == 10 && p.getY() == 21));
        verify(player).setGrounded(true);
    }
    @Test
    void testPhysicsUpdateCollisionXOnly() {
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker checker = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider collider = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);

        when(player.getRigidBody()).thenReturn(rb);
        when(player.getCollider()).thenReturn(collider);
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 0));

        when(rb.getNextPos()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 1));
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 1));
        when(rb.getAcceleration()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));

        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider nextColX = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider nextColY = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        when(collider.colPosCheck(argThat(p -> p != null && p.getX() == 1 && p.getY() == 0))).thenReturn(nextColX);
        when(collider.colPosCheck(argThat(p -> p != null && p.getX() == 0 && p.getY() == 1))).thenReturn(nextColY);

        when(checker.check(nextColX)).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class)); // Collision X
        when(checker.check(nextColY)).thenReturn(null); // No collision Y

        controller.physicsUpdate(checker);

        verify(rb).setVelocity(argThat(v -> v.getX() == 0));
        verify(rb, never()).setVelocity(argThat(v -> v.getY() == 0));
    }

    @Test
    void testPhysicsUpdateCollisionYOnly() {
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker checker = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider collider = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);

        when(player.getRigidBody()).thenReturn(rb);
        when(player.getCollider()).thenReturn(collider);
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 0));

        when(rb.getNextPos()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 1));
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 1));
        when(rb.getAcceleration()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));

        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider nextColX = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider nextColY = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class);
        when(collider.colPosCheck(argThat(p -> p != null && p.getX() == 1 && p.getY() == 0))).thenReturn(nextColX);
        when(collider.colPosCheck(argThat(p -> p != null && p.getX() == 0 && p.getY() == 1))).thenReturn(nextColY);

        when(checker.check(nextColX)).thenReturn(null); // No collision X
        when(checker.check(nextColY)).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class)); // Collision Y

        controller.physicsUpdate(checker);

        verify(rb, never()).setVelocity(argThat(v -> v.getX() == 0));
        verify(rb).setVelocity(argThat(v -> v.getY() == 0));
    }
}
