package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

public class IdleState extends PlayerState {
    public IdleState(PlayerModel playerModel) {
        super(playerModel);
    }

    public PlayerState getNextState() {
        return this;
    }
}
