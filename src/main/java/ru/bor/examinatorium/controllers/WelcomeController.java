package ru.bor.examinatorium.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.JavaFxApplication;
import ru.bor.examinatorium.WeatherService;

import java.io.IOException;

@Component
@FxmlView("../welcome-stage.fxml")
public class WelcomeController {

    @FXML
    public Button test;
    @FXML
    private Label weatherLabel;
    @FXML
    private Button weather;
    private WeatherService weatherService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public WelcomeController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void loadWeatherForecast(ActionEvent actionEvent) {
        this.weatherLabel.setText(weatherService.getWeatherForecast());
    }

    public void loadStage_2(ActionEvent actionEvent){
        test.getScene().getWindow().hide();

        Stage stage = new Stage();
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }












}
