package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

public abstract class PlayerState {
    private final PlayerModel playerModel;

    public PlayerState(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }


}
