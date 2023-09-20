package ru.bor.examinatorium.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.entities.answermode.AnswerModeEnum;
import ru.bor.examinatorium.services.QuestionService;
import ru.bor.examinatorium.services.ThemeService;
import ru.bor.examinatorium.util.AlertExceptionWarning;
import ru.bor.examinatorium.util.DuplicateUtil;

import java.util.ArrayList;
import java.util.List;

@Component
@FxmlView("../admin-stage.fxml")
@RequiredArgsConstructor
public class AdminController {
    private final ThemeService themeService;
    private final QuestionService questionService;
    private final DuplicateUtil duplicateUtil;
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
    private void initialize(){
        loadThemes(themeLV);

        themeLV.setOnMouseClicked(event -> {
            loadQuestionOfSelectedTheme(themeLV.getSelectionModel().getSelectedItem());
        });
        questionLV.setOnMouseClicked(event -> {
            showQuestionInfo(questionLV.getSelectionModel().getSelectedItem());
        });
//        listView.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                String selectedItem = listView.getSelectionModel().getSelectedItem();
//                System.out.println("Selected Item: " + selectedItem);
//            }
//        });
    }
    private void showQuestionInfo(String questionName) {
        Question question = questionService.findByQuestionName(questionName);
        questionIdTF.setText(question.getId().toString());
        questionTA.setText(question.getQuestion());
        answer1TA.setText(question.getAnswerOne());
        answer2TA.setText(question.getAnswerTwo());
        answer3TA.setText(question.getAnswerThree());
        answer4TA.setText(question.getAnswerFour());
        rightAnswerTF.setText(question.getRightAnswer());
        duplicateUtil.setBackgroundImage(picRegion, question.getBytes());

        switch (question.getAnswerMode()){
            case MULTI_ANSWER:
                answerModeTGroup.selectToggle(multiAnswerRB);
                break;
            case SINGLE_ANSWER:
                answerModeTGroup.selectToggle(singleAnswerRB);
                break;
        }
    }
    private void clearQuestionInfo(){
        // TODO: 19.09.2023 при смене темы - убрать инфо вопросов
    }
    private void showThemeInfo(Theme theme) {
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
        Theme theme = themeService.getThemeByThemeName(themName);
        showThemeInfo(theme);
        questionLV.getItems().clear();
        Long themeId = theme.getId();
        List<String> questionName = new ArrayList<>();
        questionService.findAllThemeQuestions(themeId).forEach(question -> questionName.add(question.getQuestion()));
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

    public void saveNewQuestion(ActionEvent actionEvent) {
        if (!themeLV.getSelectionModel().isEmpty()) {
            // TODO: 20.09.2023 пока простой вариант
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
                    // TODO: 20.09.2023 нужна картинка по дефолту!!! 
//                .fileName()
                .build();
            questionService.saveQuestion(questionToSave);
        }

    }
}
