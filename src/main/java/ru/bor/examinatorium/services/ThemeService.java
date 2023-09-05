package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.repositories.ThemeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes(){
        return themeRepository.findAll();
    }

    public void addTheme(String themeName){
        Theme theme = new Theme();
        theme.setThemeName(themeName);
        themeRepository.save(theme);
    }
    public void deleteTheme(Long id){
        themeRepository.deleteById(id);

    }

    public Theme getThemeById(Long id){
        Optional<Theme> ot = themeRepository.findById(id);
        Theme t = ot.get();
        return t;
    }


}
