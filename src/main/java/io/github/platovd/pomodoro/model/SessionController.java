package io.github.platovd.pomodoro.model;

import io.github.platovd.pomodoro.model.elements.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SessionController {
    private boolean isActive = false;

    private Session session;
    private Timer timer = new DefaultTimer();


    private TimerSegmentData dataFocus;
    private TimerSegmentData dataRest;


    public SessionController(@Autowired Session session, @Qualifier("focusTimerSegmentData") TimerSegmentData dataFocus, @Qualifier("restTimerSegmentData") TimerSegmentData dataRest) {
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

    public int getCurrentFocusSegment() {
        return session.getCurrentSegmentNumber();
    }

    public String getStatusString() {
        if (session.getSessionStatus().equals(SessionStatus.FOCUS)) return "Период фокусировки";
        return "Период отдыха";
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
    }

    public void disActivate() {
        isActive = false;
    }

    public void reset() {
        session.reset();
        updateTimer();
    }

    public int getFocusSegments() {
        return session.getSegmentNumber();
    }

    public void setDataFocus(TimerSegmentData dataFocus) {
        this.dataFocus = dataFocus;
    }

    public void setDataRest(TimerSegmentData dataRest) {
        this.dataRest = dataRest;
    }

    public void setFocusSegmentsCnt(int focusSegmentsCnt) {
        session = new DefaultSession(focusSegmentsCnt);
    }
}
