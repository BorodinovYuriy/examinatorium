package ru.bor.examinatorium;

import javafx.application.Application;
import org.springframework.context.ConfigurableApplicationContext;

@org.springframework.boot.autoconfigure.SpringBootApplication

public class SpringBootApplication {
    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {

        Application.launch(JavaFxApplication.class, args);
    }
}
