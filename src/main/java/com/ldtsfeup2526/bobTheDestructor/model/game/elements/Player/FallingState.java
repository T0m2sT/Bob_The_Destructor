package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class FallingState extends PlayerState {
    public FallingState(PlayerModel playerModel) {
        super(playerModel);
        playerModel.getRigidBody().setAcceleration(new Vector(
                playerModel.getRigidBody().getAcceleration().getX(),
                playerModel.getRigidBody().getGravity()
        ));
    }

    public PlayerState getNextState() {

        Collider blockUnder = getPlayerModel().getCollider().colPosCheck(
                new Position(getPlayerModel().getPosition().getX(), getPlayerModel().getPosition().getY()+1));
        if (getPlayerModel().getScene().checkCollision(blockUnder)) {
            return new IdleState(getPlayerModel());
        }

        return this;
    }
}
