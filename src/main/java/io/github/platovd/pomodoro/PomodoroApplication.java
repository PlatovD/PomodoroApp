package io.github.platovd.pomodoro;

import io.github.platovd.pomodoro.spring.Config;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class PomodoroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Controller controller = context.getBean("controller", Controller.class);
        controller.startApp();
    }

    public static void main(String[] args) {
        launch();
    }
}