package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

public class BlockModel extends ElementModel {
    public enum Type {DIRT, ROCK, COAL, IRON, GOLD, DIAMOND}

    private final Type type;

    private int durability;

    public BlockModel(Position position, Type type) {
        super(position);
        this.type = type;
        switch (type) {
            case DIAMOND: durability = 10; break;
            case GOLD: durability = 7; break;
            case IRON: durability = 5; break;
            case COAL: durability = 3; break;
            case ROCK: durability = 2; break;
            default: durability = 1; break;
        }
    }

    public Type getType() {return type;}

    public int getDurability() {return durability;}

    public void setDurability(int durability) {this.durability = durability;}

    public int decreaseDurability(PickaxeModel pickaxe) {return durability - pickaxe.getLevel();}
}
