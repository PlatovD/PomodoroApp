package io.github.platovd.pomodoro;

import io.github.platovd.pomodoro.model.SessionController;
import io.github.platovd.pomodoro.view.SettingsSceneBuilder;
import io.github.platovd.pomodoro.view.TimerSceneBuilder;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {
    private final Stage stage;
    private final Scene timerScene;
    private final Scene settingsScene;
    private final SessionController sessionController;
    private AnimationTimer pomodoroTimerAnimation;

    public Controller(@Autowired Stage stage,
                      @Autowired TimerSceneBuilder timerScene,
                      @Autowired SettingsSceneBuilder settingsScene,
                      @Autowired SessionController sessionController) {
        this.stage = stage;
        this.timerScene = timerScene.getScene();
        this.settingsScene = settingsScene.getScene();
        this.sessionController = sessionController;
        this.pomodoroTimerAnimation = createAnimationTimer();
    }

    public void startApp() {
        stage.setScene(settingsScene);
        stage.show();
    }

    public void stopApp() {
        stage.close();
        stage.setScene(null);
    }

    private void startTimerHandler() {
        sessionController.activate();
        pomodoroTimerAnimation.start();
    }

    public void switchScene() {
        stage.setScene(stage.getScene().equals(timerScene) ? settingsScene : timerScene);
    }

    private void updateTimerView() {
        String currentTime = sessionController.getData();

    }

    private void updateSessionStatusView() {
        String sessionStatus = sessionController.getStatusString();
    }

    private void updateProgressBarView() {
        int currentSegment = sessionController.getCurrentFocusSegment();
        int countAllSegments = sessionController.getFocusSegments();


    }

    private void updateDynamicViewFields() {
        updateTimerView();
        updateSessionStatusView();
        updateProgressBarView();
    }

    private AnimationTimer createAnimationTimer() {
        return new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - lastUpdate < 1000L) return;
                lastUpdate = System.currentTimeMillis();

                boolean isEnd = false;
                isEnd = sessionController.step();
                updateDynamicViewFields();

                if (isEnd) // i must reset all the view and data there
            }
        };
    }

}