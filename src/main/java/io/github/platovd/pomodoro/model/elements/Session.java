package io.github.platovd.pomodoro.model.elements;

public interface Session {
    int getCurrentSegmentNumber();

    int getSegmentNumber();

    SessionStatus getSessionStatus();

    boolean step();

    void reset();
}
