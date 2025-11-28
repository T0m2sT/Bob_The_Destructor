package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

public abstract class ElementModel {
    private Position position;

    public ElementModel(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void moveLeft() {
        this.position = position.getLeft();
    }

    public void moveRight() {
        this.position = position.getRight();
    }

    public void moveUp() {
        this.position = position.getUp();
    }

    public void moveDown() {
        this.position = position.getDown();
    }
}
