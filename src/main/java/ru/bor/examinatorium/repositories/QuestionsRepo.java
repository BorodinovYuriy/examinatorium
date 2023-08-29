package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.Question;

@Repository
public interface QuestionsRepo extends JpaRepository<Question, Long> {
}
