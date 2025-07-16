package io.github.platovd.pomodoro;

import io.github.platovd.pomodoro.model.SessionController;
import io.github.platovd.pomodoro.model.elements.DefaultSession;
import io.github.platovd.pomodoro.model.elements.TimerSegmentData;
import io.github.platovd.pomodoro.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PomodoroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        View view = View.getInstance(new SessionController(new DefaultSession(4), new TimerSegmentData(25, 0), new TimerSegmentData(5, 0)));
        stage.setScene(view.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}