package com.greatestquotes;

import com.greatestquotes.controllers.AuthController;
import com.greatestquotes.controllers.BaseController;
import com.greatestquotes.controllers.RegController;
import com.greatestquotes.models.DBHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
//        TODO Нужно сделать так чтоб в случае проблем с подключением об этом сообщалось пользователю.
//        DBHandler.getConnection();

        showAuthWindow();
    }

    public void showAuthWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Authentication");
            primaryStage.setScene(scene);
            BaseController controller = fxmlLoader.getController();
            controller.setAppFX(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegistrationWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("reg.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Registration");
            primaryStage.setScene(scene);
            BaseController controller = fxmlLoader.getController();
            controller.setAppFX(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}