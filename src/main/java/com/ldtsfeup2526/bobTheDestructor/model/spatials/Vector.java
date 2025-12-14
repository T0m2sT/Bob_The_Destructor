package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;

public class Vector extends Spatial<Float> {
    public Vector(float x, float y) {
        super(x, y);
    }

    public Vector(Spatial<?> spatial) {
        super(spatial, Number::floatValue);
    }
}
