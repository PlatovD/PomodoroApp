package io.github.platovd.pomodoro;

import io.github.platovd.pomodoro.model.SessionController;
import io.github.platovd.pomodoro.utils.ControllerStatus;
import io.github.platovd.pomodoro.view.ITimerSceneBuilder;
import io.github.platovd.pomodoro.view.SceneBuilder;
import io.github.platovd.pomodoro.view.SettingsSceneBuilder;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class Controller {
    private ControllerStatus status;
    private final Stage stage;
    private final Scene timerScene;
    private final Scene settingsScene;
    private final SessionController sessionController;
    private AnimationTimer pomodoroTimerAnimation;

    private final Consumer<String> timerViewChanger;
    private final Consumer<String> sessionStatusViewChanger;
    private final BiConsumer<Integer, Integer> progressBarViewChanger;

    public Controller(@Autowired Stage stage,
                      @Autowired ITimerSceneBuilder timerSceneBuilder,
                      @Autowired SettingsSceneBuilder settingsSceneBuilder,
                      @Autowired SessionController sessionController) {
        this.stage = stage;
        this.timerScene = timerSceneBuilder.getScene();
        this.settingsScene = settingsSceneBuilder.getScene();
        this.sessionController = sessionController;
        this.pomodoroTimerAnimation = createAnimationTimer();

        this.timerViewChanger = timerSceneBuilder.getTimerViewChanger();
        this.sessionStatusViewChanger = timerSceneBuilder.getSessionStatusViewChanger();
        this.progressBarViewChanger = timerSceneBuilder.getProgressBarViewChanger();
        this.status = ControllerStatus.WAIT;

        setAllListenersOnViews(timerSceneBuilder, settingsSceneBuilder);
    }

    public void startApp() {
        stage.setScene(timerScene);
        stage.show();
        updateDynamicViewFields();
    }

    public void stopApp() {
        stage.close();
        stage.setScene(null);
    }

    private void switchScene() {
        stage.setScene(stage.getScene().equals(timerScene) ? settingsScene : timerScene);
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


    private AnimationTimer createAnimationTimer() {
        return new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - lastUpdate < 1000L) return;
                lastUpdate = System.currentTimeMillis();

                boolean endFlag;
                endFlag = sessionController.step();
                updateDynamicViewFields();

                if (!endFlag) resetData();
            }
        };
    }

    private void setAllListenersOnViews(ITimerSceneBuilder timerSceneBuilder, SceneBuilder settingSceneBuilder) {
        timerSceneBuilder.setActionsOnButtons(this::startHandler, this::stopHandler, this::resetHandler, this::changeSceneHandler);
    }

    private void startHandler() {
        status = ControllerStatus.SESSION;
        sessionController.activate();
        pomodoroTimerAnimation.start();
    }

    private void stopHandler() {
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

    private void changeSceneHandler() {
        status = ControllerStatus.WAIT;
        switchScene();
    }
}