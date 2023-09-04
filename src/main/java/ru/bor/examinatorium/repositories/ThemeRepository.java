package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.Theme;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long> {

}
