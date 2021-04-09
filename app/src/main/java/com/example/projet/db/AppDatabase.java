package com.example.projet.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)  //,QuestionsReponses.class}
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao UserDao();
    //public abstract QuestionsReponsesDao QuestionsReponsesDao();

}
