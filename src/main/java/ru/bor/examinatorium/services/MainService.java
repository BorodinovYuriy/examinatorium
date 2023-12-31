package ru.bor.examinatorium.services;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MainService {

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


    public void changeColorToRed(VBox vBox) {
        vBox.setStyle("-fx-background-color: red;");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
            vBox.setStyle("-fx-background-color:  #008080;");
        }));
        timeline.play();
    }



}
