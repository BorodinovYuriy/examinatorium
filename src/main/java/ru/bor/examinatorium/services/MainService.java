package ru.bor.examinatorium.services;

import javafx.scene.layout.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.repositories.QuestionsRepo;

@Service
@RequiredArgsConstructor
public class MainService {
    private final QuestionsRepo questionsRepo;

    public String getImgUrl(Region region){
        String style = region.getStyle();
        String[] styleProperties = style.split(";");
        for (String property : styleProperties) {
            if (property.trim().startsWith("-fx-background-image:")) {
                return property.substring(27, property.length() -2);
            }
        }
        return null;
    }
    public void setBackground(Region region, String imageUrl) {
        String style = String.format("-fx-background-image: url('%s'); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center;", imageUrl);
        region.setStyle(style);
    }

    public String getTicket() {
        Question q = questionsRepo.findById(1L).get();
        return q.toString();
    }
}
