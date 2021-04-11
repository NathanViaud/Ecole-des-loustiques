package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.MathsEx1Data.Multiplication;
import com.example.projet.MathsEx1Data.TableDeMultiplication;
import com.example.projet.BaseDeDonnée.DatabaseClient;
import com.example.projet.BaseDeDonnée.User;

import java.util.ArrayList;

public class MathsExo1MultiplicationAcitivity extends AppCompatActivity {

    public static final String NUM_KEY= "num_KEY"; //nombre de la table sélectionné, envoyé par MathsExo1Activity

    private TableDeMultiplication tableDeMultiplication;
    private ArrayList<EditText> reponsesUtilisateursTview;

    private boolean mode_correction = false;
    private boolean fin = false;

    private User userC;
    private DatabaseClient mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_mult);

        userC = ((MyApplication) this.getApplication()).getUserCourrant();//récupération user Courrant en passant par MyApplication
        mDb = DatabaseClient.getInstance(getApplicationContext());

        String stbl = getIntent().getStringExtra(NUM_KEY); //récuperation du numéro de la table
        int table = Integer.parseInt(stbl);// transformation en int pour pouvoir s'en servir

        tableDeMultiplication = new TableDeMultiplication(table); // creation de table lié au numéro

        tableDeMultiplication.shuffle(); //on mélange l'odre des multiplications

        Button validerView = findViewById(R.id.valider);

        reponsesUtilisateursTview = new ArrayList<>();
        majGraphique(); //on fait la maj graphique pour afficher la table



        validerView.setOnClickListener(new View.OnClickListener() {//boutton valider
            @Override
            public void onClick(View v) {

                int i = 0; // creation de lindex pour les listes
                for(EditText reponsesUtilisateursTview : reponsesUtilisateursTview){ // on boucle sur la liste des réponses de l'utilisateur jusqua la derniere rep
                    if (!TextUtils.isEmpty(reponsesUtilisateursTview.getText())){ //on verifie que la réponse existe : cad si il a un nombre ou que le string est null donc que lutilisateur na rien rempli
                        int reponseUtilisateur = Integer.parseInt(reponsesUtilisateursTview.getText().toString()); //on transforme le string en int
                        tableDeMultiplication.getMultiplication(i).setReponseUtilisateur(reponseUtilisateur);//on ajoute l'integer aux reponses de l'utilisateur a lindex correspondant

                    }
                    i++;//on incrémente lindex
                }
                if (tableDeMultiplication.getNombreReponsesCorrectes()==10){ // si lutilisateur a tout juste
                    majScore();//on modifie son score en base de donnée
                    Intent TbltMultVIC = new Intent(MathsExo1MultiplicationAcitivity.this, MathsExo1FelActivity.class);
                    startActivity(TbltMultVIC);// on lance l'acitivté félicitation
                }else {
                    majScore();//on modifie son score en base de donnée
                    Intent TbltMultDEF = new Intent(MathsExo1MultiplicationAcitivity.this, MathsExo1ErreurActivity.class);
                    fin = true;
                    String repFausses = Integer.toString(tableDeMultiplication.getNombreReponsesFausses());//on transforme le nombre en string pour le passe dans la clé
                    TbltMultDEF.putExtra(MathsExo1ErreurActivity.NUM_KEY_ERR, repFausses);//on passe le nombre d'erreurs a l'activité
                    startActivity(TbltMultDEF);// on lance l'acitivté erreurs
                }
            }
        });

    }

    private void majGraphique() {
        LinearLayout operationsTview = findViewById(R.id.operationsT);//récupération du layout des multiplications
        operationsTview.removeAllViews();

        for (Multiplication multiplication : tableDeMultiplication.getMultiplications()) { //on boucle dans la talble de multiplication du numéro sélectionner

            LinearLayout ligneViewTmplt = (LinearLayout) getLayoutInflater().inflate(R.layout.templt_ligne, null);//on inflate le layout correspondant a une ligne

            //on récupere les vues liés aux operandes et on les passe en string
            TextView operande1View = ligneViewTmplt.findViewById(R.id.operande1);
            TextView operande2View = ligneViewTmplt.findViewById(R.id.operande2);
            String operande1Chaine = Integer.toString(multiplication.getOperande1());
            String operande2Chaine = Integer.toString(multiplication.getOperande2());

            //on les ajoute a ces vues
            operande1View.setText(operande1Chaine);
            operande2View.setText(operande2Chaine);
            EditText resView = ligneViewTmplt.findViewById(R.id.templt_res);//

            reponsesUtilisateursTview.add(resView);//ajout a la liste la reponse : la valeur rentrer dans la l'edit text correspondant a la ligne
            operationsTview.addView(ligneViewTmplt);//ajout de la ligne avec les operandes et la valeur du edit text a la vue

        }

    }

    // Permet de mettre en couleur(rouge ou vert) lors du mode Correction
    private void majCorrection(){
        for (int i=0; i<tableDeMultiplication.getMultiplications().size(); i++) {
            Multiplication m_mult = tableDeMultiplication.getMultiplication(i);
            if (m_mult.existReponseUtilisateur()) {
                if (m_mult.isReponseCorrecte()) {
                    reponsesUtilisateursTview.get(i).setTextColor(Color.GREEN);

                } else {
                    reponsesUtilisateursTview.get(i).setTextColor(Color.RED);
                }

            }

        }
    }


    //Activation du mode correction
    @Override
    protected void onStart() {
        super.onStart();
        if(fin){
            mode_correction = true;
            View modeCorrection = findViewById(R.id.mode_correction);
            modeCorrection.setVisibility(View.VISIBLE);
            majCorrection();
        }
    }

    private void majScore() {

        final int Score = tableDeMultiplication.getNombreReponsesCorrectes(); // récuperation du score

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class UpdateUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                if (userC != null){//si l'user courrant existe
                    User user = userC;
                    if(!mode_correction){//et si on est pas en mode correction

                        user.setScore1M(Score);//on modifie le score de l'user courrant de l'exercice correspondant

                    }
                    // adding to database
                    mDb.getAppDatabase()
                            .UserDao()
                            .update(user);//on update l'user dans la bdd avec la nouvelle valeur du score

                    return user;//on le return
                }else return null;//si l'utilisateur n'existe pas on ne renvoi null

            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                Toast.makeText(getApplicationContext(), "Score Updated", Toast.LENGTH_LONG).show();//on affiche l'update
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        UpdateUser st = new UpdateUser();
        st.execute();
    }

}