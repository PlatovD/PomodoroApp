package io.github.platovd.pomodoro.view;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ISettingsPaneBuilder extends PaneBuilder {
    Supplier<String> getFocusMinutesDataGetter();

    Supplier<String> getFocusSecondsDataGetter();

    Supplier<String> getRestMinutesDataGetter();

    Supplier<String> getRestSecondsDataGetter();

    Supplier<Integer> getFocusSegmentsCountDataGetter();

    void setOnActionsButtons(Runnable timerButton, Runnable submitButton);
}
