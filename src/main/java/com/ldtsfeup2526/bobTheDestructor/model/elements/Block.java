package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

public class Block extends Element{
    public enum Type {DIRT, ROCK, COAL, IRON, GOLD, DIAMOND}

    private final Type type;

    public Block(Position position, Type type) {
        super(position);
        this.type = type;
    }

    public Type getType() {return type;}
}
