package io.github.platovd.pomodoro.model.elements;

public class DefaultTimer implements Timer {
    private int minutes;
    private int seconds;
    private boolean isActive = false;

    public DefaultTimer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public DefaultTimer() {
    }

    @Override
    public void start() {
        isActive = true;
    }

    @Override
    public void stop() {
        isActive = false;
    }

    @Override
    public boolean step() {
        if (!isActive) return true;

        if (minutes + seconds == 0) {
            isActive = false;
            return false;
        }

        if (seconds == 0) {
            minutes -= 1;
            seconds = 59;
        }

        seconds -= 1;

        return true;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getSeconds() {
        return seconds;
    }

    @Override
    public void setData(TimerSegmentData data) {
        minutes = data.getMinutes();
        seconds = data.getSeconds();
    }
}
