package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet.db.DatabaseClient;
import com.example.projet.db.User;

public class GestionCompteActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    //public static final User USER_KEY = new User();
    // VIEW
    private Button deleteView, retourView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_compte);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        //User userCour = getIntent().get(USER_KEY);
        // Récupérer les vues
        deleteView = findViewById(R.id.supprCompte);
        retourView = findViewById(R.id.retour);

        // Associer un événement au bouton save
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        retourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(getApplicationContext(), "Retour", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteUser() {

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class DeleteUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a task
                User user = new User();

                // adding to database
                mDb.getAppDatabase()
                        .UserDao()
                        .delete(user);

                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                finish();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        DeleteUser st = new DeleteUser();
        st.execute();
    }

}