package ru.bor.examinatorium.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.entities.answermode.AnswerModeEnum;
import ru.bor.examinatorium.services.QuestionService;
import ru.bor.examinatorium.services.ThemeService;
import ru.bor.examinatorium.util.AlertExceptionWarning;
import ru.bor.examinatorium.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@FxmlView("../admin-stage.fxml")
@RequiredArgsConstructor
public class AdminController {
    private final ThemeService themeService;
    private final QuestionService questionService;
    private final Util util;
    @FXML
    public ListView<String> themeLV;
    @FXML
    public ListView<String> questionLV;
    @FXML
    public TextField themeIdTF;
    @FXML
    public TextField themeNameTF;
    @FXML
    public TextField timeOfThemeTF;
    @FXML
    public TextField numberOfQuestionToWinTF;
    @FXML
    public TextField numberOfMistakesTF;
    @FXML
    public ToggleGroup themeEditMode;
    @FXML
    public RadioButton editRB;
    @FXML
    public RadioButton addRB;
    @FXML
    public Button themeActionButton;
    @FXML
    public TextField questionIdTF;
    @FXML
    public TextArea questionTA;
    @FXML
    public TextArea answer1TA;
    @FXML
    public TextArea answer2TA;
    @FXML
    public TextArea answer3TA;
    @FXML
    public TextArea answer4TA;
    @FXML
    public ToggleGroup answerModeTGroup;
    @FXML
    public RadioButton singleAnswerRB;
    @FXML
    public RadioButton multiAnswerRB;
    @FXML
    public TextField rightAnswerTF;
    @FXML
    public Region picRegion;
    @FXML
    public Button setPicBTN;
    @FXML
    public Button deleteThemeAndQuestionsBTN;
    @FXML
    public Button saveNewQuestionBTN;
    @FXML
    public Button updateQuestionBTN;
    @FXML
    public Button deleteQuestionBTN;
    @FXML
    public TextField pathToPicTF;
    @FXML
    public HBox picHBox;
    @FXML
    public Button createQuestionBTN;
    @FXML
    public VBox questionVBox;
    @FXML
    public VBox themeVbox;

