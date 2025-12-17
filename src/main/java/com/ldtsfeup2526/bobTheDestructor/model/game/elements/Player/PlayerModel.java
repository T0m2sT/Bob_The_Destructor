package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerModel extends ElementModel {
    private Collider collider;
    private RigidBody rigidBody;
    private Scene scene;
    private boolean lookRight = true;
    private PlayerState state;
    private float jumpForce = 2.6f;
    private MineralModel mineralSelected = null;
    private float miningDistance = 10;

    public PlayerModel(Position position) {
        super(position);
        this.collider = new Collider(position, new Size(5, 5));
        this.rigidBody = new RigidBody(position);
        this.state = new IdleState(this);
    }

    public void update() {
        cleanupMinerals();
        physicsUpdate();
        updateState();
        findMineralInReach();
    }

    public void physicsUpdate() {
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

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void notifyWhenAnimFinished(String animName) {
        if (Objects.equals(animName, "MineAnim")) {
            state = new IdleState(this);
        }
    }

    public void findMineralInReach() {
        if (mineralSelected != null && mineralSelected.getState() == MineralState.DESTROYED) {
            mineralSelected = null;
        } else if (mineralSelected != null) {
            double distanceFromPlayer = getPosition().distance(mineralSelected.getPosition());
            if (distanceFromPlayer > miningDistance) {
                mineralSelected.setState(MineralState.UNSELECTED);
                mineralSelected = null;
            }
        }

        for (MineralModel mineralModel : scene.getMineralModels()) {

            if (mineralModel.getState() == MineralState.DESTROYED) {
                continue;
            }

            Position mineralPos = mineralModel.getPosition();
            double distanceFromPlayer = getPosition().distance(mineralPos);
            if (distanceFromPlayer <= miningDistance) {
                if (mineralSelected == null) {
                    mineralSelected = mineralModel;
                    mineralModel.setState(MineralState.SELECTED);
                } else if (distanceFromPlayer < getPosition().distance(mineralModel.getPosition())){
                    mineralSelected.setState(MineralState.UNSELECTED);
                    mineralModel.setState(MineralState.SELECTED);
                    mineralSelected = mineralModel;
                }

            }
        }
    }

    public void notifyWhenPickaxeHit() {
        if (state.getMineral() != null) {
            state.getMineral().setState(MineralState.DESTROYED);
            scene.incrementCurrentMineralsCollected();
        } else if (mineralSelected != null) {
            mineralSelected.setState(MineralState.DESTROYED);
            scene.incrementCurrentMineralsCollected();
        }
    }

    public void cleanupMinerals() {
        for (MineralModel mineralModel : new ArrayList<>(scene.getMineralModels())) {
            if (mineralModel.getState() == MineralState.CLEANUP) {
                scene.getMineralModels().remove(mineralModel);
            }
        }
    }
}

