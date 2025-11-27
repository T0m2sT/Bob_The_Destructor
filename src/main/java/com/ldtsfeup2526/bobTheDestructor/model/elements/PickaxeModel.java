package com.ldtsfeup2526.bobTheDestructor.model.elements;

public class PickaxeModel {
    public enum Type {IRON, GOLD, DIAMOND}

    private Type type;

    private int damage;

    public PickaxeModel(Type type) {
        this.type = type;
        switch (type) {
            case DIAMOND: damage = 3; break;
            case GOLD: damage = 2; break;
            default: damage = 1; break;
        }
    }

    public int getDamage() {
        return damage;
    }

    public Type getType() {
        return type;
    }

    private void updateDamage() {
        switch (type) {
            case DIAMOND: damage = 3; break;
            case GOLD: damage = 2; break;
            default: damage = 1; break;
        }
    }

    public void setType(Type type) {
        this.type = type;
        updateDamage();
    }
}