    @FXML
    private void initialize(){
        loadThemes(themeLV);
        themeVbox.setDisable(true);
        themeLV.setOnMouseClicked(event -> {
            themeLvEvent();
        });
        themeLV.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                themeLvEvent();
            }
        });

        questionLV.setOnMouseClicked(event -> {
            String[] questionId = questionLV.getSelectionModel().getSelectedItem().split(". ");
            showQuestionInfo(Long.parseLong(questionId[0]));
        });
    }
    private void themeLvEvent() {
        themeVbox.setDisable(false);
        if(themeLV.getSelectionModel().getSelectedItem() != null){
            loadQuestionOfSelectedTheme(themeLV.getSelectionModel().getSelectedItem());
            createQuestionBTN.setDisable(false);
        }else{
            createQuestionBTN.setDisable(true);
        }
    }
    private void showQuestionInfo(Long questionId) {
        questionVBox.setStyle("-fx-background-color:  #008B8B;");
        createQuestionBTN.setDisable(false);
        saveNewQuestionBTN.setDisable(true);
        deleteQuestionBTN.setDisable(false);
        updateQuestionBTN.setDisable(false);
        Question question = questionService.findQuestionById(questionId).orElse(null);
        if(question != null){
            picHBox.setVisible(true);
            questionIdTF.setText(question.getId().toString());
            questionTA.setText(question.getQuestion());
            answer1TA.setText(question.getAnswerOne());
            answer2TA.setText(question.getAnswerTwo());
            answer3TA.setText(question.getAnswerThree());
            answer4TA.setText(question.getAnswerFour());
            rightAnswerTF.setText(question.getRightAnswer());
            util.setBackgroundImage(picRegion, question.getBytes());
            switch (question.getAnswerMode()){
                case MULTI_ANSWER:
                    answerModeTGroup.selectToggle(multiAnswerRB);
                    break;
                case SINGLE_ANSWER:
                    answerModeTGroup.selectToggle(singleAnswerRB);
                    break;
            }
        }
    }
    private void updateQuestionLV(){
        questionLV.getItems().clear();
        if(!themeLV.getSelectionModel().getSelectedItems().isEmpty()){
            loadQuestionOfSelectedTheme(themeLV.getSelectionModel().getSelectedItem());
        }
    }
    private void clearQuestionInfo(){
        questionIdTF.clear();
        questionTA.clear();
        answer1TA.clear();
        answer2TA.clear();
        answer3TA.clear();
        answer4TA.clear();
        rightAnswerTF.clear();
        util.setBackgroundImage(picRegion, null);
    }
    private void showThemeInfo(Theme theme) {
        updateQuestionBTN.setDisable(true);
        deleteQuestionBTN.setDisable(true);
        themeIdTF.setText(theme.getId().toString());
        themeNameTF.setText(theme.getThemeName());
        timeOfThemeTF.setText(String.valueOf(theme.getCountdownSeconds()));
        numberOfQuestionToWinTF.setText(String.valueOf(theme.getNumberOfQuestions()));
        numberOfMistakesTF.setText(String.valueOf(theme.getNumberOfMistakes()));

    }
    private void loadThemes(ListView<String> themeLV) {
        themeService.loadThemes(themeLV);
    }
    private void loadQuestionOfSelectedTheme(String themName){
        clearQuestionInfo();
        picHBox.setVisible(false);
        Theme theme = themeService.getThemeByThemeName(themName);
        showThemeInfo(theme);
        questionLV.getItems().clear();
        Long themeId = theme.getId();
        List<String> questionName = new ArrayList<>();
        questionService.findAllThemeQuestions(themeId).forEach(question -> questionName.add(question.getId()+". "+question.getQuestion()));
        questionLV.getItems().addAll(questionName);
    }
    public void doThemeChanges(ActionEvent actionEvent) {
        try{
            ObservableList<String> selectedItems = themeLV.getSelectionModel().getSelectedItems();
            RadioButton selectedRadioButton = (RadioButton) themeEditMode.getSelectedToggle();

            if (!selectedItems.isEmpty() && selectedRadioButton.getId().equals("editRB")){
                String themeName = themeLV.getSelectionModel().getSelectedItem();
                Theme theme = themeService.getThemeByThemeName(themeName);

                theme.setThemeName(themeNameTF.getText());
                theme.setCountdownSeconds(Integer.parseInt(timeOfThemeTF.getText()));
                theme.setNumberOfQuestions(Integer.parseInt(numberOfQuestionToWinTF.getText()));
                theme.setNumberOfMistakes(Integer.parseInt(numberOfMistakesTF.getText()));
                themeService.updateTheme(theme.getId(),theme);

                themeLV.getItems().clear();
                loadThemes(themeLV);
                themeLV.getSelectionModel().select(themeNameTF.getText());

            }
            if(selectedRadioButton.getId().equals("addRB")
                && !themeNameTF.getText().isEmpty()
                && !timeOfThemeTF.getText().isEmpty()
                && !numberOfQuestionToWinTF.getText().isEmpty()
                && !numberOfMistakesTF.getText().isEmpty()){

                Theme theme = Theme.builder()
                        .themeName(themeNameTF.getText())
                        .countdownSeconds(Integer.parseInt(timeOfThemeTF.getText()))
                        .numberOfQuestions(Integer.parseInt(numberOfQuestionToWinTF.getText()))
                        .numberOfMistakes(Integer.parseInt(numberOfMistakesTF.getText()))
                        .build();
                themeService.saveNewTheme(theme);

                themeLV.getItems().clear();
                loadThemes(themeLV);
                themeLV.getSelectionModel().select(themeNameTF.getText());
            }
        }catch (NumberFormatException e){
            AlertExceptionWarning.showAlert("NumberFormatException!","Проверьте правильность ввода чиселовых значений!");
        }

    }
    public Question prepareQuestion(){
        try{
            if (!themeLV.getSelectionModel().isEmpty()) {
                AnswerModeEnum answerMode = null;
                RadioButton selectedRadioButton = (RadioButton) answerModeTGroup.getSelectedToggle();
                switch (selectedRadioButton.getId()){
                    case "multiAnswerRB":
                        answerMode = AnswerModeEnum.MULTI_ANSWER;
                        break;
                    case "singleAnswerRB":
                        answerMode = AnswerModeEnum.SINGLE_ANSWER;
                        break;
                }
                Question questionToSave = Question.builder()
                        .themeId(Long.parseLong(themeIdTF.getText()))
                        .question(questionTA.getText())
                        .answerOne(answer1TA.getText())
                        .answerTwo(answer2TA.getText())
                        .answerThree(answer3TA.getText())
                        .answerFour(answer4TA.getText())
                        .rightAnswer(rightAnswerTF.getText())
                        .answerMode(answerMode)
                        .build();

                if(validateQuestionToSave(questionToSave)){
                    return questionToSave;
                }else {
                    AlertExceptionWarning.showAlert("Ошибка валидации вопроса!", "Проверьте, правильно ли составлен вопрос!");
                }
            }
        }catch (NullPointerException e){
            AlertExceptionWarning.showAlert("Ошибка валидации вопроса!", "Проверьте, правильно ли составлен вопрос!");
        }
        return null;
    }
    public void saveNewQuestion(ActionEvent actionEvent) {
        Question question = prepareQuestion();
            if(question != null){
                Long createdId = questionService.saveQuestion(question).getId();
                updateQuestionLV();
                clearQuestionInfo();
                questionLV.getSelectionModel().select(createdId+". "+question.getQuestion());
                showQuestionInfo(createdId);
                picHBox.setVisible(true);
            }
    }
    private boolean validateQuestionToSave(Question questionToSave) {
        if(!questionToSave.getQuestion().trim().isEmpty()) {
            try {
                int rightAns = Integer.parseInt(questionToSave.getRightAnswer());

                if(questionToSave.getAnswerMode().equals(AnswerModeEnum.SINGLE_ANSWER)){
                    if(questionToSave.getRightAnswer().trim().length() == 1){
                        return util.validateRightAnswer(questionToSave.getRightAnswer());
                    }
                }
                if(questionToSave.getAnswerMode().equals(AnswerModeEnum.MULTI_ANSWER)){
                    if(questionToSave.getRightAnswer().trim().length() <= 4){
                        return util.validateRightAnswer(questionToSave.getRightAnswer());
                    }
                }
            }catch (NumberFormatException exception){
                return false;
            }
        }
        return false;
    }
    public void deleteQuestion(ActionEvent actionEvent) {
        questionService.deleteQuestionById(Long.parseLong(questionIdTF.getText()));
        updateQuestionLV();
        clearQuestionInfo();
    }
    public void setPicture(ActionEvent actionEvent) {
        String imagePath = pathToPicTF.getText().trim();
        Long id = Long.parseLong(questionIdTF.getText());
        byte[] imageBytes = questionService.convertImageToByteArray(imagePath);
        assert imageBytes != null;
        questionService.savePicToQuestion(id,imageBytes);
        showQuestionInfo(id);

    }
    public void createQuestion(ActionEvent actionEvent) {
        createQuestionBTN.setDisable(true);
        updateQuestionBTN.setDisable(true);
        deleteQuestionBTN.setDisable(true);
        clearQuestionInfo();
        saveNewQuestionBTN.setDisable(false);
        picHBox.setVisible(false);
        questionVBox.setStyle("-fx-background-color: #7B68EE;");
    }
    public void updateQuestion(ActionEvent actionEvent) {
        Question question = prepareQuestion();
        if (question != null){
            Long id = Long.parseLong(questionIdTF.getText());
            questionService.updateQuestion(id, question);
            updateQuestionLV();
            clearQuestionInfo();
            questionLV.getSelectionModel().select(id+". "+question.getQuestion());
            showQuestionInfo(id);
            picHBox.setVisible(true);
        }
    }
    public void deleteThemeAndQuestions(ActionEvent actionEvent) {
        Long themeId = Long.parseLong(themeIdTF.getText());
        themeService.deleteThemeById(themeId);
        questionService.deleteQuestionsByThemeId(themeId);
        themeLV.getItems().clear();
        questionLV.getItems().clear();
        loadThemes(themeLV);
    }
}
