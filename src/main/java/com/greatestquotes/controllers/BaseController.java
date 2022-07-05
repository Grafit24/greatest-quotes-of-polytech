package com.greatestquotes.controllers;

import com.greatestquotes.Application;
import com.greatestquotes.models.User;

public abstract class BaseController {
    protected Application rootApp;
    protected User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setAppFX(Application rootApp) {
        this.rootApp = rootApp;
    }
}
