package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class MineralModel extends ElementModel {

    private final MineralType type = MineralType.BLUE;
    private final PointingDirection direction;

    public MineralModel(Position position, String imageColor) {
        super(position);
        this.direction = directionParser(imageColor);
        System.out.println(direction);
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
}
