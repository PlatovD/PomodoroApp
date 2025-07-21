package io.github.platovd.pomodoro.spring;

import io.github.platovd.pomodoro.model.elements.DefaultSession;
import io.github.platovd.pomodoro.model.elements.Session;
import io.github.platovd.pomodoro.model.elements.TimerSegmentData;
import javafx.stage.Stage;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("io.github.platovd.pomodoro")
@EnableAspectJAutoProxy
public class Config {
    public Config() {
    }

    @Bean
    @Scope("singleton")
    public Stage getStage() {
        Stage stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(180);
        return stage;
    }

    @Bean
    @Scope("prototype")
    public Session getSession() {
        return new DefaultSession(4);
    }

    @Bean("focusTimerSegmentData")
    @Scope("prototype")
    public TimerSegmentData getFocusSegment() {
        return new TimerSegmentData(35, 0);
    }

    @Bean("restTimerSegmentData")
    @Scope("prototype")
    public TimerSegmentData getRestSegment() {
        return new TimerSegmentData(5, 0);
    }
}
