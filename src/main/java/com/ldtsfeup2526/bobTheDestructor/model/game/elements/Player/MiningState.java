package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class MiningState extends PlayerState{
    private final MineralModel mineralSelected;
    public MiningState(PlayerModel playerModel, MineralModel mineralSelected) {
        super(playerModel);
        this.mineralSelected = mineralSelected;
        playerModel.getRigidBody().setAcceleration(new Vector(0, playerModel.getRigidBody().getAcceleration().getY()));
        playerModel.getRigidBody().setVelocity(new Vector(0, playerModel.getRigidBody().getVelocity().getY()));
    }

    @Override
    public void movePlayerLeft() {}

    @Override
    public void movePlayerRight() {}

    @Override
    public void jump() {}

    @Override
    public PlayerState getNextState() {
        return this;
    }

    @Override
    public MineralModel getMineral() {
        return mineralSelected;
    }
}
