package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;

public class Vector extends Spatial<Float> {
    public Vector(Number x, Number y) {
        super(x, y, Number::floatValue);
    }

    public Vector(Spatial<?> spatial) {
        super(spatial, Number::floatValue);
    }
}
