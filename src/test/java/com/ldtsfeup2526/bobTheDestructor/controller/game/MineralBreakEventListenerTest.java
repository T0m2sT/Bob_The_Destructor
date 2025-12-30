package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MineralBreakEventListenerTest {

    @Test
    void testOnMineralBreakCalled() {
        MineralModel mineralModel = new MineralModel(new Position(0, 0), "fffdfe89", 0);
        MineralBreakEventListener listener = mock(MineralBreakEventListener.class);
        
        mineralModel.addMineralBreakEventListener(listener);
        
        mineralModel.notifyWhenAnimFinished("CrackAnim");
        
        verify(listener, times(1)).onMineralBreak(mineralModel);
    }

    @Test
    void testMultipleListeners() {
        MineralModel mineralModel = new MineralModel(new Position(0, 0), "fffdfe89", 0);
        MineralBreakEventListener listener1 = mock(MineralBreakEventListener.class);
        MineralBreakEventListener listener2 = mock(MineralBreakEventListener.class);
        
        mineralModel.addMineralBreakEventListener(listener1);
        mineralModel.addMineralBreakEventListener(listener2);
        
        mineralModel.notifyWhenAnimFinished("CrackAnim");
        
        verify(listener1, times(1)).onMineralBreak(mineralModel);
        verify(listener2, times(1)).onMineralBreak(mineralModel);
    }

    @Test
    void testRemoveListener() {
        MineralModel mineralModel = new MineralModel(new Position(0, 0), "fffdfe89", 0);
        MineralBreakEventListener listener = mock(MineralBreakEventListener.class);
        
        mineralModel.addMineralBreakEventListener(listener);
        mineralModel.removeMineralBreakEventListener(listener);
        
        mineralModel.notifyWhenAnimFinished("CrackAnim");
        
        verify(listener, never()).onMineralBreak(mineralModel);
    }

    @Test
    void testOnMineralBreakNotCalledForOtherAnim() {
        MineralModel mineralModel = new MineralModel(new Position(0, 0), "fffdfe89", 0);
        MineralBreakEventListener listener = mock(MineralBreakEventListener.class);
        
        mineralModel.addMineralBreakEventListener(listener);
        
        mineralModel.notifyWhenAnimFinished("OtherAnim");
        
        verify(listener, never()).onMineralBreak(mineralModel);
    }
}
