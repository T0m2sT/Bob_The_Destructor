package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;

public class Position extends Spatial<Integer> {

    public Position(Number x, Number y) {
        super(x, y, Number::intValue);
    }

    public Position(Spatial<?> spatial) {
        super(spatial, Number::intValue);
    }
}
