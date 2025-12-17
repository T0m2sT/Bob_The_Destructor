package com.ldtsfeup2526.bobTheDestructor.controller.game.elements;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState;
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
        PlayerModel player = getModel();

        if (actions.contains(Action.RIGHT)) player.moveRight();
        if (actions.contains(Action.LEFT)) player.moveLeft();
        if (actions.contains(Action.JUMP) || actions.contains(Action.UP)) player.jump();
        if (actions.contains(Action.MINE) && player.getState().getClass() != MiningState.class) player.mine();

        if (!actions.contains(Action.LEFT) && !actions.contains(Action.RIGHT)) player.applyFriction();

        player.update();
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