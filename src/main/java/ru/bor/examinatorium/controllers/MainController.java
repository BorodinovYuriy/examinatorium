package ru.bor.examinatorium.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.services.MainService;

@Component
@FxmlView("../main-stage.fxml")
@RequiredArgsConstructor
public class MainController {
    private final ApplicationContext context;
    private final MainService mainService;
    private static final int COUNTDOWN_SECONDS = 100;
    @FXML
    public ImageView imageView_questionIMG;
    @FXML
    public Image image_img;
    @FXML
    public Region region;
    @FXML
    public TextArea questionTextArea;
    @FXML
    public Label timer_Label;
    private int remainingSeconds;

    @FXML
    private void initialize() {
        mainService.setBackground(region,"images/default_image.png");//тест
        questionTextArea.setText(mainService.getTicket());//тест

        startCountdown();
    }

    private void startCountdown() {
        remainingSeconds = COUNTDOWN_SECONDS;

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (remainingSeconds > 0) {
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                timer_Label.setText(String.format("%02d:%02d", minutes, seconds));
                remainingSeconds--;
            } else {
                timer_Label.setText("Countdown done!");
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private String getImgUrl(Region region) {
        return mainService.getImgUrl(region);
    }
    private void setBackground(Region region, String imageUrl) {
        mainService.setBackground(region, imageUrl);
    }




}
