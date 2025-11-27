package com.ldtsfeup2526.bobTheDestructor.view.element;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.elements.ElementModel;

public interface ElementView<T extends ElementModel> {
    void draw(T element, GUI gui);
}