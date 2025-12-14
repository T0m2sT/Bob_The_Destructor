package com.ldtsfeup2526.bobTheDestructor.model;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class Spatial<T extends Number> {
    private final T x;
    private final T y;
    public Spatial(Number x, Number y, Function<Number, T> converter) {
        this.x = converter.apply(x);
        this.y = converter.apply(y);
    }

    public Spatial(Spatial<? extends Number> spatial, Function<Number, T> converter) {
        this.x = converter.apply(spatial.getX());
        this.y = converter.apply(spatial.getY());
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public void print() {
        System.out.println((x.toString() + ", " + y.toString()));
    }

    public double magnitude() {
        float x = getX().floatValue();
        float y = getY().floatValue();
        return Math.sqrt(x*x + y*y);
    }

}
