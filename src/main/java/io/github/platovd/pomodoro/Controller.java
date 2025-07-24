package io.github.platovd.pomodoro;

import io.github.platovd.pomodoro.model.SessionController;
import io.github.platovd.pomodoro.model.elements.TimerSegmentData;
import io.github.platovd.pomodoro.utils.Constants;
import io.github.platovd.pomodoro.utils.ControllerStatus;
import io.github.platovd.pomodoro.view.ISettingsPaneBuilder;
import io.github.platovd.pomodoro.view.ITimerPaneBuilder;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class Controller {
    private ControllerStatus status;
    private final Stage stage;
    private Scene mainScene;
    private final Pane timerPane;
    private final Pane settingsPane;
    private final SessionController sessionController;
    private final AnimationTimer pomodoroTimerAnimation;

    private final Consumer<String> timerViewChanger;
    private final Consumer<String> sessionStatusViewChanger;
    private final BiConsumer<Integer, Integer> progressBarViewChanger;

    private final Supplier<String> focusMinutesDataGetter;
    private final Supplier<String> focusSecondsDataGetter;
    private final Supplier<String> restMinutesDataGetter;
    private final Supplier<String> restSecondsDataGetter;
    private final Supplier<Integer> focusSegmentsCountDataGetter;

    public Controller(@Autowired Stage stage,
                      @Autowired ITimerPaneBuilder timerPaneBuilder,
                      @Autowired ISettingsPaneBuilder settingsPaneBuilder,
                      @Autowired SessionController sessionController) {
        this.stage = stage;
        this.timerPane = timerPaneBuilder.getPane();
        this.settingsPane = settingsPaneBuilder.getPane();
        this.sessionController = sessionController;
        this.pomodoroTimerAnimation = createAnimationTimer();

        this.timerViewChanger = timerPaneBuilder.getTimerViewChanger();
        this.sessionStatusViewChanger = timerPaneBuilder.getSessionStatusViewChanger();
        this.progressBarViewChanger = timerPaneBuilder.getProgressBarViewChanger();

        this.focusMinutesDataGetter = settingsPaneBuilder.getFocusMinutesDataGetter();
        this.focusSecondsDataGetter = settingsPaneBuilder.getFocusSecondsDataGetter();
        this.restMinutesDataGetter = settingsPaneBuilder.getRestMinutesDataGetter();
        this.restSecondsDataGetter = settingsPaneBuilder.getRestSecondsDataGetter();
        this.focusSegmentsCountDataGetter = settingsPaneBuilder.getFocusSegmentsCountDataGetter();

        this.status = ControllerStatus.WAIT;

        setAllListenersOnViews(timerPaneBuilder, settingsPaneBuilder);
    }

    public void startApp() {
        this.mainScene = new Scene(timerPane);
        stage.setScene(mainScene);
        stage.show();
        updateDynamicViewFields();
    }

    public void stopApp() {
        stage.close();
        stage.setScene(null);
    }

    private void switchPane() {
        mainScene.setRoot(mainScene.getRoot().equals(timerPane) ? settingsPane : timerPane);
    }

    private void updateTimerView() {
        String currentTime = sessionController.getData();
        timerViewChanger.accept(currentTime);
    }

    private void updateSessionStatusView() {
        if (!status.equals(ControllerStatus.SESSION)) {
            sessionStatusViewChanger.accept(status.toString());
            return;
        }
        String sessionStatus = sessionController.getStatusString();
        sessionStatusViewChanger.accept(sessionStatus);
    }

    private void updateProgressBarView() {
        int currentSegment = sessionController.getCurrentFocusSegment();
        int countAllSegments = sessionController.getFocusSegments();
        progressBarViewChanger.accept(countAllSegments, currentSegment);
    }

    private void updateDynamicViewFields() {
        updateTimerView();
        updateSessionStatusView();
        updateProgressBarView();
    }

    private void resetData() {
        sessionController.reset();
    }

    private void processEndOfSession() {
        resetHandler();
    }


    private AnimationTimer createAnimationTimer() {
        return new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - lastUpdate < Constants.TIMER_UPDATE_PERIOD_MILLISECONDS) return;
                lastUpdate = System.currentTimeMillis();

                boolean endFlag;
                endFlag = sessionController.step();
                updateDynamicViewFields();

                if (!endFlag) processEndOfSession();
            }
        };
    }

    private void setAllListenersOnViews(ITimerPaneBuilder timerSceneBuilder, ISettingsPaneBuilder settingPaneBuilder) {
        timerSceneBuilder.setActionsOnButtons(this::startHandler, this::stopHandler, this::resetHandler, this::changePaneHandler);
        settingPaneBuilder.setOnActionsButtons(this::changePaneHandler, this::submitNewTimerSettings);
    }

    private void startHandler() {
        status = ControllerStatus.SESSION;
        sessionController.activate();
        pomodoroTimerAnimation.start();
    }

    private void stopHandler() {
        if (!status.equals(ControllerStatus.SESSION)) return;
        status = ControllerStatus.PAUSE_SESSION;
        pomodoroTimerAnimation.stop();
        updateDynamicViewFields();
    }

    private void resetHandler() {
        stopHandler();
        status = ControllerStatus.WAIT;
        resetData();
        updateDynamicViewFields();
    }

    private void changePaneHandler() {
        stopHandler();
        status = ControllerStatus.WAIT;
        switchPane();
    }

    private void submitNewTimerSettings() {
        TimerSegmentData timerFocusSegmentData;
        TimerSegmentData timerRestSegmentData;
        int segmentsCnt = focusSegmentsCountDataGetter.get();
        try {
            int focusMinutes = Integer.parseInt(focusMinutesDataGetter.get());
            int focusSeconds = Integer.parseInt(focusSecondsDataGetter.get());
            int restMinutes = Integer.parseInt(restMinutesDataGetter.get());
            int restSeconds = Integer.parseInt(restSecondsDataGetter.get());
            timerFocusSegmentData = new TimerSegmentData(focusMinutes, focusSeconds);
            timerRestSegmentData = new TimerSegmentData(restMinutes, restSeconds);
        } catch (NumberFormatException e) {
            return;
        }
        sessionController.setDataFocus(timerFocusSegmentData);
        sessionController.setDataRest(timerRestSegmentData);
        sessionController.setFocusSegmentsCnt(segmentsCnt);

        resetHandler();
        updateDynamicViewFields();
    }
}