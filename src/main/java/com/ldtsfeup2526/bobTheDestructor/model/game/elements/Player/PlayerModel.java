package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

public class PlayerModel extends ElementModel {
    private Collider collider;
    private RigidBody rigidBody;
    private final Scene scene;
    private boolean lookRight = true;
    private PlayerState state;

    public PlayerModel(Position position, Scene scene) {
        super(position);
        this.collider = new Collider(position, new Size(5, 6));
        this.rigidBody = new RigidBody(position);
        this.scene = scene;
        this.state = new IdleState(this);
    }

    public void update() {
        //rigidBody.getVelocity().print();
        rigidBody.update();
        Vector nextPosF = rigidBody.getNextPos();
        Position nextPosI = new Position(Math.round(nextPosF.getX()), nextPosF.getY());
        Collider nextColX = collider.colPosCheck(new Position(nextPosI.getX(), getPosition().getY()));
        Collider nextColY = collider.colPosCheck(new Position(getPosition().getX(), nextPosI.getY()));

        boolean canMoveX = !scene.checkCollision(nextColX);
        boolean canMoveY = !scene.checkCollision(nextColY);

        float x = nextPosF.getX();
        float y = nextPosF.getY();

        if (!canMoveX) {
            x = rigidBody.getPosition().getX();
            rigidBody.setVelocity(new Vector(0, rigidBody.getVelocity().getY()));
            rigidBody.setAcceleration(new Vector(0, rigidBody.getAcceleration().getY()));
        }

        if (!canMoveY) {
            y = rigidBody.getPosition().getY();
            rigidBody.setVelocity(new Vector(rigidBody.getVelocity().getX(), 0));
            //rigidBody.setAcceleration(new Vector(rigidBody.getAcceleration().getX(), 0));
        }

        Position finalPos = new Position((int) Math.round(x), (int) y);
        setPosition(finalPos);
        collider.setPosition(finalPos);
        rigidBody.setPosition(new Vector(finalPos));
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setLookRight(boolean lookRight) {
        this.lookRight = lookRight;
    }

    public boolean isLookingRight() {
        return lookRight;
    }

    public Scene getScene() {
        return scene;
    }

    public void moveRight() {
        state.movePlayerRight();
    }

    public void moveLeft() {
        state.movePlayerLeft();
    }

    public void jump() {
        state.jump();
    }

    public void applyFriction() {
        state.applyFriction();
    }
}

