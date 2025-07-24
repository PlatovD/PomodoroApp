package io.github.platovd.pomodoro.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class TimerPaneBuilder implements ITimerPaneBuilder {
    private BorderPane pane;

    private Label statusView;
    private Label timerView;

    private Button buttonStart;
    private Button buttonStop;
    private Button buttonReset;
    private Button buttonSettings;

    private HBox sessionProgressView;

    public TimerPaneBuilder() {
        buildPane();
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    private void initSessionProgressBlock() {
        sessionProgressView = new HBox();
        sessionProgressView.setSpacing(30);
        sessionProgressView.setAlignment(Pos.CENTER);
    }

    private void buildPane() {
        initSessionProgressBlock();

        pane = new BorderPane();
        pane.setStyle("-fx-background-color: #1E1A2F;");

        statusView = new Label("");
        statusView.setFont(new Font(18));
        statusView.setTextFill(Paint.valueOf("#fff"));
        HBox.setHgrow(statusView, Priority.ALWAYS);
        statusView.setAlignment(Pos.CENTER);

        buttonStart = new Button();
        ViewUtils.setGraphicsOnButton(buttonStart, "/play.png");
        buttonStop = new Button();
        ViewUtils.setGraphicsOnButton(buttonStop, "/stop.png");
        buttonReset = new Button();
        ViewUtils.setGraphicsOnButton(buttonReset, "/reset.png");
        buttonSettings = new Button();
        ViewUtils.setGraphicsOnButton(buttonSettings, "/settings.png");

        HBox toolBarBox = new HBox(buttonStart, buttonStop, buttonReset);
        toolBarBox.setAlignment(Pos.TOP_CENTER);
        toolBarBox.setSpacing(10);

        timerView = new Label();
        timerView.setFont(new Font(40));
        timerView.setTextFill(Paint.valueOf("#fff"));

        Region topRegion = new Region();
        HBox.setHgrow(topRegion, Priority.ALWAYS);
        HBox topBox = new HBox(statusView, topRegion, buttonSettings);

        Region bottomRegion = new Region();
        HBox.setHgrow(bottomRegion, Priority.ALWAYS);
        HBox bottomBox = new HBox(sessionProgressView, bottomRegion, toolBarBox);

        pane.topProperty().set(topBox);
        pane.centerProperty().set(timerView);
        pane.bottomProperty().set(bottomBox);
    }

    @Override
    public Consumer<String> getTimerViewChanger() {
        return timerView::setText;
    }

    @Override
    public Consumer<String> getSessionStatusViewChanger() {
        return statusView::setText;
    }

    @Override
    public BiConsumer<Integer, Integer> getProgressBarViewChanger() {
        return this::updateProgressBarFromData;
    }

    @Override
    public void setActionsOnButtons(Runnable startButton, Runnable stopButton, Runnable resetButton, Runnable settingsButton) {
        buttonStart.setOnAction(e -> startButton.run());
        buttonStop.setOnAction(e -> stopButton.run());
        buttonReset.setOnAction(e -> resetButton.run());
        buttonSettings.setOnAction(e -> settingsButton.run());
    }

    private Rectangle createRectForProgressBar(boolean isActive) {
        Rectangle rect = new Rectangle(10, 10);
        rect.setStyle("-fx-fill: " + (isActive ? "#7B68EE" : "#d6d2ef") + ";" + "-fx-arc-height: 5px; -fx-arc-width: 5px");
        return rect;
    }


    private void updateProgressBarFromData(int overageSegmentsCnt, int currentSegment) {
        sessionProgressView.getChildren().clear();
        Region margin = new Region();
        margin.minWidth(10);
        sessionProgressView.getChildren().add(margin);
        for (int i = 0; i < overageSegmentsCnt; i++)
            sessionProgressView.getChildren().add(createRectForProgressBar(currentSegment >= i));
    }
}
