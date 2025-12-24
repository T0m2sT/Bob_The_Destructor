package com.ldtsfeup2526.bobTheDestructor.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnimationTest {
    @Test
    void testUpdateLoop() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, true);
        
        assertEquals(0, anim.getFrame());
        anim.update(0.05);
        assertEquals(0, anim.getFrame());
        anim.update(0.06);
        assertEquals(1, anim.getFrame());
        anim.update(0.1);
        assertEquals(0, anim.getFrame());
    }

    @Test
    void testUpdateNoLoop() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, false);
        
        anim.update(0.2);
        assertEquals(1, anim.getFrame());
        assertTrue(anim.isFinished());
        
        anim.update(0.1);
        assertEquals(1, anim.getFrame());
    }

    @Test
    void testCooldown() {
        Sprite s1 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1}, 0.1, true);
        anim.setCooldownTime(0.5);
        
        anim.update(0.11);
        assertEquals(0, anim.getFrame());
        
        anim.update(0.2);
        anim.update(0.31);
        
        anim.update(0.01);
        assertEquals(0, anim.getFrame());
    }

    @Test
    void testCopyAndStop() {
        Sprite s1 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1}, 0.1, true);
        anim.setCooldownTime(0.5);
        anim.update(0.2);
        anim.stop();
        assertEquals(0, anim.getFrame());
        
        Animation copy = anim.copy();
        assertEquals("test", copy.getAnimName());
        assertEquals(1, copy.getSprites().length);
        assertEquals(0.5, copy.getCooldownTime());
    }

    @Test
    void testIsFinished() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        
        Animation animLoop = new Animation("test", new Sprite[]{s1, s2}, 0.1, true);
        animLoop.update(0.15);
        assertFalse(animLoop.isFinished());
        
        Animation animNoLoop = new Animation("test", new Sprite[]{s1, s2}, 0.1, false);
        animNoLoop.update(0.05);
        assertFalse(animNoLoop.isFinished());
        animNoLoop.update(0.1);
        assertTrue(animNoLoop.isFinished());
    }
}
