package com.example.projet.BaseDeDonnée;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,QuestionsReponses.class}, version = 2, exportSchema = false) //on definie les deux entités de la bdd + la version
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao UserDao(); //ajout de l'interface pour la bdd lié a la classe User

    public abstract QuestionsReponsesDao QuestionsReponsesDao();


}
