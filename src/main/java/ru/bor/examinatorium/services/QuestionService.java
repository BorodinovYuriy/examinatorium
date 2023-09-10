package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.repositories.QuestionsRepository;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionsRepository questionsRepository;

    public void saveQuestion(Question question) {
        questionsRepository.save(question);
    }
}
