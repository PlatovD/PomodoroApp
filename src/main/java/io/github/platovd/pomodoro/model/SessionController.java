package io.github.platovd.pomodoro.model;

import io.github.platovd.pomodoro.model.elements.*;

public class SessionController {
    private boolean isActive = false;

    private Session session;
    private Timer timer = new DefaultTimer();


    private TimerSegmentData dataFocus;
    private TimerSegmentData dataRest;


    public SessionController(Session session, TimerSegmentData dataFocus, TimerSegmentData dataRest) {
        this.session = session;
        this.dataFocus = dataFocus;
        this.dataRest = dataRest;
        updateTimer();
    }


    private void updateTimer() {
        if (session.getSessionStatus().equals(SessionStatus.FOCUS))
            timer.setData(dataFocus);
        else
            timer.setData(dataRest);
        timer.start();
    }

    public boolean step() {
        if (!timer.step()) {
            if (!session.step()) {
                return false;
            }
            updateTimer();
        }

        return true;
    }

    public String getData() {
        return String.format("%02d : %02d", timer.getMinutes(), timer.getSeconds());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void reset() {
        session.reset();
        updateTimer();
    }
}
