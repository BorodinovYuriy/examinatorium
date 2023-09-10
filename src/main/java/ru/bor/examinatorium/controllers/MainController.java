package ru.bor.examinatorium.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.entities.answermode.AnswerModeEnum;
import ru.bor.examinatorium.services.MainService;
import ru.bor.examinatorium.services.QuestionService;
import ru.bor.examinatorium.services.ThemeService;
import ru.bor.examinatorium.util.FileResourcesUtils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@FxmlView("../main-stage.fxml")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final ThemeService themeService;
    private final QuestionService questionService;

    @FXML
    public Label choiseTextInfo_Label;// TODO: 07.09.2023 логика отображения и дизайн -всего ответов и кол-во правильных
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
    private Theme theme;
    private FileResourcesUtils fileResourcesUtils;
    List<Question> questionList = new ArrayList<>();
    public void setTheme(ObservableList<String> selectedItems) {
        // TODO: 10.09.2023 доделать метод
        this.theme = themeService.getThemeByThemeName(selectedItems.get(0));
    }

    @FXML
    private void initialize() {
        fileResourcesUtils = new FileResourcesUtils();
        Question question = createTestEntityQuestions();
        questionService.saveQuestion(question);
        Image image = new Image(new ByteArrayInputStream(question.getBytes()));
        mainService.setBackgroundImage(region,question.getBytes());

        questionTextArea.setText(mainService.getTicket());//тест

        startCountdown();
    }

    private Question createTestEntityQuestions() {

        byte[] arr = fileResourcesUtils
                .convertStreamToByteArr(
                        fileResourcesUtils
                                .getFileFromResourceAsStream("images/classic.png"));

        Question q1 = Question.builder()
                .theme_id(1L)
                .question("Чему равно выражение 2+2 ?")
                .answerOne("Один")
                .answerTwo("Три")
                .answerThree("Четыре")
                .answerFour("Пять")
                .rightAnswer(3)
                .answerMode(AnswerModeEnum.SINGLE_ANSWER.name())
                .fileName("classic.png")
                .bytes(arr)
                .contentType("image/jpeg")
                .build();
        return q1;

    }

    private void startCountdown() {
        remainingSeconds = theme.getCountdownSeconds();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (remainingSeconds > 0) {
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                timer_Label.setText(String.format("%02d:%02d", minutes, seconds));
                remainingSeconds--;
            } else {
                timer_Label.setText("Время вышло!");
                timeline.stop();
                // TODO: 05.09.2023 логика завершения и досрочного завершения(либо через ActionEvent)
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    private String getImgUrl(Region region) {
        return mainService.getImgUrl(region);
    }




}
