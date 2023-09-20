package ru.bor.examinatorium.util;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
public class DuplicateUtil {
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
}
