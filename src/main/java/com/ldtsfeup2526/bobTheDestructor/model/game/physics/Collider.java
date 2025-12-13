package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;

public class Collider {
    private Position position;
    private Size size;

    public Collider(Position position) {
        this(position, new Size(8, 8));
    }

    public Collider(Position position, Size size) {
        this.position = position;
        this.size = size;
        //System.out.println(position.getX().toString() + " " + position.getY().toString());
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }
}
