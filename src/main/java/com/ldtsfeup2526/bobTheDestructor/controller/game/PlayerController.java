package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.*;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;

import java.util.List;
import java.util.Objects;

public class PlayerController extends Controller<PlayerModel> implements PlayerStateListener{
    private final SoundManager soundManager;

    public PlayerController(PlayerModel player, SoundManager soundManager) {
        super(player);
        getModel().addPlayerStateListener(this);
        this.soundManager = soundManager;
    }

    @Override
    public void update(Game game, List<Action> actions, double deltaTime) {
        PlayerModel player = getModel();

        if (actions.contains(Action.RIGHT)) player.moveRight();
        if (actions.contains(Action.LEFT)) player.moveLeft();
        if (actions.contains(Action.JUMP) || actions.contains(Action.UP)) player.jump();
        if (actions.contains(Action.MINE) && player.getState().getClass() != MiningState.class) player.mine();

        if (!actions.contains(Action.LEFT) && !actions.contains(Action.RIGHT)) player.applyFriction();

        player.update();
    }

    @Override
    public void onPlayerStateEnter(PlayerState playerState) {
        if (playerState instanceof JumpingState) {
            soundManager.playSFX("sounds/soundEffects/jumping.wav");
        } else if (playerState instanceof FallingState) {
            soundManager.playSFX("sounds/soundEffects/falling.wav");
        } else if (playerState instanceof WalkingState) {
            soundManager.playSFXLoop("sounds/soundEffects/walking.wav");
        }
    }

    @Override
    public void onPlayerStateExit(PlayerState playerState) {
        if (playerState instanceof WalkingState) {
            soundManager.stopSFX("sounds/soundEffects/walking.wav");
        }
    }

    public void positionCorrection(CollisionChecker collisionChecker) {
        Collider collider = collisionChecker.check(getModel().getCollider());

        if (Objects.isNull(collider)) {
            getModel().updateLastValidPos();
            return;
        }
        System.out.println("Done");
        getModel().setPosition(getModel().getLastValidPos());

    }

    public void physicsUpdate(CollisionChecker collisionChecker) {
        PlayerModel playerModel = getModel();
        RigidBody rigidBody = playerModel.getRigidBody();
        Collider collider = playerModel.getCollider();

        rigidBody.update();
        Vector nextPosF = rigidBody.getNextPos();
        Position nextPosI = new Position(Math.round(nextPosF.getX()), nextPosF.getY());
        Collider nextColX = collider.colPosCheck(new Position(nextPosI.getX(), playerModel.getPosition().getY()));
        Collider nextColY = collider.colPosCheck(new Position(playerModel.getPosition().getX(), nextPosI.getY()));

        boolean canMoveX = Objects.isNull(collisionChecker.check(nextColX));
        boolean canMoveY = Objects.isNull(collisionChecker.check(nextColY));

        float x = nextPosF.getX();
        float y = nextPosF.getY();

        if (!canMoveX) {
            x = rigidBody.getPosition().getX();
            rigidBody.setVelocity(new Vector(0, rigidBody.getVelocity().getY()));
            rigidBody.setAcceleration(new Vector(0, rigidBody.getAcceleration().getY()));
        }

        if (!canMoveY) {
            y = rigidBody.getPosition().getY();
            rigidBody.setVelocity(new Vector(rigidBody.getVelocity().getX(), 0));
            //rigidBody.setAcceleration(new Vector(rigidBody.getAcceleration().getX(), 0));
        }

        Position finalPos = new Position((int) Math.round(x), (int) y);
        playerModel.setPosition(finalPos);
        collider.setPosition(finalPos);
        rigidBody.setPosition(new Vector(finalPos));

        groundedUpdate(collisionChecker);
    }

    private void groundedUpdate(CollisionChecker collisionChecker) {
        Collider blockUnder = getModel().getCollider().colPosCheck(
                new Position(getModel().getPosition().getX(), getModel().getPosition().getY()+1));

        getModel().setGrounded(!Objects.isNull(collisionChecker.check(blockUnder)));
    }
}