package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class MineralModel extends ElementModel {
    public enum Type {COAL, IRON, GOLD, DIAMOND}

    private final int value;

    private final Type type;

    public MineralModel(Position position, Type type) {
        super(position);
        this.type = type;
        switch (type) {
            case DIAMOND: value = 20; break;
            case GOLD: value = 10; break;
            case IRON: value = 3; break;
            default: value = 1; break;
        }
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
