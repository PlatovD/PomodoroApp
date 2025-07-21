package io.github.platovd.pomodoro.model.elements;

public class DefaultSession implements Session {
    private final int targetFocusSegments;
    private int currentSegment;
    private SessionStatus sessionStatus = SessionStatus.FOCUS;

    public DefaultSession(int targetFocusSegments) {
        this.targetFocusSegments = targetFocusSegments;
    }

    @Override
    public int getCurrentSegmentNumber() {
        return currentSegment;
    }

    @Override
    public int getSegmentNumber() {
        return targetFocusSegments;
    }

    @Override
    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @Override
    public boolean step() {
        if (sessionStatus.equals(SessionStatus.FOCUS)) {
            sessionStatus = SessionStatus.REST;
        } else {
            sessionStatus = SessionStatus.FOCUS;
            currentSegment++;
        }
        return currentSegment + 1 != targetFocusSegments || sessionStatus.equals(SessionStatus.FOCUS);
    }

    @Override
    public void reset() {
        currentSegment = 0;
        sessionStatus = SessionStatus.FOCUS;
    }
}
