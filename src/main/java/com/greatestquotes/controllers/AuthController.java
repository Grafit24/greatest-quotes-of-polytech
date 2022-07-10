package com.greatestquotes.controllers;

import com.greatestquotes.utils.State;
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

        State resultState = user.auth(login, password);
        if (State.DONE.equals(resultState)) {
            rootApp.showMainWindow();
        } else if (State.NO_ENTRY.equals(resultState)) {
            messageText.setText("Wrong login or password!");
        // TODO Починить, когда нет подключения не выводит из-за того что невозможно null.close()
        } else if (State.NO_CONNECTION.equals(resultState)) {
            messageText.setText("No connection to the server.");
        } else {
            messageText.setText("Something go wrong.");
        }
    }
}