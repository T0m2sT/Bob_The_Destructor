package com.ldtsfeup2526.bobTheDestructor.states;

public interface IGameStateObserver {

    void notifyStateChange(State<?> state);
}
