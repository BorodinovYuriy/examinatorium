package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.repositories.QuestionsRepository;
import ru.bor.examinatorium.util.AlertExceptionWarning;
import ru.bor.examinatorium.util.NotFoundInternException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionsRepository questionsRepository;

    public Question saveQuestion(Question question) {
        return questionsRepository.save(question);
    }
    public List<Question> findAllThemeQuestions(Long themeId) {
        return questionsRepository.findAllByThemeId(themeId);
    }
    public void deleteQuestionById(long id) {
        questionsRepository.deleteById(id);
    }
    public Optional<Question> findQuestionById(Long questionId) {
        return questionsRepository.findById(questionId);
    }
    public void savePicToQuestion(Long questionId, byte[] arr) {
        if(arr != null){
            Question questionFromDB = questionsRepository.findById(questionId).get();
            questionFromDB.setBytes(arr);
            questionsRepository.save(questionFromDB);
        }
    }
    public byte[] convertImageToByteArray(String imagePath) {
        try {
            File file = new File(imagePath);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            AlertExceptionWarning.showAlert("Предупреждение!","Путь указан не верно!" );
            return null;
        }
    }
    public void updateQuestion(long id, Question question) {
        Question questionFromDB = questionsRepository.findById(id).get();
        questionFromDB.setThemeId(question.getThemeId());
        questionFromDB.setQuestion(question.getQuestion());
        questionFromDB.setAnswerOne(question.getAnswerOne());
        questionFromDB.setAnswerTwo(question.getAnswerTwo());
        questionFromDB.setAnswerThree(question.getAnswerThree());
        questionFromDB.setAnswerFour(question.getAnswerFour());
        questionFromDB.setRightAnswer(question.getRightAnswer());
        questionFromDB.setAnswerMode(question.getAnswerMode());
        questionsRepository.save(questionFromDB);
    }
    public void deleteQuestionsByThemeId(Long themeId) {
        questionsRepository.findAllByThemeId(themeId);
    }
}
