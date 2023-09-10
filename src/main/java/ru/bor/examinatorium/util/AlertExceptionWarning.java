package ru.bor.examinatorium.util;

import javafx.scene.control.Alert;

public class AlertExceptionWarning {

    public static void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
