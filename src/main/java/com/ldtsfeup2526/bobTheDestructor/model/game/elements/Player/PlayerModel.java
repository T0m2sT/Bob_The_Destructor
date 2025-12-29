package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PickaxeHitEventListener;
import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerStateListener;
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
    private final Collider collider;
    private final RigidBody rigidBody;
    private boolean lookRight = true;
    private PlayerState state;
    private final float jumpForce = 2.6f;
    private MineralModel mineralSelected = null;
    private final float miningDistance = 10;
    private final List<PickaxeHitEventListener> pickaxeHitEventListeners = new ArrayList<>();
    private final List<PlayerStateListener> playerStateListeners = new ArrayList<>();
    private boolean grounded = false;
    private List<Position> lastValidPos = new ArrayList<>();

    public PlayerModel(Position position) {
        super(position);
        this.lastValidPos.add(position);
        this.collider = new Collider(position, new Size(5, 5));
        this.rigidBody = new RigidBody(position);
        this.state = new IdleState(this);
    }

    public void update() {
        updateState();
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
        setState(new MiningState(this, mineralSelected));
    }

    public void applyFriction() {
        state.applyFriction();
    }

    public PlayerState getState() {
        return state;
    }

    public void updateState() {
        setState(state.getNextState());
    }

    public void setState(PlayerState newState) {
        if (newState != state) {
            for(PlayerStateListener playerStateListener : playerStateListeners) {
                playerStateListener.onPlayerStateExit(state);
                playerStateListener.onPlayerStateEnter(newState);
            }
        }

        state = newState;
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

    public void addPlayerStateListener(PlayerStateListener listener) {
        playerStateListeners.add(listener);
    }

    public void removePlayerStateListener(PlayerStateListener listener) {
        playerStateListeners.remove(listener);
    }

    public MineralModel getMineralSelected() {
        return mineralSelected;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public Position getLastValidPos() {
        return lastValidPos.getFirst();
    }

    public void updateLastValidPos() {
        if (lastValidPos.size() == 10) {
            lastValidPos.removeFirst();
        }
        this.lastValidPos.add(getPosition());
    }
}

