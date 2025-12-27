package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PickaxeHitEventListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerModel extends ElementModel {
    private Collider collider;
    private RigidBody rigidBody;
    private boolean lookRight = true;
    private PlayerState state;
    private float jumpForce = 2.6f;
    private MineralModel mineralSelected = null;
    private float miningDistance = 10;
    private List<PickaxeHitEventListener> pickaxeHitEventListeners = new ArrayList<>();

    private boolean grounded = false;

    public PlayerModel(Position position) {
        super(position);
        this.collider = new Collider(position, new Size(5, 5));
        this.rigidBody = new RigidBody(position);
        this.state = new IdleState(this);
    }

    public void update() {
        updateState();
    }

    public void physicsUpdate(CollisionChecker collisionChecker) {
        //rigidBody.getVelocity().print();
        rigidBody.update();
        Vector nextPosF = rigidBody.getNextPos();
        Position nextPosI = new Position(Math.round(nextPosF.getX()), nextPosF.getY());
        Collider nextColX = collider.colPosCheck(new Position(nextPosI.getX(), getPosition().getY()));
        Collider nextColY = collider.colPosCheck(new Position(getPosition().getX(), nextPosI.getY()));

        boolean canMoveX = !collisionChecker.check(nextColX);
        boolean canMoveY = !collisionChecker.check(nextColY);

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

        groundedUpdate(collisionChecker);
    }

    private void groundedUpdate(CollisionChecker collisionChecker) {
        Collider blockUnder = getCollider().colPosCheck(
                new Position(getPosition().getX(), getPosition().getY()+1));

        grounded = collisionChecker.check(blockUnder);
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

    public void moveRight() {
        state.movePlayerRight();
    }

    public void moveLeft() {
        state.movePlayerLeft();
    }

    public void jump() {
        state.jump();
    }

    public void mine() {
        state = new MiningState(this, mineralSelected);
    }

    public void applyFriction() {
        state.applyFriction();
    }

    public PlayerState getState() {
        return state;
    }

    public void updateState() {
        state = state.getNextState();
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public void notifyWhenAnimFinished(String animName) {
        if (Objects.equals(animName, "MineAnim")) {
            state = new IdleState(this);
        }
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void updateSelectedMineral(List<MineralModel> nearbyMinerals) {
        MineralModel closest = null;
        double minDistance = miningDistance; // max reach

        for (MineralModel mineral : nearbyMinerals) {
            if (mineral.getState() == MineralState.DESTROYED) continue;

            double distance = getPosition().distance(mineral.getPosition());
            if (distance <= minDistance) {
                minDistance = distance;
                closest = mineral;
            }
        }

        this.mineralSelected = closest;
    }

    public void notifyWhenPickaxeHit() {
        for (PickaxeHitEventListener listeners: pickaxeHitEventListeners) {
            listeners.onPickaxeHit(this);
        }
    }

    public void addPickaxeHitEventListener(PickaxeHitEventListener listener) {
        pickaxeHitEventListeners.add(listener);
    }

    public void removePickaxeHitEventListener(PickaxeHitEventListener listener) {
        pickaxeHitEventListeners.remove(listener);
    }

    public MineralModel getMineralSelected() {
        return mineralSelected;
    }
}

