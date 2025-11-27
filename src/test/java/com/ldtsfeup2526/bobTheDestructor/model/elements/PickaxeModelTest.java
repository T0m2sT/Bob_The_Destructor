package com.ldtsfeup2526.bobTheDestructor.model.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PickaxeModelTest {
    private PickaxeModel pickaxe;

    @BeforeEach
    void setUp() {
        pickaxe = new PickaxeModel(PickaxeModel.Type.IRON);
    }

    @Test
    void pickaxeGetDamageTest() {
        assert pickaxe.getDamage() == 1;
    }

    @Test
    void pickaxeGetDamageAfterSetTypeTest() {
        pickaxe.setType(PickaxeModel.Type.DIAMOND);
        assert pickaxe.getDamage() == 3;
    }

    @Test
    void pickaxeGetTypeTest() {
        assert pickaxe.getType() == PickaxeModel.Type.IRON;
    }

    @Test
    void pickaxeSetTypeTest() {
        pickaxe.setType(PickaxeModel.Type.DIAMOND);
        assert pickaxe.getType() == PickaxeModel.Type.DIAMOND;
    }
}