package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class IdleState extends PlayerState {
    public IdleState(PlayerModel playerModel) {
        super(playerModel);
    }

    @Override
    public PlayerState getNextState() {
        if (Math.abs(getPlayerModel().getRigidBody().getVelocity().getX()) > 0) {
            return new WalkingState(getPlayerModel());
        }

        if (getPlayerModel().getRigidBody().getVelocity().getY() < 0) {
            return new JumpingState(getPlayerModel());
        }

        Collider blockUnder = getPlayerModel().getCollider().colPosCheck(
                new Position(getPlayerModel().getPosition().getX(), getPlayerModel().getPosition().getY()+1));
        if (!getPlayerModel().getScene().checkCollision(blockUnder)) {
            return new FallingState(getPlayerModel());
        }

        return this;
    }

    @Override
    public MineralModel getMineral() {
        return null;
    }
}
