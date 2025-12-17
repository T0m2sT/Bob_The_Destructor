package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

import java.util.Objects;

public class MineralModel extends ElementModel {

    private final MineralType type;
    private final PointingDirection direction;
    private MineralState state = MineralState.UNSELECTED;

    public MineralModel(Position position, String imageColor, int mineralType) {
        super(position);
        this.direction = directionParser(imageColor);
        this.type = MineralType.values()[mineralType];
        //System.out.println(direction);
    }

    private PointingDirection directionParser(String imageColor) {
        return switch (imageColor) {
            case "fffdfe89" -> PointingDirection.DOWN;
            case "ffff5dcc" -> PointingDirection.LEFT;
            case "ff5efdf7" -> PointingDirection.RIGHT;
            default -> PointingDirection.UP;
        };
    }


    public MineralType getType() {
        return type;
    }

    public PointingDirection getDirection() {
        return direction;
    }

    public MineralState getState() {
        return state;
    }

    public void setState(MineralState state) {
        this.state = state;
    }

    public void notifyWhenAnimFinished(String name) {
        if (Objects.equals(name,"CrackAnim")) {
            setState(MineralState.CLEANUP);
        }
    }
}
