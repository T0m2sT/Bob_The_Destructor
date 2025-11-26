package com.ldtsfeup2526.bobTheDestructor.model;

import com.ldtsfeup2526.bobTheDestructor.model.elements.PickaxeModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PickaxeModelTest {
    private PickaxeModel pickaxe;

    @BeforeEach
    void setUp() {
        pickaxe = new PickaxeModel(1);
    }

    @Test
    void pickaxeGetLevelTest() {
        assert pickaxe.getLevel() == 1;
    }

    @Test
    void pickaxeSetLevelTest() {
        pickaxe.setLevel(2);
        assert pickaxe.getLevel() == 2;
    }
}