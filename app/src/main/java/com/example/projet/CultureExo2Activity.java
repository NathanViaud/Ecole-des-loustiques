package com.example.projet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.BaseDeDonnée.DatabaseClient;
import com.example.projet.BaseDeDonnée.QuestionsReponses;
import com.example.projet.BaseDeDonnée.User;

import java.util.ArrayList;
import java.util.List;

public class CultureExo2Activity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    // VIEW
    private RadioButton reponse1view, reponse2view, reponse3view;
    private RadioGroup radioGroup;
    private TextView QuestionView;

    //variables
    private Integer index, erreurs;
    private ArrayList<Integer> reponses;
    private boolean mode_correction =false;
    private boolean lastQ = false;

    private User userC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_exo2);

        //instanciation
        mDb = DatabaseClient.getInstance(getApplicationContext());
        userC = ((MyApplication) this.getApplication()).getUserCourrant();

        reponse1view = findViewById(R.id.ex2Rep1);
        reponse2view = findViewById(R.id.ex2Rep2);
        reponse3view = findViewById(R.id.ex2Rep3);

        radioGroup = findViewById(R.id.ex2_radioGrp);

        QuestionView = findViewById(R.id.ex2Question);

        index = 0;
        erreurs = 0;
        reponses = new ArrayList<>();

        for(int i=0;i<=6;i++){
            reponses.add(0);//on ajoute par défaut la position 0 a toutes les questions
        }


        getQuestionsRep();//on lance la recuperation des questions reponses


    }

    public void Suivant(View view){
        maj();
        index++;
        getQuestionsRep();

    }

    public void Precedent(View view){
        maj();//mise a jour de la liste des réponses
        index--;
        getQuestionsRep();
    }

    public void maj(){

        //en fonction de la checkbox cliquer on met a jour la liste de la position des réponses
           if (reponse1view.isChecked()){
               reponses.set(index,1);
           }else if(reponse2view.isChecked()){
               reponses.set(index,2);
           }else if(reponse3view.isChecked()) {
               reponses.set(index,3);
           }else reponses.set(index,0);


       }






    private void getQuestionsRep() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des questionsReponses et de mettre à jour le textView question et le radioGroup des questions
        class GetQuestionsRep extends AsyncTask<Void, Void, List<QuestionsReponses>> {

            @Override
            protected List<QuestionsReponses> doInBackground(Void... voids) {
                List<QuestionsReponses> questionsReponsesList = mDb.getAppDatabase()//recuperation de toutes les questions reponses de la base de donnée
                        .QuestionsReponsesDao()
                        .getAll();
                return questionsReponsesList;
            }

            @Override
            protected void onPostExecute(List<QuestionsReponses> questionsReponses) {
                super.onPostExecute(questionsReponses);

                if (index > 6){//si on est a la derniere question
                    int val = 0;
                    for (int i = 0; i<=6; i++){//on boucle sur le nombre de questions
                        if (reponses.get(i) != questionsReponses.get(i).getReponseCorrect() || reponses.get(i) == 0){
                            //si la postion de la reponses a lindex donnée ne correspond pas a celui de la base de donnée
                            val++; // on ajoute une erreurs
                        }
                    }
                    erreurs = val;
                    mode_correction = true;
                    index --;
                    if(erreurs >0){
                        majScore();//mise a jour du score
                        Intent intent = new Intent(CultureExo2Activity.this, CultureExo1ErreurActivity.class);
                        intent.putExtra(MathsEx2ErreursActivity.NB_ERR, erreurs);//ajout du nombre derreurs a la clé
                        startActivity(intent);//on lance l'acitivité erreurs
                        lastQ = reponses.get(index) != questionsReponses.get(index).getReponseCorrect();//on verifie la derniere question
                    } else {
                        majScore();
                        Intent intent = new Intent(CultureExo2Activity.this, CultureExo1FelActivity.class);
                        startActivity(intent);
                    }

                } else{//sinon cela veut dire quon est pas a la derniere questions
                    //on modifie les vues avec les valeurs de la bdd
                    QuestionView.setText(questionsReponses.get(index).getQuestion());

                    reponse1view.setText(questionsReponses.get(index).getReponse1());
                    reponse2view.setText(questionsReponses.get(index).getReponse2());
                    reponse3view.setText(questionsReponses.get(index).getReponse3());
                    switch(reponses.get(index)){//on sauvgarde le clique du radio button
                        case 1:
                            reponse1view.setChecked(true);
                            break;
                        case 2:
                            reponse2view.setChecked(true);
                            break;
                        case 3:
                            reponse3view.setChecked(true);
                            break;
                        default:
                            radioGroup.clearCheck();
                            break;
                    }
                    //on lance le mode correction
                    TextView erreurView = findViewById(R.id.erreur);
                    if(mode_correction && reponses.get(index) != questionsReponses.get(index).getReponseCorrect()){
                        erreurView.setVisibility(View.VISIBLE);
                    } else{
                        erreurView.setVisibility(View.INVISIBLE);
                    }
                }


            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetQuestionsRep et execution de la demande asynchrone
        GetQuestionsRep gt = new GetQuestionsRep();
        gt.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView erreurView = findViewById(R.id.erreur);
        if(mode_correction){
            if(lastQ){
                erreurView.setVisibility(View.VISIBLE);
            } else{
                erreurView.setVisibility(View.INVISIBLE);
            }
        }

    }

    private void majScore() {//epliquer dans MathsExo1MMultiplicationActivity

        final int Score = 7-erreurs;

        /**
         * Création d'une classe asynchrone pour supprimer la tache donnée par l'utilisateur
         */
        class UpdateUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                if (userC != null){
                    User user = userC;
                    user.setScore2G(Score);


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
