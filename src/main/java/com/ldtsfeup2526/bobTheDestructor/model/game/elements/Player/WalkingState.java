package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;

public class WalkingState extends PlayerState{
    public WalkingState(PlayerModel playerModel) {
        super(playerModel);
    }

    @Override
    public PlayerState getNextState() {
        if (getPlayerModel().getRigidBody().getVelocity().getY() < 0) {
            return new JumpingState(getPlayerModel());
        }

        if (!getPlayerModel().isGrounded()) {
            return new FallingState(getPlayerModel());
        }

        if (Math.abs(getPlayerModel().getRigidBody().getVelocity().getX()) < 0.2) {
            return new IdleState(getPlayerModel());
        }

        return this;
    }

    @Override
    public MineralModel getMineral() {
        return null;
    }
}
