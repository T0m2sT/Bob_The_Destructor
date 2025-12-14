package com.ldtsfeup2526.bobTheDestructor.controller.game.elements;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;

import java.util.List;

public class PlayerController extends Controller<PlayerModel> {
    public PlayerController(PlayerModel player) {
        super(player);
    }

    @Override
    public void update(Game game, List<Action> actions) {
        getModel().update();
        if (actions.contains(Action.LEFT)) moveLeft();
        if (actions.contains(Action.RIGHT)) moveRight();
        if (actions.contains(Action.UP)) moveUp();
        if (actions.contains(Action.DOWN)) moveDown();

        if (!actions.contains(Action.LEFT) && !actions.contains(Action.RIGHT)) stopHorizontalMovement();
        if (!actions.contains(Action.UP) && !actions.contains(Action.DOWN)) stopVerticalMovement();
    }

    public void moveLeft() {
        getModel().getRigidBody().setVelocity(new Vector(-1, getModel().getRigidBody().getVelocity().getY()));
    }

    public void moveRight() {
        getModel().getRigidBody().setVelocity(new Vector(1, getModel().getRigidBody().getVelocity().getY()));
    }

    public void moveUp() {
        getModel().getRigidBody().setVelocity(new Vector(getModel().getRigidBody().getVelocity().getX(), -1));
    }

    public void moveDown() {
        getModel().getRigidBody().setVelocity(new Vector(getModel().getRigidBody().getVelocity().getX(), 1));
    }

    public void stopHorizontalMovement() {
        getModel().getRigidBody().setVelocity(new Vector(0, getModel().getRigidBody().getVelocity().getY()));
    }

    public void stopVerticalMovement() {
        getModel().getRigidBody().setVelocity(new Vector(getModel().getRigidBody().getVelocity().getX(), 0));
    }
}