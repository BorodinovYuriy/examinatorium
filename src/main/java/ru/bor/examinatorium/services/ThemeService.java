package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.repositories.ThemeRepository;
import ru.bor.examinatorium.util.AlertExceptionWarning;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes(){
        return themeRepository.findAll();
    }
    public void saveTheme(Theme theme){
        if(!themeRepository.existsThemeByThemeName(theme.getThemeName().trim())){
            themeRepository.save(theme);
        }else {
            AlertExceptionWarning.showAlert("Ошибка уникальности имён", "Тема с таким названием уже существует!");
        }
    }
    public void deleteThemeById(Long id){
        themeRepository.deleteById(id);
    }
    public Theme getThemeByThemeName(String themeName){
        return themeRepository.getThemeByThemeName(themeName);
    }
}
