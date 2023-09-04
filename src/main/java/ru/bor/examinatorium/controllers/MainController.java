package ru.bor.examinatorium.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.services.MainService;

@Component
@FxmlView("../main-stage.fxml")
@RequiredArgsConstructor
public class MainController {
    private final ApplicationContext context;
    private final MainService mainService;
    @FXML
    public ImageView imageView_questionIMG;
    @FXML
    public Image image_img;
    public Region region;
    public TextArea questionTextArea;

    @FXML
    private void initialize() {
        mainService.setBackground(region,"images/default_image.png");
        questionTextArea.setText(mainService.getTicket());
    }

    private String getImgUrl(Region region) {
        return mainService.getImgUrl(region);
    }
    private void setBackground(Region region, String imageUrl) {
        mainService.setBackground(region, imageUrl);
    }




}
