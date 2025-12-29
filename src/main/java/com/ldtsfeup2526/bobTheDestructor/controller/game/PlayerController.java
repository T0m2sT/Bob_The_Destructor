package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.*;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
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
            return;
        }

        int vecX = getModel().getPosition().getX() - collider.getPosition().getX();
        int vecY = getModel().getPosition().getY() - collider.getPosition().getY();

        int moveX = collider.getSize().getX() - Math.abs(vecX);
        int moveY = collider.getSize().getY() - Math.abs(vecY);


        int newX = (int) (getModel().getPosition().getX() + moveX * Math.signum(vecX) + Math.signum(vecX));
        int newY = (int) (getModel().getPosition().getY() + moveY * Math.signum(vecY) + Math.signum(vecY));
        getModel().setPosition(new Position(newX, newY));
    }
}