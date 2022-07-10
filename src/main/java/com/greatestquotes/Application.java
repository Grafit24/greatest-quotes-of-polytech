package com.greatestquotes;

import com.greatestquotes.controllers.*;
import com.greatestquotes.models.DBHandler;
import com.greatestquotes.models.Quote;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage){
        primaryStage = stage;
        showAuthWindow();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        DBHandler.closeConnection();
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
            MainController controller = fxmlLoader.getController();
            controller.setAppFX(this);
            controller.update();
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
            controller.setStage(extraStage);
            extraStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCreateWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("write.fxml"));
            CreateController controller = new CreateController();
            Stage extraStage = new Stage();
            extraStage.setTitle("Create");
            controller.setAppFX(this);
            controller.setStage(extraStage);
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            extraStage.setScene(scene);
            extraStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditWindow(Quote quote) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("write.fxml"));
            Stage extraStage = new Stage();
            EditController controller = new EditController();
            controller.setAppFX(this);
            controller.setQuote(quote);
            controller.setStage(extraStage);
            extraStage.setTitle("Edit");
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            extraStage.setScene(scene);
            extraStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}