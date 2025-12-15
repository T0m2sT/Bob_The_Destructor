package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class WalkingState extends PlayerState{
    public WalkingState(PlayerModel playerModel) {
        super(playerModel);
    }

    public PlayerState getNextState() {
        return this;
    }
}
