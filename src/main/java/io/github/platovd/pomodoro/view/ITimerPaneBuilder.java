package io.github.platovd.pomodoro.view;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ITimerPaneBuilder extends PaneBuilder {
    Consumer<String> getTimerViewChanger();

    Consumer<String> getSessionStatusViewChanger();

    BiConsumer<Integer, Integer> getProgressBarViewChanger();

    void setActionsOnButtons(Runnable startButton, Runnable stopButton, Runnable resetButton, Runnable settingsButton);
}
