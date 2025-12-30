package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralModelTest {
    @Test
    void testDirectionDown() {
        MineralModel mineral = new MineralModel(new Position(0,0), "fffdfe89", 0);
        assertEquals(PointingDirection.DOWN, mineral.getDirection());
    }

    @Test
    void testDirectionLeft() {
        MineralModel mineral = new MineralModel(new Position(0,0), "ffff5dcc", 0);
        assertEquals(PointingDirection.LEFT, mineral.getDirection());
    }

    @Test
    void testDirectionRight() {
        MineralModel mineral = new MineralModel(new Position(0,0), "ff5efdf7", 0);
        assertEquals(PointingDirection.RIGHT, mineral.getDirection());
    }

    @Test
    void testDirectionUpDefault() {
        // Standard default case
        MineralModel mUp = new MineralModel(new Position(0,0), "other", 0);
        assertEquals(PointingDirection.UP, mUp.getDirection());

        // Case sensitivity check (should fall to default)
        MineralModel mUpCase = new MineralModel(new Position(0,0), "FFFDFE89", 0);
        assertEquals(PointingDirection.UP, mUpCase.getDirection());

        // Empty string case
        MineralModel mUpEmpty = new MineralModel(new Position(0,0), "", 0);
        assertEquals(PointingDirection.UP, mUpEmpty.getDirection());

        // Random string
        MineralModel mUpRandom = new MineralModel(new Position(0,0), "12345678", 0);
        assertEquals(PointingDirection.UP, mUpRandom.getDirection());
    }

    @Test
    void testAllCombinations() {
        String[] colors = {"fffdfe89", "ffff5dcc", "ff5efdf7", "anything_else"};
        PointingDirection[] expectedDirections = {PointingDirection.DOWN, PointingDirection.LEFT, PointingDirection.RIGHT, PointingDirection.UP};
        
        for (int i = 0; i < colors.length; i++) {
            for (int typeIdx = 0; typeIdx < MineralType.values().length; typeIdx++) {
                MineralModel mineral = new MineralModel(new Position(0,0), colors[i], typeIdx);
                assertEquals(expectedDirections[i], mineral.getDirection());
                assertEquals(MineralType.values()[typeIdx], mineral.getType());
            }
        }
    }

    @Test
    void testDirectionParserNull() {
        MineralModel mineral = new MineralModel(new Position(0,0), null, 0);
        assertEquals(PointingDirection.UP, mineral.getDirection());
    }

    @Test
    void testStateAndType() {
        MineralModel mineral = new MineralModel(new Position(1,1), "fffdfe89", 1);
        assertEquals(MineralType.BLUE, mineral.getType());
        assertEquals(MineralState.UNSELECTED, mineral.getState());
        
        mineral.setState(MineralState.SELECTED);
        assertEquals(MineralState.SELECTED, mineral.getState());
    }

    @Test
    void testNotifyWhenAnimFinished() {
        MineralModel mineral = new MineralModel(new Position(0,0), "fffdfe89", 0);
        
        // Test matching animation name
        mineral.setState(MineralState.UNSELECTED);
        mineral.notifyWhenAnimFinished("CrackAnim");
        assertEquals(MineralState.CLEANUP, mineral.getState());

        // Test matching animation name with different instance but same content
        mineral.setState(MineralState.UNSELECTED);
        mineral.notifyWhenAnimFinished(new String("CrackAnim"));
        assertEquals(MineralState.CLEANUP, mineral.getState());
        
        // Test that it doesn't set to UNSELECTED or something else if it's already CLEANUP
        mineral.notifyWhenAnimFinished("CrackAnim");
        assertEquals(MineralState.CLEANUP, mineral.getState());

        // Test non-matching animation names
        mineral.setState(MineralState.DESTROYED);
        mineral.notifyWhenAnimFinished("OtherAnim");
        assertEquals(MineralState.DESTROYED, mineral.getState());
        
        mineral.notifyWhenAnimFinished("IdleAnim");
        assertEquals(MineralState.DESTROYED, mineral.getState());
        
        mineral.notifyWhenAnimFinished("crackanim"); // Case sensitivity
        assertEquals(MineralState.DESTROYED, mineral.getState());

        mineral.notifyWhenAnimFinished(null);
        assertEquals(MineralState.DESTROYED, mineral.getState());
        
        mineral.notifyWhenAnimFinished("");
        assertEquals(MineralState.DESTROYED, mineral.getState());
    }

    @Test
    void testNotifyWhenAnimFinished_FromDifferentStates() {
        MineralModel mineral = new MineralModel(new Position(0,0), "fffdfe89", 0);
        
        mineral.setState(MineralState.UNSELECTED);
        mineral.notifyWhenAnimFinished("CrackAnim");
        assertEquals(MineralState.CLEANUP, mineral.getState());
        
        mineral.setState(MineralState.SELECTED);
        mineral.notifyWhenAnimFinished("CrackAnim");
        assertEquals(MineralState.CLEANUP, mineral.getState());
    }

    @Test
    void testConstructor() {
        Position pos = new Position(10, 20);
        MineralModel mineral = new MineralModel(pos, "fffdfe89", 1);
        
        assertEquals(pos, mineral.getPosition());
        assertEquals(PointingDirection.DOWN, mineral.getDirection());
        assertEquals(MineralType.BLUE, mineral.getType());
        assertEquals(MineralState.UNSELECTED, mineral.getState());

        MineralModel mineral1 = new MineralModel(pos, "ffff5dcc", 2);

        assertEquals(pos, mineral1.getPosition());
        assertEquals(PointingDirection.LEFT, mineral1.getDirection());
        assertEquals(MineralType.YELLOW, mineral1.getType());
        assertEquals(MineralState.UNSELECTED, mineral1.getState());

        MineralModel mineral2 = new MineralModel(pos, "ff5efdf7", 0);

        assertEquals(pos, mineral2.getPosition());
        assertEquals(PointingDirection.RIGHT, mineral2.getDirection());
        assertEquals(MineralType.PINK, mineral2.getType());
        assertEquals(MineralState.UNSELECTED, mineral2.getState());

        MineralModel mineral3 = new MineralModel(pos, "anything_else", 2);

        assertEquals(pos, mineral3.getPosition());
        assertEquals(PointingDirection.UP, mineral3.getDirection());
        assertEquals(MineralType.YELLOW, mineral3.getType());
        assertEquals(MineralState.UNSELECTED, mineral3.getState());
    }

    @Test
    void testSetState_AllValues() {
        MineralModel mineral = new MineralModel(new Position(0,0), "fffdfe89", 0);
        for (MineralState state : MineralState.values()) {
            mineral.setState(state);
            assertEquals(state, mineral.getState());
        }
    }

    @Test
    void testSetPosition() {
        MineralModel mineral = new MineralModel(new Position(0,0), "fffdfe89", 0);
        Position newPos = new Position(5, 5);
        mineral.setPosition(newPos);
        assertEquals(newPos, mineral.getPosition());
    }

    @Test
    void testAllMineralTypes() {
        MineralModel pink = new MineralModel(new Position(0,0), "fffdfe89", 0);
        assertEquals(MineralType.PINK, pink.getType());

        MineralModel blue = new MineralModel(new Position(0,0), "fffdfe89", 1);
        assertEquals(MineralType.BLUE, blue.getType());

        MineralModel yellow = new MineralModel(new Position(0,0), "fffdfe89", 2);
        assertEquals(MineralType.YELLOW, yellow.getType());
    }
}
