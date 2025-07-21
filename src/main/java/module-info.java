module io.github.platovd.pomodoro {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.beans;


    opens io.github.platovd.pomodoro to javafx.fxml;
    exports io.github.platovd.pomodoro;
    exports io.github.platovd.pomodoro.model.elements;
    opens io.github.platovd.pomodoro.model.elements to javafx.fxml;
}