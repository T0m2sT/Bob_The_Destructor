package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class MineralModel extends ElementModel {

    private final MineralType type;

    public MineralModel(Position position, MineralType type) {
        super(position);
        this.type = type;
    }

    public MineralType getType() {
        return type;
    }
}
