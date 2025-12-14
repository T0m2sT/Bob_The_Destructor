package com.ldtsfeup2526.bobTheDestructor.controller.game.elements;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.MineralModel;

import java.util.List;

public class MineralController extends Controller<MineralModel> {
    public MineralController(MineralModel mineral) {
        super(mineral);
    }

    @Override
    public void update(Game game, List<Action> actions) {
    }

    public void decreaseHealth(){
        getModel().setHealth(getModel().getHealth() - 1);
    }
}
