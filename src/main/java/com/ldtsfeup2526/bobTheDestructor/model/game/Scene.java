package com.ldtsfeup2526.bobTheDestructor.model.game;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;

public class Scene {
    private PlayerModel playerModel = new PlayerModel(new Position(50, 50));

    public PlayerModel getPlayerModel() {
        return playerModel;
    }
}
