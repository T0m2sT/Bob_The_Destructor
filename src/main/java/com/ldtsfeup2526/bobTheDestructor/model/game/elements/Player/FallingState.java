package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class FallingState extends PlayerState {
    public FallingState(PlayerModel playerModel) {
        super(playerModel);
        playerModel.getRigidBody().setAcceleration(new Vector(
                playerModel.getRigidBody().getAcceleration().getX(),
                playerModel.getRigidBody().getGravity()
        ));
    }

    @Override
    public PlayerState getNextState() {

        if (getPlayerModel().isGrounded()) {
            return new IdleState(getPlayerModel());
        }

        return this;
    }

    @Override
    public MineralModel getMineral() {
        return null;
    }
}
