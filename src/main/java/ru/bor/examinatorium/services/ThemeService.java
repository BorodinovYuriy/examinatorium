package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Theme;
import ru.bor.examinatorium.repositories.ThemeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;



    public List<Theme> getThemes(){
        return themeRepository.findAll();
    }


}
