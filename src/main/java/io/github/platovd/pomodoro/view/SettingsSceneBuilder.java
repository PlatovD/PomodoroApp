package io.github.platovd.pomodoro.view;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class SettingsSceneBuilder implements SceneBuilder {
    Scene scene = new Scene(new HBox());

    public SettingsSceneBuilder() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
