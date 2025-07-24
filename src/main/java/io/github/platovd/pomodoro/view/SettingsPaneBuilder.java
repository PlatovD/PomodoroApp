package io.github.platovd.pomodoro.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class SettingsPaneBuilder implements ISettingsPaneBuilder {
    private BorderPane pane;

    private TextField focusSecondsTextField;
    private TextField focusMinutesTextField;
    private TextField restSecondsTextField;
    private TextField restMinutesTextField;
    private Spinner<Integer> focusSegmentsField;

    private VBox formBox;

    private Button buttonSubmit;
    private Button buttonTimer;

    public SettingsPaneBuilder() {
        buildPane();
    }

    private void buildPane() {
        pane = new BorderPane();
        pane.setStyle("-fx-background-color: #1E1A2F;");

        buttonTimer = new Button();
        buttonSubmit = new Button();

        buildForm();

        ViewUtils.setGraphicsOnButton(buttonTimer, "/back.png");
        ViewUtils.setGraphicsOnButton(buttonSubmit, "/apply.png");

        Region topRegion = new Region();
        HBox.setHgrow(topRegion, Priority.ALWAYS);
        Region topCenterRegion = new Region();
        VBox.setVgrow(topCenterRegion, Priority.ALWAYS);
        HBox topBox = new HBox(topRegion, buttonTimer);


        pane.centerProperty().set(formBox);
        pane.topProperty().set(topBox);
    }

    private void buildForm() {
        Label labelFMin = new Label("Минуты фокусировки");
        focusMinutesTextField = new TextField("35");
        Label labelFSec = new Label("Секунды фокусировки");
        focusSecondsTextField = new TextField("0");
        Label labelRMin = new Label("Минуты отдыха");
        restMinutesTextField = new TextField("5");
        Label labelRSec = new Label("Секунды отдыха");
        restSecondsTextField = new TextField("0");
        Label labelSCount = new Label("Кол-во сегментов фокусировки");
        focusSegmentsField = new Spinner<>(1, 12, 4);
        styleInputFields(focusMinutesTextField, focusSecondsTextField, restMinutesTextField, restSecondsTextField);

        Region centerBottomRegion = new Region();
        HBox.setHgrow(centerBottomRegion, Priority.ALWAYS);
        HBox centerBottomBox = new HBox(centerBottomRegion, buttonSubmit);

        formBox = new VBox(
                labelFMin,
                focusMinutesTextField,
                labelFSec,
                focusSecondsTextField,
                labelRMin,
                restMinutesTextField,
                labelRSec,
                restSecondsTextField,
                labelSCount,
                focusSegmentsField,
                centerBottomRegion,
                centerBottomBox
        );
        formBox.setSpacing(10);
        formBox.setStyle("-fx-max-width: 500px; -fx-background-color: #d6d2ef; -fx-background-radius: 20px; " +
                "-fx-padding: 20px; -fx-max-height: 700px");
    }

    private void styleInputFields(TextField... inputFields) {
        for (TextField inputField : inputFields) {
            inputField.setStyle(
                    "-fx-border-radius: 5px;" +
                            "-fx-background-color: #fff"
            );
        }
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public Supplier<String> getFocusMinutesDataGetter() {
        return () -> focusMinutesTextField.getText();
    }

    @Override
    public Supplier<String> getFocusSecondsDataGetter() {
        return () -> focusSecondsTextField.getText();
    }

    @Override
    public Supplier<String> getRestMinutesDataGetter() {
        return () -> restMinutesTextField.getText();
    }

    @Override
    public Supplier<String> getRestSecondsDataGetter() {
        return () -> restSecondsTextField.getText();
    }

    @Override
    public Supplier<Integer> getFocusSegmentsCountDataGetter() {
        return () -> focusSegmentsField.getValue();
    }

    @Override
    public void setOnActionsButtons(Runnable timerButton, Runnable submitButton) {
        buttonTimer.setOnAction(e -> timerButton.run());
        buttonSubmit.setOnAction(e -> submitButton.run());
    }
}
