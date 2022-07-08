package com.greatestquotes;

import com.greatestquotes.controllers.BaseController;
import com.greatestquotes.controllers.BaseStageController;
import com.greatestquotes.models.DBHandler;
import com.greatestquotes.models.User;
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

    @Override
    public void stop() throws Exception {
        super.stop();

        DBHandler.getConnection().close();
        System.out.println("Connection close. You can sleep calm :)");
    }

    public void showAuthWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("auth.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reg.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
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

    public void showProfileWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage extraStage = new Stage();
            extraStage.setTitle("Profile");
            extraStage.setScene(scene);
            BaseStageController controller = fxmlLoader.getController();
            controller.setAppFX(this);
            controller.setUser(user);
            controller.setStage(extraStage);
            extraStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}