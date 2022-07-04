package com.greatestquotes.controllers;

import javafx.fxml.FXML;

public class AuthController extends BaseController {

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
}