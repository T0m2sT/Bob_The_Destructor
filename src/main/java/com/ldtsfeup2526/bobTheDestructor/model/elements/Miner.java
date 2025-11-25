package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

public class Miner extends Element {
    private int level;

    public Miner(Position position) {
        super(position);
        level = 1;
    }

    public int getLevel() {return level;}

    public void setLevel(int level) {this.level = level;}
}
