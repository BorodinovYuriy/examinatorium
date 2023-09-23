package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Question,Long> {
        List<Question> findAllByThemeId(Long themeId);
        Question findByQuestion(String question);
        Optional<Question> findById(Long id);
        void deleteAllByThemeId(Long themeId);
}
