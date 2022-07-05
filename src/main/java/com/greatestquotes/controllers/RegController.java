package com.greatestquotes.controllers;

import com.greatestquotes.models.User;
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
        String message = "";
        // TODO Улучшить систему проверки не позволяя создавать пустой аккаунт(в SQL так же).
        // TODO Улучшить систему проверки задав минимальные критерии для Логина и пароля(так же в SQL)
        boolean loginCondition = loginField.getText().length() < 256;
        boolean passwordCondition = passwordField.getText().equals(repeatPasswordField.getText());
        if (!loginCondition)
            message += "Please, use login not bigger than 255 symbols!\n";
        if (!passwordCondition)
            message += "Please, repeat password carefully!\n";

        if (!passwordCondition || !loginCondition)
            messageText.setText(message);
        else {
            User.createNewUser(loginField.getText(), passwordField.getText());
            rootApp.showAuthWindow();
        }
    }
}
