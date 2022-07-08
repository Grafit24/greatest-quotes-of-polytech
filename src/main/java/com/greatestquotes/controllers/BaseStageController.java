package com.greatestquotes.controllers;

import com.greatestquotes.Application;
import com.greatestquotes.models.User;
import javafx.stage.Stage;

public abstract class BaseStageController extends BaseController {
    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
