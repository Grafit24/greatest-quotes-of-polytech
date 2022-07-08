package com.greatestquotes;

import com.greatestquotes.controllers.BaseController;
import com.greatestquotes.controllers.MainController;
import com.greatestquotes.models.User;
import com.greatestquotes.utils.HashCode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage primaryStage;
    private User user;

    @Override
    public void start(Stage stage){
        primaryStage = stage;
        user = new User();

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
            controller.setUser(user);
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

    public void showMainWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Greatest quotes");
            primaryStage.setScene(scene);
            BaseController controller = fxmlLoader.getController();
            controller.setAppFX(this);
            controller.setUser(user);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}