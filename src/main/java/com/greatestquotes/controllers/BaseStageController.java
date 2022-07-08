package com.greatestquotes.controllers;

import javafx.stage.Stage;

public abstract class BaseStageController extends BaseController {
    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
