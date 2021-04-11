package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.db.DatabaseClient;
import com.example.projet.db.User;

public class GestionCompteActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    // VIEW
    private Button deleteView, retourView, modifView;
    private TextView NomU, PrenomU, IdU, ScoreMaths1, ScoreMaths2, ScoreCult1, ScoreCult2;
    private EditText NomET, PrenomET;

    private User userC;
    private boolean isModif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = DatabaseClient.getInstance(getApplicationContext());

        setContentView(R.layout.activity_gestion_compte);

        // Récupération du DatabaseClient

        // Récupérer les vues
        deleteView = findViewById(R.id.supprCompte);
        retourView = findViewById(R.id.retour);
        modifView = findViewById(R.id.modifCompte);

        NomU = findViewById(R.id.nomG);
        PrenomU = findViewById(R.id.prenomG);
        IdU = findViewById(R.id.idG);
        ScoreMaths1 = findViewById(R.id.Score1Math);
        ScoreMaths2 = findViewById(R.id.Score2Math);
        ScoreCult1 = findViewById(R.id.Score1Gen);
        ScoreCult2 = findViewById(R.id.Score2Gen);

        NomET = findViewById(R.id.nomGtext);
        PrenomET = findViewById(R.id.prenomGtext);


        //Récupération de l'utilisateur courrant avec MyApplication
        userC = ((MyApplication) this.getApplication()).getUserCourrant(); //a instancier dans le onCreate sinon l'instanciation
        // de la classe MyApp marche pas car elle n'est pas connu

        isModif = false;

        PrenomU.setText("Prénom : "+userC.getPrenom());
        NomU.setText("Nom : "+userC.getNom());
        IdU.setText("ID : "+userC.getId());
        ScoreMaths1.setText(ScoreMaths1.getText()+""+userC.getScore1M()+"/10");
        ScoreMaths2.setText(ScoreMaths2.getText()+""+userC.getScore2M()+"/10");
        ScoreCult1.setText(ScoreCult1.getText()+""+userC.getScore1G()+"/10");
        ScoreCult2.setText(ScoreCult2.getText()+""+userC.getScore2G()+"/7");

        // Associer un événement au bouton save
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
                Intent intent = new Intent(GestionCompteActivity.this,MainActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        modifView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isModif == false){
                    NomU.setText("Nom : ");
                    NomET.setVisibility(View.VISIBLE);
                    NomET.setText(userC.getNom());

                    PrenomU.setText("Prénom : ");
                    PrenomET.setVisibility(View.VISIBLE);
                    PrenomET.setText(userC.getPrenom());

                    modifView.setText("Fini");
                    isModif = true;
                }else {
                    updateUser();
                    userC.setNom(NomET.getText().toString());
                    userC.setPrenom(PrenomET.getText().toString());

                    NomU.setText("Nom : "+userC.getNom());
                    NomET.setVisibility(View.INVISIBLE);
                    NomET.setText(" ");

                    PrenomU.setText("Prénom : "+userC.getPrenom());
                    PrenomET.setVisibility(View.INVISIBLE);
                    PrenomET.setText(" ");

                    modifView.setText("Modifier");
                    isModif = false;
                }

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
                User user = userC;

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

    private void updateUser() {

        // Récupérer les informations contenues dans les vues
        final String sNom = NomET.getText().toString().trim();
        final String sPrenom = PrenomET.getText().toString().trim();

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class UpdateUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a task
                User user = userC;
                user.setPrenom(sPrenom);
                user.setNom(sNom);

                // adding to database
                mDb.getAppDatabase()
                        .UserDao()
                        .update(user);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        UpdateUser st = new UpdateUser();
        st.execute();
    }

}