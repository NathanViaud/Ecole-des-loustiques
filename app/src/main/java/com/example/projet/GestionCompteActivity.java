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

import com.example.projet.BaseDeDonnée.DatabaseClient;
import com.example.projet.BaseDeDonnée.User;

public class GestionCompteActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    private Button modifView;

    private TextView NomUser;
    private TextView PrenomUser;

    private EditText NomEditT, PrenomEditT;

    private User userC;
    private boolean isModif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupération de l'instace du DatabaseClient pour la bdd
        mDb = DatabaseClient.getInstance(getApplicationContext());

        setContentView(R.layout.activity_gestion_compte);

        // VIEW
        //BUTTONS
        Button deleteView = findViewById(R.id.supprCompte);
        Button retourView = findViewById(R.id.retour);
        modifView = findViewById(R.id.modifCompte);

        //TEXT VIEW
        NomUser = findViewById(R.id.nomGestCompte);
        PrenomUser = findViewById(R.id.prenomGestCompte);
        TextView idUser = findViewById(R.id.idGestCompte);
        TextView scoreMaths1 = findViewById(R.id.Score1Math);
        TextView scoreMaths2 = findViewById(R.id.Score2Math);
        TextView scoreCult1 = findViewById(R.id.Score1Gen);
        TextView scoreCult2 = findViewById(R.id.Score2Gen);

        //EDIT TEXT
        NomEditT = findViewById(R.id.nomGestCompteEditT);
        PrenomEditT = findViewById(R.id.prenomGestCompteEditT);

        //Récupération de l'utilisateur courrant avec MyApplication
        userC = ((MyApplication) this.getApplication()).getUserCourrant(); //a instancier dans le onCreate sinon l'instanciation
        // de la classe MyApp marche pas car elle n'est pas connu

        isModif = false;

        PrenomUser.setText("Prénom : "+userC.getPrenom());
        NomUser.setText("Nom : "+userC.getNom());
        idUser.setText("ID : "+userC.getId());

        //test pour voir si l'utilisateur courrant a deja fait les exercies
        if(userC.getScore1M() == null){
            scoreMaths1.setText(scoreMaths1.getText()+"Jamais fait");
        }else{
            scoreMaths1.setText(scoreMaths1.getText()+""+userC.getScore1M()+"/10");
        }
        if(userC.getScore2M() == null){
            scoreMaths2.setText(scoreMaths2.getText()+"Jamais fait");
        }else{
            scoreMaths2.setText(scoreMaths2.getText()+""+userC.getScore2M()+"/10");
        }
        if(userC.getScore1G() == null){
            scoreCult1.setText(scoreCult1.getText()+"Jamais fait");
        }else{
            scoreCult1.setText(scoreCult1.getText()+""+userC.getScore1G()+"/10");
        }
        if(userC.getScore2G() == null){
            scoreCult2.setText(scoreCult2.getText()+"Jamais fait");
        }else{
            scoreCult2.setText(scoreCult2.getText()+""+userC.getScore2G()+"/7");
        }

        // Associer un événement au bouton save
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
                Intent intent = new Intent(GestionCompteActivity.this,MainActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // flag pour clear le dessus de la pile
                startActivity(intent);
            }
        });

        modifView.setOnClickListener(new View.OnClickListener() { //modification des informations de l'user
            @Override
            public void onClick(View view) {
                if (isModif == false){ // si le boutton a été cliquer
                    NomUser.setText("Nom : ");
                    NomEditT.setVisibility(View.VISIBLE); // on rend visible les edit text
                    NomEditT.setText(userC.getNom());

                    PrenomUser.setText("Prénom : ");
                    PrenomEditT.setVisibility(View.VISIBLE);
                    PrenomEditT.setText(userC.getPrenom());

                    modifView.setText("Fini");
                    isModif = true; //on modifie pour dire que le prochain clique déclenchera la modification
                }else {
                    updateUser(); // sinon on update l'user avec la valeur dans les edit Text
                    userC.setNom(NomEditT.getText().toString());
                    userC.setPrenom(PrenomEditT.getText().toString());

                    NomUser.setText("Nom : "+userC.getNom()); //on affiche le nouveau nom
                    NomEditT.setVisibility(View.INVISIBLE); // on rend invisible les edit text
                    NomEditT.setText(" ");

                    PrenomUser.setText("Prénom : "+userC.getPrenom());
                    PrenomEditT.setVisibility(View.INVISIBLE);
                    PrenomEditT.setText(" ");

                    modifView.setText("Modifier");
                    isModif = false; //on modifie pour dire que la modification n'est plus entrain d'etre faite
                }

            }
        });

        retourView.setOnClickListener(new View.OnClickListener() { // boutton retour
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void deleteUser() { //méthode pour delete l'user courrant de la bdd

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class DeleteUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a task
                User user = userC; // on récupere l user courrant

                // adding to database
                mDb.getAppDatabase()
                        .UserDao()
                        .delete(user); //on le supprime de la bdd avec la méthode du Dao

                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                // Quand la tache est créée, on arrête l'activité (on l'enleve de la pile d'activités)
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

        // Récupérer les informations contenues dans les vues Edit text
        final String sNom = NomEditT.getText().toString().trim();
        final String sPrenom = PrenomEditT.getText().toString().trim();

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class UpdateUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                User user = userC; // on récupere l'user courrant
                user.setPrenom(sPrenom); // attribution des nouvèlles valeurs
                user.setNom(sNom);

                mDb.getAppDatabase()
                        .UserDao()
                        .update(user);//on le modifie de la bdd avec la méthode du Dao

                return user; //on le renvoi modifier
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