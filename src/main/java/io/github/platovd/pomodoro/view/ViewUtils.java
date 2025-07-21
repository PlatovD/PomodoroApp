package io.github.platovd.pomodoro.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

import java.util.Objects;

public class ViewUtils {
    public static void setGraphicsOnButton(Button button, String imgPath) {
        button.setBackground(Background.EMPTY);
        Image image = new Image(Objects.requireNonNull(View.class.getResourceAsStream(imgPath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        button.setGraphic(imageView);
    }
}
