package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class RigidBody {
    private Vector position;
    private Vector velocity = new Vector(0, 0);
    private Vector acceleration;

    private Vector maxVelocity = new Vector(1, 10);
    private float speed = 0.1f;
    private float friction = 0.2f;
    private float gravity = 0.5f;

    public RigidBody(Position position) {
        this.position = new Vector(position);
        acceleration = new Vector(0, gravity);
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
        float velX = velocity.getX() + acceleration.getX();
        float velY = velocity.getY() + acceleration.getY();

        velX = (Math.abs(velX) > maxVelocity.getX()) ? maxVelocity.getX() * Math.signum(velX) : velX;
        velY = (Math.abs(velY) > maxVelocity.getY()) ? maxVelocity.getY() * Math.signum(velY) : velY;

        velocity = new Vector(velX, velY);
    }

    public Vector getNextPos() {
        return new Vector(position.getX() + velocity.getX(), position.getY() + velocity.getY());
    }

    public void moveRight() {
        if (Math.signum(velocity.getX()) == -1) {
            velocity = new Vector(0, velocity.getY());
        }
        acceleration = new Vector(speed, acceleration.getY());
    }

    public void moveLeft() {
        if (Math.signum(velocity.getX()) == 1) {
            velocity = new Vector(0, velocity.getY());
        }
        acceleration = new Vector(-speed, acceleration.getY());
    }

    public void jump(float jumpForce) {
        setVelocity(new Vector(velocity.getX(), -jumpForce));
    }

    public void applyFriction() {
        if (Math.abs(velocity.getX()) < 0.2f) {
            velocity = new Vector(0, velocity.getY());
            acceleration = new Vector(0, acceleration.getY());
            return;
        }
        acceleration = new Vector(
                -friction * Math.signum(velocity.getX()),
                acceleration.getY()
        );
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getGravity() {
        return gravity;
    }
}
