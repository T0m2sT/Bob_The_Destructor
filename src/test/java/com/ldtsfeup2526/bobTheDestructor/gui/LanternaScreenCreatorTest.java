package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LanternaScreenCreatorTest {
    @Test
    void testDefaultConstructor() {
        LanternaScreenCreator creator = new LanternaScreenCreator();
    }

    @Test
    void testCreateScreen() throws URISyntaxException, IOException, FontFormatException {
        DefaultTerminalFactory factory = mock(DefaultTerminalFactory.class);
        TerminalScreen screen = mock(TerminalScreen.class);
        AWTTerminalFrame terminalFrame = mock(AWTTerminalFrame.class);
        Component component = mock(Component.class);
        KeyListener keyListener = mock(KeyListener.class);

        when(factory.createScreen()).thenReturn(screen);
        when(screen.getTerminal()).thenReturn(terminalFrame);
        when(terminalFrame.getComponent(0)).thenReturn(component);

        LanternaScreenCreator creator = new LanternaScreenCreator(factory);
        Resolution resolution = new Resolution(100, 50);

        var result = creator.createScreen(resolution, 12, "Test Title", keyListener);

        assertEquals(screen, result);
        verify(factory).setInitialTerminalSize(new TerminalSize(100, 50));
        verify(factory).setForceAWTOverSwing(true);
        verify(factory).setTerminalEmulatorFontConfiguration(argThat(Objects::nonNull));
        verify(terminalFrame).setTitle("Test Title");
        verify(component).addKeyListener(keyListener);
    }
}
