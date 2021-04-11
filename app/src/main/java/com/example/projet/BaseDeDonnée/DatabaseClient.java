package com.example.projet.BaseDeDonnée;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de l'application
        // à l'aide du "Room database builder"
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "EcoleDesLoustics").addCallback(roomDatabaseCallback).build(); //addCallback(roomDatabaseCallback).

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }


    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Quelle est la capitale de la France ?\" , \"Lyon\", \"Paris\",\"Marseille\",\"2\");");
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Quelle est la conjugaison correct du verbe mordre à la premiere personne du singulier au présent ?\" , \"Je mords\", \"Je mort\",\"Je morre\",\"1\");");
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Quelle est le nom de période historique avec les premiers ancêtres des Hommes ?\" , \"Ancetrehistoire\", \"L'avanthistoire\",\"Préhistoire\",\"3\");");
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Que vaut 1000g ?\" , \"10 cg\", \"100 mg\",\"1kg\",\"3\");");
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Quelle est la bonne écriture ?\" , \"Coqsisse\", \"Cokcisse\",\"Coccyx\",\"3\");");
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Choisissez le mot masculin\" , \"Cuillère\", \"Horaire\",\"Tasse\",\"2\");");
            db.execSQL("INSERT INTO QuestionsReponses (question, reponse1, reponse2, reponse3, reponseCorrect) VALUES(\"Quelle est l'état de l'eau des nuages et des brouillards ? \" , \"Solide\", \"Liquide\",\"Gazeux\",\"3\");");
        }
    };

}
