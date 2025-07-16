package io.github.platovd.pomodoro.model.elements;

public class DefaultSession implements Session {
    private int targetFocusSegments;
    private int currentSegment;
    private SessionStatus sessionStatus = SessionStatus.FOCUS;

    public DefaultSession(int targetFocusSegments) {
        this.targetFocusSegments = targetFocusSegments;
    }

    @Override
    public int getSegmentNumber() {
        return currentSegment;
    }

    @Override
    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @Override
    public boolean step() {
        if (sessionStatus.equals(SessionStatus.FOCUS)) {
            sessionStatus = SessionStatus.REST;
            currentSegment++;
        } else {
            sessionStatus = SessionStatus.FOCUS;
        }
        return currentSegment != targetFocusSegments;
    }

    @Override
    public void reset() {
        currentSegment = 0;
        sessionStatus = SessionStatus.FOCUS;
    }
}
