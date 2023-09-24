package ru.bor.examinatorium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bor.examinatorium.entities.Theme;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long> {

    Theme getThemeByThemeName(String themeName);
    Optional<Theme> findById(Long id);
    boolean existsThemeByThemeName(String themeName);
    Theme getThemeById(Long id);
}
