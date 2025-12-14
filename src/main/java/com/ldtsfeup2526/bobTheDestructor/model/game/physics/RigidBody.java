package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class RigidBody {
    private Vector position;
    private Vector velocity = new Vector(0, 0);
    private Vector acceleration = new Vector(0, 0);

    public RigidBody(Position position) {
        this.position = new Vector(position);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void update() {
        velocity = new Vector(velocity.getX() + acceleration.getX(), velocity.getY() + acceleration.getY());
    }

    public Vector getNextPos() {
        return new Vector(position.getX() + velocity.getX(), position.getY() + velocity.getY());
    }


}
