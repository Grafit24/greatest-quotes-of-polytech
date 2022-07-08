package com.greatestquotes.controllers;

import com.greatestquotes.models.User;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegController extends BaseController {
    @FXML
    protected TextField loginField;

    @FXML
    protected TextField passwordField;

    @FXML
    protected TextField repeatPasswordField;

    @FXML
    protected Text messageText;

    @FXML
    protected void onSignUpButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();

        boolean loginLengthCondition = login.length() < 256 && login.length() > 5;
        boolean passwordEnterCondition = password.equals(repeatPasswordField.getText());
        boolean notEmpty = login.length() > 0 && password.length() > 0;
        boolean passwordWithoutSpaces = !password.contains(" ") && !login.contains(" ");

        if (!loginLengthCondition)
            messageText.setText("Please, use login from 6 to 255 symbols.");
        else if (!passwordEnterCondition)
            messageText.setText("Please, repeat password carefully.");
        else if (!notEmpty)
            messageText.setText("Please, fill the fields.");
        else if (!passwordWithoutSpaces)
            messageText.setText("Please, don't use spaces.");

        if (loginLengthCondition && passwordEnterCondition && notEmpty && passwordWithoutSpaces) {
            State resultState = User.createNewUser(loginField.getText(), passwordField.getText());
            if (State.DONE.equals(resultState)) {
                rootApp.showAuthWindow();
            } else if (State.DUPLICATE.equals(resultState)) {
                messageText.setText(
                        "User with this login already exist! Please, use another login.");
            } else if (State.NO_CONNECTION.equals(resultState)) {
                messageText.setText("No connection to the server.");
            } else {
                messageText.setText("Something go wrong.");
            }
        }
    }
}
