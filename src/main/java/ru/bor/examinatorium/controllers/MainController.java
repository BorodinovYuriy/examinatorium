package ru.bor.examinatorium.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
import ru.bor.examinatorium.util.Util;

import java.util.List;
import java.util.Random;

@Component
@FxmlView("../main-stage.fxml")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final ThemeService themeService;
    private final QuestionService questionService;
    private final Util util;

    @FXML
    public Label choiseTextInfo_Label;// TODO: 07.09.2023 логика отображения и дизайн -всего ответов и кол-во правильных
    @FXML
    public Region region;
    @FXML
    public TextArea questionTextArea;
    @FXML
    public Label timerLabel;
    @FXML
    public VBox singleBox;
    @FXML
    public VBox multiBox;
    @FXML
    public ToggleGroup choiceGroup;
    @FXML
    public CheckBox mCBox1;
    @FXML
    public CheckBox mCBox2;
    @FXML
    public CheckBox mCBox3;
    @FXML
    public CheckBox mCBox4;
    @FXML
    public RadioButton sRBox1;
    @FXML
    public RadioButton sRBox2;
    @FXML
    public RadioButton sRBox3;
    @FXML
    public RadioButton sRBox4;
    @FXML
    public Button AnswerButton;
    @FXML
    public VBox vBoxAll;
    @FXML
    public Label mistakes;
    @FXML
    public Label questionCount;
    private int remainingSeconds;
    private Theme theme;
    private List<Question> questionList;
    private int[] randomNumberOfTicketArr;
    private int questionPage = 0;
    protected int lastQuestions;
    protected int mistakesCount;


    public void setTheme(ObservableList<String> selectedItems) {
        this.theme = themeService.getThemeByThemeName(selectedItems.get(0));
        lastQuestions = theme.getNumberOfQuestions();
        mistakesCount = theme.getNumberOfMistakes();
    }
    @FXML
    private void initialize() {
        singleBox.setVisible(false);
        multiBox.setVisible(false);
        questionList = questionService.findAllThemeQuestions(theme.getId());
        setRandomNumberArr(questionList.size());
        showQuestion();
        startCountdown();
    }
    private void setRandomNumberArr(int questionListSize) {
        randomNumberOfTicketArr = new int[questionListSize];

        Random random = new Random();
        int count = 0;
        while (count < randomNumberOfTicketArr.length) {

            int randomInt = random.nextInt(questionListSize);
            boolean isDuplicate = false;
            for (int i = 0; i < count; i++) {
                if (randomNumberOfTicketArr[i] == randomInt) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                randomNumberOfTicketArr[count] = randomInt;
                count++;
            }
        }
    }
    private void showQuestion() {

        questionCount.setText(String.valueOf(lastQuestions));
        mistakes.setText(String.valueOf(mistakesCount));
        resetChoiceButtons();
        if (questionPage >= randomNumberOfTicketArr.length) questionPage = 0;
        if(questionPage < 0) questionPage = randomNumberOfTicketArr.length - 1;
        if(questionList.get(randomNumberOfTicketArr[questionPage]) != null){
            Question question = questionList.get(randomNumberOfTicketArr[questionPage]);

            questionTextArea.setText(question.toString());
            util.setBackgroundImage(region, question.getBytes());

            if(question.getAnswerMode().equals(AnswerModeEnum.SINGLE_ANSWER)){
                singleBox.setVisible(true);
                multiBox.setVisible(false);
            }
            if(question.getAnswerMode().equals(AnswerModeEnum.MULTI_ANSWER)){
                singleBox.setVisible(false);
                multiBox.setVisible(true);
            }
            questionPage++;
        }
    }
    private void resetChoiceButtons() {
        mCBox1.setSelected(false);
        mCBox2.setSelected(false);
        mCBox3.setSelected(false);
        mCBox4.setSelected(false);
        sRBox1.setSelected(false);
        sRBox2.setSelected(false);
        sRBox3.setSelected(false);
        sRBox4.setSelected(false);

    }
    public void showNextTicket(ActionEvent actionEvent) {
        showQuestion();
    }
    public void showPreviousTicket(ActionEvent actionEvent) {
        questionPage -= 2;
        showQuestion();
    }
    private void startCountdown() {
        remainingSeconds = theme.getCountdownSeconds();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (remainingSeconds > 0) {
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                remainingSeconds--;
            } else {
                timerLabel.setText("Время вышло!");
                timeline.stop();
                // TODO: 05.09.2023 логика завершения и досрочного завершения(либо через ActionEvent)
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    public void doAnswer(ActionEvent actionEvent) {
        boolean isRight = false;
        Question question = questionList.get(randomNumberOfTicketArr[questionPage-1]);

        if (question.getAnswerMode().equals(AnswerModeEnum.SINGLE_ANSWER) && choiceGroup.getSelectedToggle() != null) {
            isRight = isRightForSingleAnswer(question);
        }
        if(question.getAnswerMode().equals(AnswerModeEnum.MULTI_ANSWER)){
            isRight = isRightForMultiAnswer(question);
        }
        if (!isRight){
            mainService.changeColorToRed(vBoxAll);//Знак неправильного ответа!
            mistakesCount--;
        }else {
            lastQuestions--;
        }
        if(lastQuestions != 0 || mistakesCount != 0 ){
            resetChoiceButtons();
            showQuestion();
        }else{
            stopTest(lastQuestions, mistakesCount);
        }

    }

    private void stopTest(int lastQuestions, int mistakesCount) {

    }


    private boolean isRightForMultiAnswer(Question question) {
        StringBuilder internResult = new StringBuilder();
        String rightAnswerFromQuestion = question.getRightAnswer();

        if (mCBox1.isSelected()) internResult.append("1");
        if (mCBox2.isSelected()) internResult.append("2");
        if (mCBox3.isSelected()) internResult.append("3");
        if (mCBox4.isSelected()) internResult.append("4");

        String intResult = internResult.toString();
        return !intResult.isEmpty() && intResult.equals(rightAnswerFromQuestion);
        // TODO: 11.09.2023 дальнейшая логика и возврат boolean, продумать логику уже отвеченных билетов
    }
    private boolean isRightForSingleAnswer(Question question) {
        RadioButton selectedRadioButton = (RadioButton) choiceGroup.getSelectedToggle();
        String internAnswer = selectedRadioButton.getId().substring(5);
        return internAnswer.equals(question.getRightAnswer());
        // TODO: 11.09.2023 дальнейшая логика и возврат boolean, продумать логику уже отвеченных билетов
    }

}
