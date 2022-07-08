package com.greatestquotes.controllers;

import com.greatestquotes.models.Role;
import com.greatestquotes.models.Roles;
import com.greatestquotes.models.User;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class ProfileController extends BaseStageController {
    @FXML
    protected TextField loginField;

    @FXML
    protected TextField newPasswordField;

    @FXML
    protected TextField repeatNewPasswordField;

    @FXML
    protected Label messageText;

    @FXML
    protected Label rolesLabel;

    @FXML
    protected void onSaveButtonClick() {
        String login = loginField.getText();
        String password = newPasswordField.getText();

        boolean loginLengthCondition = login.length() < 256 && login.length() > 5;
        boolean passwordEnterCondition = password.equals(newPasswordField.getText());
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
            State resultState = user.edit(loginField.getText(), newPasswordField.getText());
            if (State.DONE.equals(resultState)) {
                messageText.setTextFill(Paint.valueOf("GREEN"));
                messageText.setText("Your data has changed successfully!");
                user.auth(loginField.getText(), newPasswordField.getText());
                stage.close();
                rootApp.showMainWindow();
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

    @Override
    public void setUser(User user) {
        super.setUser(user);

        loginField.setText(user.getLogin());
        rolesLabel.setText(formatRoles(user.getRoles()));
    }

    protected String formatRoles(Roles roles) {
        StringBuilder builder = new StringBuilder();
        for (Role role : roles)
            builder.append(String.format("%s; ", role.name()));
        return builder.toString();
    }
}
