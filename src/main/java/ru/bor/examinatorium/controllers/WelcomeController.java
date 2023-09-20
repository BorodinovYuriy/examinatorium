package ru.bor.examinatorium.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.bor.examinatorium.services.InternService;
import ru.bor.examinatorium.services.ThemeService;

import java.util.ArrayList;
import java.util.List;

@Component
@FxmlView("../welcome-stage.fxml")
@RequiredArgsConstructor
public class WelcomeController {
    private final ApplicationContext context;
    private final MainController mainController;
    private final ThemeService themeService;
    private final InternService internService;
    @FXML
    public ListView<String> choiceListView;
    @FXML
    public Button startTestButton;
    @FXML
    public CheckBox adminVisibleCheckBox;
    @FXML
    public AnchorPane adminLogoAnchorPane;
    @FXML
    public TextField adminLogin;
    @FXML
    public TextField adminPassword;
    @FXML
    public Button adminAccessButton;
    @FXML
    public Label choiceTheTestLabel;


    @FXML
    private void initialize() {
        //подтягивание тем + активация кнопки старт
        startTestButton.setDisable(true);
        loadThemes();
        choiceListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            startTestButton.setDisable(newValue == null);
        }));
    }

    private void loadThemes() {
        List<String> themeName = new ArrayList<>();
        themeService.getAllThemes().forEach(theme -> themeName.add(theme.getThemeName()));
        choiceListView.getItems().addAll(themeName);
    }

    public void loadMainPage(ActionEvent actionEvent){
        startTestButton.getScene().getWindow().hide();
        mainController.setTheme(choiceListView.getSelectionModel().getSelectedItems());
        Stage stage = new Stage();
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.show();
    }

    public void setVisibleAdminPane(ActionEvent actionEvent){
        adminLogoAnchorPane.setVisible(adminVisibleCheckBox.isSelected());
    }
    public void loadAdminPage(ActionEvent actionEvent){
        startTestButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(AdminController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
