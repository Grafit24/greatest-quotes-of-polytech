package com.greatestquotes.controllers;

import com.greatestquotes.utils.HashCode;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController extends BaseController {

    @FXML
    protected TextField loginField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected void onGuestButtonClick() {
        rootApp.showMainWindow();
    }

    @FXML
    protected void onSignUpButtonClick() {
        rootApp.showRegistrationWindow();
    }

    @FXML
    protected void onSignInButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (user.auth(login, password))
            rootApp.showMainWindow();
    }
}