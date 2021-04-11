package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.MathsEx2Data.Operations;

import com.example.projet.BaseDeDonnée.DatabaseClient;
import com.example.projet.BaseDeDonnée.User;


public class MathsEx2Activity extends AppCompatActivity {

    public static final String TYPE = "type";
    public static final String ORDRE = "ordre";

    private Operations m_operations;
    private int index = 0;
    private char type;
    private int ordre;
    private ColorStateList defaultColor;

    private boolean mode_correction = false;

    private boolean fin = false;

    private User userC;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_ex2);

        //Récuperation des choix de l'utilisateur
        type = getIntent().getCharExtra(TYPE,'+');
        ordre = getIntent().getIntExtra(ORDRE,0);


        //Creation Création de la liste d'operation
        m_operations = new Operations(ordre, type);

        TextView calcul = findViewById(R.id.ex2Req);
        EditText resView = findViewById(R.id.ex2Res);
        calcul.setText(m_operations.getOperation(index).getOp1() +" "+ m_operations.getType() +" " + m_operations.getOperation(index).getOp2()+ " = "); // affiche le calcul
        defaultColor = resView.getTextColors();

        userC = ((MyApplication) this.getApplication()).getUserCourrant();
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void Suivant(View view){
        EditText resView = findViewById(R.id.ex2Res);
        String resString = resView.getText().toString();
        int res;

        //Mise en forme du résultat avant de l'ajouter dans le modèle
        if(resString.matches("")){
            res = -1;
        }else{
            res = Integer.parseInt(resString);
        }

        m_operations.setRes(index, res);
        index++;
        maj();
    }

    public void Precedent(View view){
        EditText resView = findViewById(R.id.ex2Res);
        String resString = resView.getText().toString();
        int res;
        if(resString.matches("")){
            res = -1;
        }else{
            res = Integer.parseInt(resString);
        }
        m_operations.setRes(index, res);
        if(index>0){
            index-=1;
            maj();
        }
    }


    //Verification de la position
    public void maj(){
        EditText resView = findViewById(R.id.ex2Res);
        resView.setTextColor(defaultColor);
        if(index <10) {
            majGraph();
        } else{
            m_operations.correction();
            int erreur = m_operations.getNbErreurs();
            if(erreur>0){
                majScore();
                Intent intent = new Intent(this, MathsEx2ErreursActivity.class);
                intent.putExtra(MathsEx2ErreursActivity.NB_ERR, erreur);
                startActivity(intent);
                fin = true;
                index --;
            } else{
                majScore();
                Intent intent = new Intent(this, MathsEx2FelActivity.class);
                startActivity(intent);
            }

        }
    }


    /*
    * Enlever la couleur rouge de l'input lorsqu'on écrit dedans
    * */
    public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        super.onKeyUp(keyCode, event);
        EditText resView = findViewById(R.id.ex2Res);
        resView.setTextColor(defaultColor);
        return false;
    };

    //Gère toute la mise à jour graphique de la vue
    public void majGraph(){
        EditText resView = findViewById(R.id.ex2Res);
        TextView calculView = findViewById(R.id.ex2Req);
        TextView indexView = findViewById(R.id.index);
        resView.setTextColor(defaultColor);
            //affiche le resultat et lui donne une couleur dans le mode correction
            if(m_operations.existe(index) && m_operations.getResAt(index)!=-1){
                resView.setText(String.valueOf(m_operations.getResAt(index)));
                if(!m_operations.getOperation(index).Correction(m_operations.getResAt(index)) && mode_correction == true){
                    resView.setTextColor(Color.RED);
                    majIndex();
                } else if (m_operations.getOperation(index).Correction(m_operations.getResAt(index)) && mode_correction == true){
                    resView.setTextColor(Color.GREEN);
                    majIndex();
                }

            } else{
                resView.setText("");
            }
            calculView.setText(m_operations.getOperation(index).getOp1() + " "+ m_operations.getType() + " " + m_operations.getOperation(index).getOp2() + "= ");
            indexView.setText(String.valueOf(index+1) + "/10");
    }


    //Initialisation du mode correction
    @Override
    protected void onStart() {
        super.onStart();
        if(fin){
            mode_correction = true;

            View modeCorrection = findViewById(R.id.mode_correction);
            modeCorrection.setVisibility(View.VISIBLE);

            majGraph();
            majIndex();
        }

    }

    //affiche une liste de numéro de question dans le mode correction
    public void majIndex(){
        LinearLayout indexTview = findViewById(R.id.indexT);
        indexTview.removeAllViews();

        //Création de la liste de numéro
        for (int i=0;i<10;i++){

            LinearLayout indexTemplate = (LinearLayout)getLayoutInflater().inflate(R.layout.template_index, null);

            TextView indexView = indexTemplate.findViewById(R.id.index);
            String indexString = Integer.toString(i+1);
            indexView.setText(indexString);
            if(m_operations.getOperation(i).Correction(m_operations.getResAt(i))){
                indexView.setTextColor(Color.GREEN);
            } else{
                indexView.setTextColor(Color.RED);
            }
            indexTview.addView(indexTemplate);
        }
    }

    private void majScore() {

        final int Score = m_operations.getNbReps();

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class UpdateUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                if (userC != null){
                    User user = userC;
                    user.setScore2M(Score);


                    // adding to database
                    mDb.getAppDatabase()
                            .UserDao()
                            .update(user);

                    return user;
                }else return null;

            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                Toast.makeText(getApplicationContext(), "Score Updated", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        UpdateUser st = new UpdateUser();
        st.execute();
    }
}