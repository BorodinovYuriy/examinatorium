package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.Intern;
@Repository
public interface InternRepository extends JpaRepository<Intern, Long> {
}
