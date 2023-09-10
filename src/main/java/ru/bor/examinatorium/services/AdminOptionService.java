package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.repositories.OptionRepository;

@Service
@RequiredArgsConstructor
public class AdminOptionService {
    private final OptionRepository optionRepository;

}
