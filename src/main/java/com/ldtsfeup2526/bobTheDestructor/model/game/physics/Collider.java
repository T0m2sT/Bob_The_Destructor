package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

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

    public boolean isPointOver(Spatial<?> point) {
        Vector pointPos = new Vector(point);

        Vector originCorner = new Vector(position);
        Vector oppositeCorner = new Vector(getOppositeCorner());

        return  pointPos.getX() >= originCorner.getX() &&
                pointPos.getX() <= oppositeCorner.getX() &&
                pointPos.getY() >= originCorner.getY() &&
                pointPos.getY() <= oppositeCorner.getY();

    }

    public boolean isColliderOver(Collider collider) {
        Vector cornerC1 = new Vector(position);
        Vector oppositeCornerC1 = new Vector(getOppositeCorner());

        Vector cornerC2 = new Vector(collider.getPosition());
        Vector oppositeCornerC2 = new Vector(collider.getOppositeCorner());

        return cornerC1.getX() <= oppositeCornerC2.getX() &&
                oppositeCornerC1.getX() >= cornerC2.getX() &&
                cornerC1.getY() <= oppositeCornerC2.getY() &&
                oppositeCornerC1.getY() >= cornerC2.getY();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Position getOppositeCorner() {
        return new Position(position.getX()+size.getX(), position.getY()+size.getY());
    }

    public Collider colPosCheck(Position position) {
        return new Collider(position, size);
    }
}
