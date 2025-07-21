package io.github.platovd.pomodoro;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class PomodoroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        Controller controller = context.getBean("controller", Controller.class);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}