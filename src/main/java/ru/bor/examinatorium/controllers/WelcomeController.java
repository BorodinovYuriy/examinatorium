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
import ru.bor.examinatorium.services.ThemeService;
import ru.bor.examinatorium.services.WelcomeService;

import java.util.ArrayList;
import java.util.List;

@Component
@FxmlView("../welcome-stage.fxml")
@RequiredArgsConstructor
public class WelcomeController {

    private final WelcomeService welcomeService;

    private final ApplicationContext context;

    private final ThemeService themeService;


    @FXML
    public ListView<String> ChoiceListView;
    @FXML
    public TextField idTextField;
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
        loadTheme();

    }

    public void setVisibleAdminPane(ActionEvent actionEvent){
        adminLogoAnchorPane.setVisible(adminVisibleCheckBox.isSelected());
    }
    public void loadMainPage(ActionEvent actionEvent){
        // TODO: 05.09.2023 нажатие "старт" и передача данных
        startTestButton.getScene().getWindow().hide();

        Stage stage = new Stage();
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(950);
        stage.show();
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
    private void loadTheme() {
        List<String> themeName = new ArrayList<>();
        themeService.getAllThemes().forEach(theme -> themeName.add(theme.getThemeName()));
        ChoiceListView.
                getItems().
                addAll(themeName);
    }



}
