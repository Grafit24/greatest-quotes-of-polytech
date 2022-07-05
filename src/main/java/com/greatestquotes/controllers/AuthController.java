package com.greatestquotes.controllers;

import com.greatestquotes.utils.HashCode;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AuthController extends BaseController {

    @FXML
    protected TextField loginField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected Text messageText;

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

        if (login.length() == 0 || password.length() == 0)
            messageText.setText("Please, fill the fields.");

        if (user.auth(login, password))
            rootApp.showMainWindow();
        else
            messageText.setText("Wrong login or password!");
    }
}