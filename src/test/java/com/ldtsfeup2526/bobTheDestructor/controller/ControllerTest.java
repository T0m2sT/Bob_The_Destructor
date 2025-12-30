package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {
    @Test
    void testGetModel() {
        Object model = new Object();
        Controller<Object> controller = new Controller<Object>(model) {
            @Override
            public void update(com.ldtsfeup2526.bobTheDestructor.Game game, List<Action> actions, double deltaTime) throws IOException {}
        };
        assertEquals(model, controller.getModel());
    }
}
