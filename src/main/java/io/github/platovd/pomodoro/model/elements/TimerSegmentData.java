package io.github.platovd.pomodoro.model.elements;

public class TimerSegmentData {
    private final int minutes;
    private final int seconds;

    public TimerSegmentData(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}
