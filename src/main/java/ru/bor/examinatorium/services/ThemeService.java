package ru.bor.examinatorium.services;

import javafx.scene.control.ListView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.repositories.ThemeRepository;
import ru.bor.examinatorium.util.AlertExceptionWarning;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes(){
        return themeRepository.findAll();
    }
    public void saveNewTheme(Theme theme){
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

    public void loadThemes(ListView<String> themeLV) {
        List<String> themes = new ArrayList<>();
        getAllThemes().forEach(theme -> themes.add(theme.getThemeName()));
        themeLV.getItems().addAll(themes);
    }


    public void updateTheme(Long oldThemeIid, Theme newTheme) {
        Theme fromDB = themeRepository.getThemeById(oldThemeIid);
        fromDB.setThemeName(newTheme.getThemeName());
        fromDB.setCountdownSeconds(newTheme.getCountdownSeconds());
        fromDB.setNumberOfQuestions(newTheme.getNumberOfQuestions());
        fromDB.setNumberOfMistakes(newTheme.getNumberOfMistakes());
        themeRepository.save(fromDB);
    }
}

