package io.github.platovd.pomodoro.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

@Component
public class TimerSceneBuilder implements SceneBuilder {
    private Scene scene;

    private Label statusView;
    private Label timerView;

    private Button buttonStart;
    private Button buttonStop;
    private Button buttonReset;

    private HBox sessionProgressView;

    @Override
    public Scene getScene() {
        return scene;
    }

    public void initSessionProgressBlock() {
        sessionProgressView = new HBox();
        HBox.setHgrow(sessionProgressView, Priority.ALWAYS);
        sessionProgressView.setSpacing(30);
        sessionProgressView.setAlignment(Pos.CENTER);
    }

    private void buildScene() {
        initSessionProgressBlock();

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #1E1A2F;");

        statusView = new Label("");
        statusView.setFont(new Font(18));
        statusView.setTextFill(Paint.valueOf("#fff"));
        HBox.setHgrow(statusView, Priority.ALWAYS);
        statusView.setAlignment(Pos.CENTER);

        pane.topProperty().set(statusView);

        buttonStart = new Button();
        ViewUtils.setGraphicsOnButton(buttonStart, "/play.png");
        buttonStop = new Button();
        ViewUtils.setGraphicsOnButton(buttonStop, "/stop.png");
        buttonReset = new Button();
        ViewUtils.setGraphicsOnButton(buttonReset, "/reset.png");

        HBox toolBarBox = new HBox(buttonStart, buttonStop, buttonReset);
        toolBarBox.setAlignment(Pos.TOP_CENTER);
        toolBarBox.setSpacing(10);


        pane.bottomProperty().set(new HBox(sessionProgressView, toolBarBox));

        timerView = new Label();
        timerView.setFont(new Font(40));
        timerView.setTextFill(Paint.valueOf("#fff"));
        pane.centerProperty().set(timerView);
        scene = new Scene(pane);
    }
}
