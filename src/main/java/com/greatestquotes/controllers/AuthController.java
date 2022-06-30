package com.greatestquotes.controllers;


import com.greatestquotes.Application;
import javafx.fxml.FXML;

public class AuthController {
    private Application rootApp;

    @FXML
    protected void onGuestButtonClick() {
        System.out.println("Guest enter");
    }

    @FXML
    protected void onSignUpButtonClick() {
        rootApp.showRegistrationWindow();
    }

    @FXML
    protected void onSignInButtonClick() {
        System.out.println("SignIn pressed");
    }

    public void setAppFX(Application rootApp) {
        this.rootApp = rootApp;
    }
}