package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

public class JumpingState extends PlayerState {
    public JumpingState(PlayerModel playerModel) {
        super(playerModel);
    }

    public PlayerState getNextState() {
        return this;
    }
}
