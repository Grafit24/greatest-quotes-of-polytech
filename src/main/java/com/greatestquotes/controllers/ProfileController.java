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

    @Override
    public void initialize() {
        super.initialize();

        loginField.setText(user.getLogin());
        rolesLabel.setText(formatRoles(user.getRoles()));
    }

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
            State state = user.edit(loginField.getText(), newPasswordField.getText());
            switch (state) {
                case DONE -> {
                    messageText.setTextFill(Paint.valueOf("GREEN"));
                    messageText.setText("Your data has changed successfully!");
                    user.auth(loginField.getText(), newPasswordField.getText());
                    stage.close();
                    rootApp.getMainWindowController().update();
                }
                case DUPLICATE -> messageText.setText(
                        "User with this login already exist! Please, use another login.");
                default -> messageText.setText(state.getText());
            }
        }
    }

    protected String formatRoles(Roles roles) {
        StringBuilder builder = new StringBuilder();
        for (Role role : roles)
            builder.append(String.format("%s; ", role.name()));
        return builder.toString();
    }
}
