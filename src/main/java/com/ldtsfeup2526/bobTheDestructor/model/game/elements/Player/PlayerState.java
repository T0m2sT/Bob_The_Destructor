package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;

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
        if (playerModel.isGrounded()) {
            playerModel.getRigidBody().jump(playerModel.getJumpForce());
        }
    }

    public void applyFriction() {
        playerModel.getRigidBody().applyFriction();
    }

    public abstract PlayerState getNextState();
    public abstract MineralModel getMineral();
}
