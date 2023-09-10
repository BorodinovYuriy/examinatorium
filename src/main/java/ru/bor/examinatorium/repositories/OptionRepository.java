package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.Options;

@Repository
public interface OptionRepository extends JpaRepository<Options,Long> {
}
