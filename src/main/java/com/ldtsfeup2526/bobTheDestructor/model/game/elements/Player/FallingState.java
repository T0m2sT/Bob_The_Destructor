package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

public class FallingState extends PlayerState {
    public FallingState(PlayerModel playerModel) {
        super(playerModel);
    }

    public PlayerState getNextState() {
        return this;
    }
}
