package io.github.platovd.pomodoro.model.elements;

public interface Session {
    int getSegmentNumber();

    SessionStatus getSessionStatus();

    boolean step();

    void reset();
}
