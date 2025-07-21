package io.github.platovd.pomodoro.view;

import javafx.scene.control.Button;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ButtonHandlersVisitor implements HandlersVisitor {
    @Override
    public void visit(Button start, Button stop, Button reset, Button openSettings) {
        start.setOnAction(e -> {
            start();
            sessionController.setActive(true);
        });

        stop.setOnAction(e ->
        {
            stop();
        });

        reset.setOnAction(e -> reset());
    }

    @Override
    public void visit(Button applySettings, Button openTimer) {

    }
}
