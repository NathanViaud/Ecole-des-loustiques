package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.CultureEx1Data.Quizz;
import com.example.projet.BaseDeDonnée.DatabaseClient;
import com.example.projet.BaseDeDonnée.User;


public class CultureExo1Activity extends AppCompatActivity {

    private int index;
    private Quizz m_quizz;
    private ColorStateList defaultColor;

    private boolean mode_correction = false;

    private boolean fin = false;

    private User userC;
    private DatabaseClient mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_exo1);
        index =0;
        m_quizz = new Quizz();
        EditText resView = findViewById(R.id.CEx1Res);

        //affichage du drapeau à l'index actuel
        ImageView drapeau = findViewById(R.id.drapeau);
        String ressourceName = m_quizz.getDrapeau(index);
        int resID = getResources().getIdentifier(ressourceName , "drawable", this.getPackageName());
        drapeau.setImageResource(resID);

        defaultColor = resView.getTextColors();

        userC = ((MyApplication) this.getApplication()).getUserCourrant();
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void Suivant(View view){
        EditText resView = findViewById(R.id.CEx1Res);
        String res = resView.getText().toString();
        m_quizz.setRes(index, res);
        index++;
        maj();
    }

    public void Precedent(View view){
        EditText resView = findViewById(R.id.CEx1Res);
        String res = resView.getText().toString();
        m_quizz.setRes(index, res);
        if(index>0){
            index-=1;
            maj();
        }
    }

    //Verification de la position, soit lancé la fin de l'activité soit la mise à jour graphique
    public void maj(){

        //Utile dans le mode correction
        EditText resView = findViewById(R.id.CEx1Res);
        resView.setTextColor(defaultColor);

        if(index <10) {
            majGraph();
        } else{
            int erreur = m_quizz.getNbErreurs();
            if(erreur>0){
                majScore();
                Intent intent = new Intent(this, CultureExo1ErreurActivity.class);
                intent.putExtra(MathsEx2ErreursActivity.NB_ERR, erreur);
                startActivity(intent);
                fin = true;
                index --;
            } else{
                majScore();
                Intent intent = new Intent(this, CultureExo1FelActivity.class);
                startActivity(intent);
            }

        }
    }

    //Utilisé dans le mode correction pour retirer la couleur lors de l'édition
    public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        super.onKeyUp(keyCode, event);
        EditText resView = findViewById(R.id.CEx1Res);
        resView.setTextColor(defaultColor);
        return false;
    };

    //Mise à jour graphique de la vue
    public void majGraph (){

        EditText resView = findViewById(R.id.CEx1Res);
        ImageView drapeau = findViewById(R.id.drapeau);
        TextView indexView = findViewById(R.id.index);
        resView.setTextColor(defaultColor);

        //affichage du resultat enregistré par le joueur à l'index
        resView.setText(m_quizz.getResAt(index));

        //Lors du mode correction permet de mettre en couleur la réponse
        if(!m_quizz.correct(index) && mode_correction == true){
            resView.setTextColor(Color.RED);
            majIndex();
        } else if (m_quizz.correct(index) && mode_correction == true){
            resView.setTextColor(Color.GREEN);
            majIndex();
        }

        if(m_quizz.getResAt(index).equals("null")){
            resView.setText("");
        }

        // affichage de l'image du drapeau a l'index
        String ressourceName = m_quizz.getDrapeau(index);
        int resID = getResources().getIdentifier(ressourceName , "drawable", this.getPackageName());
        drapeau.setImageResource(resID);

        indexView.setText(String.valueOf(index+1) + "/10");

    }

    //active le mode correction
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

    //Dans le mode correction affiche et met à jour dans le mode correction une liste des index coloré en fonction du resultat  à la question
    public void majIndex(){
        LinearLayout indexTview = findViewById(R.id.indexT);
        indexTview.removeAllViews();
        for (int i=0;i<10;i++){

            LinearLayout indexTemplate = (LinearLayout)getLayoutInflater().inflate(R.layout.template_index, null);

            TextView indexView = indexTemplate.findViewById(R.id.index);
            String indexString = Integer.toString(i+1);
            indexView.setText(indexString);
            if(m_quizz.correct(i)){
                indexView.setTextColor(Color.GREEN);
            } else{
                indexView.setTextColor(Color.RED);
            }
            indexTview.addView(indexTemplate);
        }
    }

    private void majScore() {
        final int Score = m_quizz.getNbReps();

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class UpdateUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                if (userC != null){
                    User user = userC;
                    user.setScore1G(Score);


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