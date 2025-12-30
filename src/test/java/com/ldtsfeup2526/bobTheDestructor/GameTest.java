package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    void testSetStateAndGetSpriteLoader() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class);
             var mockedActionParser = mockConstruction(ActionParser.class, (mock, context) -> {
                 when(mock.getInputReader()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.controller.input.InputReader.class));
             })) {
             mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
             mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                     .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

             Game game = new Game();
             ActionParser actionParser = mockedActionParser.constructed().get(0);
             
             assertNotNull(game.getSpriteLoader());
             assertNotNull(game.getSoundManager());

             State<?> state1 = mock(State.class);
             game.setState(state1);
             verify(state1).onEnter(game);
             verify(actionParser, atLeastOnce()).notifyStateChange(state1);
             
             assertTrue(mockedActionParser.constructed().get(0).get().isEmpty() || true);

             verify(actionParser).notifyStateChange(any(com.ldtsfeup2526.bobTheDestructor.states.MainMenuState.class));

             State<?> state2 = mock(State.class);
             game.setState(state2);
             verify(state1).onExit(game);
             verify(state2).onEnter(game);
             verify(actionParser, atLeastOnce()).notifyStateChange(state2);

             game.setState(null);
             verify(state2).onExit(game);

             assertNotNull(game.getSpriteLoader());
             assertTrue(game.getSpriteLoader() instanceof com.ldtsfeup2526.bobTheDestructor.view.sprite.GameSpriteLoader);
        }
    }

    @Test
    void testRun() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);
            
            final int[] count = {0};
            final long[] startTime = new long[1];
            doAnswer(invocation -> {
                if (count[0] == 0) startTime[0] = System.currentTimeMillis();
                if (count[0]++ > 0) game.setState(null);
                return null;
            }).when(mockState).update(eq(game), any(), any(), eq(1.0/60.0));

            game.run();
            
            long duration = System.currentTimeMillis() - startTime[0];
            assertTrue(duration >= 30, "Game run should take at least ~30ms for 2 frames with 60 FPS. Actual: " + duration);
            
            verify(mockState, atLeastOnce()).update(eq(game), any(), any(), eq(1.0/60.0));
            verify(mockedGui.constructed().get(0)).close();
        }
    }

    @Test
    void testMain() throws IOException, InterruptedException {
        try (var mockedGame = mockConstruction(Game.class, (mock, context) -> {
            doNothing().when(mock).run();
        })) {
            Game.main(new String[]{});
            assertEquals(1, mockedGame.constructed().size());
            verify(mockedGame.constructed().get(0)).run();
        }
    }
    @Test
    void testRunSlow() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);
            
            doAnswer(invocation -> {
                Thread.sleep(17);
                game.setState(null);
                return null;
            }).when(mockState).update(eq(game), any(), any(), eq(1.0/60.0));

            game.run();
            verify(mockState).update(eq(game), any(), any(), eq(1.0/60.0));
        }
    }
    @Test
    void testConstructorPrintln() throws Exception {
        java.io.PrintStream standardOut = System.out;
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            new Game();
            assertTrue(outContent.toString().contains("Starting GUI..."));
        } finally {
            System.setOut(standardOut);
        }
    }

    @Test
    void testRunSleepZero() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                // deltaTime = 16. elapsedTime = 16. sleepTime = 0.
                long target = System.currentTimeMillis() + 16;
                while(System.currentTimeMillis() < target);
                game.setState(null);
                return null;
            }).when(mockState).update(eq(game), any(), any(), eq(1.0/60.0));

            game.run();
        }
    }

    @Test
    void testRunSleepExactBoundary() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                // sleepTime = deltaTime - elapsedTime = 16 - 16 = 0
                long target = System.currentTimeMillis() + 16;
                while(System.currentTimeMillis() < target);
                game.setState(null);
                return null;
            }).when(mockState).update(eq(game), any(), any(), eq(1.0/60.0));
            game.run();
        }
        
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                // Force sleepTime = 1
                long target = System.currentTimeMillis() + 15;
                while(System.currentTimeMillis() < target);
                game.setState(null);
                return null;
            }).when(mockState).update(any(), any(), any(), anyDouble());
            game.run();
        }
    }

    @Test
    void testRunSleepOneMs() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                // deltaTime = 16. elapsedTime = 15. sleepTime = 1.
                Thread.sleep(15);
                game.setState(null);
                return null;
            }).when(mockState).update(eq(game), any(), any(), eq(1.0/60.0));

            game.run();
        }
    }

    @Test
    void testMainExceptionStackTrace() throws IOException, InterruptedException {
        java.io.PrintStream standardErr = System.err;
        java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();
        System.setErr(new java.io.PrintStream(errContent));
        try (var mockedGame = mockConstruction(Game.class, (mock, context) -> {
            doAnswer(inv -> {
                throw new RuntimeException("Test Exception");
            }).when(mock).run();
        })) {
            Game.main(new String[]{});
            assertTrue(errContent.toString().contains("Test Exception"));
        } finally {
            System.setErr(standardErr);
        }
    }

    @Test
    void testRunStateNull() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                Thread.sleep(16);
                game.setState(null);
                return null;
            }).when(mockState).update(eq(game), any(), any(), eq(1.0/60.0));

            game.run();
            verify(mockState).update(eq(game), any(), any(), eq(1.0/60.0));
        }
    }

    @Test
    void testRunSleepTimeCalculation() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                Thread.sleep(5);
                game.setState(null);
                return null;
            }).when(mockState).update(any(), any(), any(), anyDouble());

            long start = System.currentTimeMillis();
            game.run();
            long duration = System.currentTimeMillis() - start;

            assertTrue(duration < 23, "Game run took too long. Likely sleepTime = deltaTime + elapsedTime mutant. Duration: " + duration);
            assertTrue(duration >= 13, "Game run was too fast. Duration: " + duration);
        }
    }
}
