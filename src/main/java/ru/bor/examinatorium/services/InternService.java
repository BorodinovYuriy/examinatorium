package ru.bor.examinatorium.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Intern;
import ru.bor.examinatorium.repositories.InternRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternService {
    private final InternRepository internRepository;

    public Optional<Intern> findInternById(long id) {
        return internRepository.findById(id);
    }

    public boolean isInternPresent(Long id) {
        return internRepository.existsById(id);
    }
}
