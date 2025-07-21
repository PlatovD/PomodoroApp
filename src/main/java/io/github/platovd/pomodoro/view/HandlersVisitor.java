package io.github.platovd.pomodoro.view;

import javafx.scene.control.Button;

public interface HandlersVisitor {
    void visit(Button start, Button stop, Button reset, Button openSettings);

    void visit(Button applySettings, Button openTimer);
}
