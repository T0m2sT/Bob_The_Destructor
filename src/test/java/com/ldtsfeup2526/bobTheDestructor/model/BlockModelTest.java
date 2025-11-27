package com.ldtsfeup2526.bobTheDestructor.model;

import com.ldtsfeup2526.bobTheDestructor.model.elements.BlockModel;
import org.junit.jupiter.api.Test;

public class BlockModelTest {
    private BlockModel block;

    @Test
    void blockGetTypeTest() {
        block = new BlockModel(new Position(1,2), BlockModel.Type.DIAMOND);
        assert block.getType() == BlockModel.Type.DIAMOND;
    }

    @Test
    void blockGetPositionTest() {
        block = new BlockModel(new Position(1,2), BlockModel.Type.DIAMOND);
        assert block.getPosition().getX() == 1;
        assert block.getPosition().getY() == 2;
    }

    @Test
    void blockSetPositionTest() {
        block = new BlockModel(new Position(1,2), BlockModel.Type.DIAMOND);
        block.setPosition(new Position(3,4));
        assert block.getPosition().getX() == 3;
        assert block.getPosition().getY() == 4;
    }

    @Test
    void blockGetDurabilityTest() {
        block = new BlockModel(new Position(1,2), BlockModel.Type.DIAMOND);
        assert block.getDurabilty() == 10;
    }

    @Test
    void blockSetDurabilityTest() {
        block = new BlockModel(new Position(1,2), BlockModel.Type.DIAMOND);
        block.setDurability(8);
        assert block.getDurabilty() == 8;
    }
}