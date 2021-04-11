package com.example.projet;

import android.app.Application;

import com.example.projet.BaseDeDonnée.User;

public class MyApplication extends Application { //creation de la sous classe MyApplication qui est la fille de notre application

    private User UserCourrant; //ajout du user Courrant

    //getter et setters
    public User getUserCourrant() {
        return UserCourrant;
    }

    public void setUserCourrant(User UserCourrant) {
        this.UserCourrant = UserCourrant;
    }
}


