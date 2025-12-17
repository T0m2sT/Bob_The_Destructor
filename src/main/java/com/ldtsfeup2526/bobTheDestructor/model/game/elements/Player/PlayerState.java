package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public abstract class PlayerState {
    private final PlayerModel playerModel;

    public PlayerState(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void movePlayerLeft() {
        playerModel.getRigidBody().moveLeft();
        playerModel.setLookRight(false);
    }

    public void movePlayerRight() {
        playerModel.getRigidBody().moveRight();
        playerModel.setLookRight(true);
    }

    public void jump() {
        Collider blockUnder = playerModel.getCollider().colPosCheck(
                new Position(playerModel.getPosition().getX(), playerModel.getPosition().getY()+1));
        if (playerModel.getScene().checkCollision(blockUnder)) {
            playerModel.getRigidBody().jump(playerModel.getJumpForce());
        }
    }

    public void applyFriction() {
        playerModel.getRigidBody().applyFriction();
    }

    public abstract PlayerState getNextState();
    public abstract MineralModel getMineral();
}
