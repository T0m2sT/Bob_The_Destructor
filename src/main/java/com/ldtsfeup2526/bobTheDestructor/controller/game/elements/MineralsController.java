package com.ldtsfeup2526.bobTheDestructor.controller.game.elements;

import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

import java.util.ArrayList;
import java.util.List;

public class MineralsController {
    private final List<MineralModel> minerals = new ArrayList<>();
    private final List<MineralController> mineralControllers = new ArrayList<>();

    public MineralsController(List<MineralModel> minerals) {
        int i = 0;
        for (MineralModel currMineral : minerals) {
            this.minerals.add(i, currMineral);
            mineralControllers.add(i, new MineralController(currMineral));
            i++;
        }
    }

    public void update(Position playerPosition, List<Action> actions) {
        if (actions.contains(Action.SELECT)){
            for (int i = 0; i < minerals.size(); i++){
                if (withinArea(minerals.get(i).getPosition(), playerPosition)) {
                    mineralControllers.get(i).decreaseHealth();
                }
            }
        }
    }

    private boolean withinArea(Position mineralPosition, Position playerPosition) {
        return mineralPosition.getX() < playerPosition.getX()+12 && mineralPosition.getX() > playerPosition.getX()-12 && mineralPosition.getY() < playerPosition.getY()+12 && mineralPosition.getY() > playerPosition.getY()-12;
    }
}
