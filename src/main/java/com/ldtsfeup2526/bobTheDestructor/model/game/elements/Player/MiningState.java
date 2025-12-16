package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class MiningState extends PlayerState{
    public MiningState(PlayerModel playerModel) {
        super(playerModel);
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
}
