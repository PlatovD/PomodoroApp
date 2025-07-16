package io.github.platovd.pomodoro.model.elements;

public interface Timer {
    void start();

    void stop();

    boolean step();

    int getMinutes();

    int getSeconds();

    void setData(TimerSegmentData data);
}
