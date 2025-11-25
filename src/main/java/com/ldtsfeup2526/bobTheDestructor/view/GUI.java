package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.io.IOException;

public abstract class GUI {
    public abstract void drawMiner(Position position);

    public abstract void drawElement(Position position);

    public abstract void drawText(Position position, String text, String color);

    public abstract void clear();

    public abstract void refresh() throws IOException;

    public abstract void close() throws IOException;
}
