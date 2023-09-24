package ru.bor.examinatorium.util;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class Util {
    public void setBackgroundImage(Region region, byte[] imageData) {
        if(imageData == null){
            imageData = convertStreamToByteArr(getFileFromResourceAsStream("images/default_image.png"));
        }
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
    public byte[] convertStreamToByteArr(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[4096];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (IOException e) {
            AlertExceptionWarning.showAlert("Ошибка пути к файлу!","Неправильно указан путь к файлу.");
            throw new RuntimeException(e);
        }

        return buffer.toByteArray();
    }
    public InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Файл не найден! " + fileName);
        } else {
            return inputStream;
        }
    }
    public int[] convertStringToIntArray(String digitsString) {
        char[] digitsChars = digitsString.toCharArray();
        int[] digitsArray = new int[digitsChars.length];

        for (int i = 0; i < digitsChars.length; i++) {
            digitsArray[i] = Character.getNumericValue(digitsChars[i]);
        }

        return digitsArray;
    }
    public boolean validateRightAnswer(String rightAnswer) {
        int[] arr = convertStringToIntArray(rightAnswer);
        boolean isValid = true;
        if(arr.length == 1 && !(arr[0] >= 1 && arr[0] <= 4)){
            isValid = false;
        } else if (arr.length > 1 && arr.length <= 4) {
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i] == arr[j]) {
                        isValid = false;
                        break;
                    }
                }
            }
        }
        return isValid;
    }



}
