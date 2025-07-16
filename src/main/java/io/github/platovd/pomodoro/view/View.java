package io.github.platovd.pomodoro.view;

import io.github.platovd.pomodoro.model.SessionController;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class View {
    private SessionController sessionController;
    private static View instance;

    private Label statusView;
    private Label timerView;
    private AnimationTimer animationTimer = new AnimationTimer() {
        long lastUpdate = 0;

        @Override
        public void handle(long now) {
            if (System.currentTimeMillis() - lastUpdate < 1L) return;
            lastUpdate = System.currentTimeMillis();
            if (!sessionController.isActive()) return;
            timerView.setText(sessionController.getData());
            if (!sessionController.step()) {
                stop();
                sessionController.reset();
            }
        }
    };
    private Scene scene;

    private View(SessionController sessionController) {
        this.sessionController = sessionController;
        buildScene();
    }

    public static View getInstance(SessionController sessionController) {
        if (instance == null) instance = new View(sessionController);
        return instance;
    }

    public void start() {
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }

    private void buildScene() {
        BorderPane pane = new BorderPane();

        statusView = new Label("");

        pane.topProperty().set(statusView);

        Button buttonStart = new Button("Start");
        Button buttonStop = new Button("Stop");
        ToolBar toolBar = new ToolBar(buttonStart, buttonStop);

        buttonStart.setOnAction(e -> {
            start();
            sessionController.setActive(true);
        });

        buttonStop.setOnAction(e ->
        {
            stop();
        });

        pane.bottomProperty().set(toolBar);

        timerView = new Label();
        pane.centerProperty().set(timerView);
        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }
}
