package ru.bor.examinatorium.services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bor.examinatorium.entities.Question;
import ru.bor.examinatorium.repositories.QuestionsRepository;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Base64;


@Service
@RequiredArgsConstructor
public class MainService {
    private final QuestionsRepository questionsRepository;

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

    public void setBackgroundImage(Region region, byte[] imageData) {
        Image image = new Image(new ByteArrayInputStream(imageData));
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        Background background = new Background(backgroundImage);
        region.setBackground(background);
    }

    public String getTicket() {
        Question q = questionsRepository.findById(1L).get();// TODO: 07.09.2023 get()!
        return q.toString();
    }


}
