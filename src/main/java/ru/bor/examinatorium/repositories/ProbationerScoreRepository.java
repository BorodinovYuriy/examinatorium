package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.ProbationerScore;
import ru.bor.examinatorium.entities.Theme;
@Repository
public interface ProbationerScoreRepository extends JpaRepository<ProbationerScore,Long> {
}
