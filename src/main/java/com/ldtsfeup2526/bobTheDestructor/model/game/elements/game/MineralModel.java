package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class MineralModel extends ElementModel {
    public enum Type {COAL, IRON, GOLD, DIAMOND}

    private int health;

    private final Type type;

    public MineralModel(Position position, Type type) {
        super(position);
        this.type = type;
        switch (type) {
            case DIAMOND: health = 20; break;
            case GOLD: health = 10; break;
            case IRON: health = 3; break;
            default: health = 1; break;
        }
    }

    public Type getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
