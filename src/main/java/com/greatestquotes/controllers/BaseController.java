package com.greatestquotes.controllers;

import com.greatestquotes.Application;

public abstract class BaseController {
    protected Application rootApp;

    public void setAppFX(Application rootApp) {
        this.rootApp = rootApp;
    }
}
