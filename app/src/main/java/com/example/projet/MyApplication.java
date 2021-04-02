package com.example.projet;

import android.app.Application;

import com.example.projet.db.User;

public class MyApplication extends Application {

    private User UserCourrant;

    public User getUserCourrant() {
        return UserCourrant;
    }

    public void setUserCourrant(User UserCourrant) {
        this.UserCourrant = UserCourrant;
    }
}


