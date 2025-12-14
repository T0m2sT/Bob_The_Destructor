package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;

public class Size extends Spatial<Integer> {
    public Size(Number x, Number y) {
        super(x, y, Number::intValue);
    }

    public Size(Spatial<?> spatial) {
        super(spatial, Number::intValue);
    }
}
