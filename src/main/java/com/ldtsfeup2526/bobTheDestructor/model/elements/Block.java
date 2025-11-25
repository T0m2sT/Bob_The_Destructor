package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

public class Block extends Element{
    public enum TYPE {DIRT, ROCK, COAL, IRON, GOLD, DIAMOND}

    private TYPE type;

    public Block(Position position, TYPE type) {
        super(position);
        this.type = type;
    }

    public TYPE getType() {return type;}
}
