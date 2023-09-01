package ru.bor.examinatorium.controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import ru.bor.examinatorium.services.WelcomeService;

import javax.annotation.PostConstruct;

@Component
@FxmlView("../welcome-stage.fxml")
@RequiredArgsConstructor
public class WelcomeController {

    private final WelcomeService welcomeService;

    private final ApplicationContext context;


    @FXML
    public ListView<String> listViewTestChoice;
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
    public TextArea help;

    @FXML
    private void initialize() {
        this.help.setText("Выберите тест для прохождения из списка тестов, введите свой id, нажмите: \"Начать\". ");
        loadViewTestChoice();
    }


    public void setVisibleAdminPane(ActionEvent actionEvent){
        help.setVisible(false);
        adminLogoAnchorPane.setVisible(adminVisibleCheckBox.isSelected());
    }
    public void loadMainPage(ActionEvent actionEvent){
        startTestButton.getScene().getWindow().hide();

        Stage stage = new Stage();
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
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
    private void loadViewTestChoice() {
        listViewTestChoice.
                getItems().
                addAll(
                        "Item 1",
                        "Item 2",
                        "Item 3",
                        "Item 4",
                        "Item 5",
                        "Item 6",
                        "Item 7",
                        "Item 8",
                        "Item 9",
                        "Item 10",
                        "Item 11",
                        "Item 12"
                );
        choiceTheTestLabel.setVisible(true);
        listViewTestChoice.setVisible(true);
        help.setVisible(false);
    }



}
