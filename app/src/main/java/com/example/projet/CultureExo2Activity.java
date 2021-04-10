package com.example.projet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.db.DatabaseClient;
import com.example.projet.db.QuestionsReponses;
import com.example.projet.db.User;

import java.util.ArrayList;
import java.util.List;

public class CultureExo2Activity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    // VIEW
    private RadioButton reponse1view, reponse2view, reponse3view;
    private RadioGroup radioGroup;
    private TextView QuestionView;

    private Integer index, erreurs;
    private ArrayList<Integer> reponses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_exo2);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        reponse1view = findViewById(R.id.ex2Rep1);
        reponse2view = findViewById(R.id.ex2Rep2);
        reponse3view = findViewById(R.id.ex2Rep3);

        radioGroup = findViewById(R.id.ex2_radioGrp);

        QuestionView = findViewById(R.id.ex2Question);

        index = 0;
        erreurs = 0;
        reponses = new ArrayList<>();
        for(int i=0;i<=6;i++){
            reponses.add(0);
        }


        getQuestionsRep();


    }

    public void Suivant(View view){
        maj();
        index++;
        getQuestionsRep();

    }

    public void Precedent(View view){
        index--;
        if (index < 0){
            finish();
        } else {
            getQuestionsRep();
        }
    }

    public void maj(){

           if (reponse1view.isChecked()){
               reponses.set(index,1);
               radioGroup.clearCheck();
           }else if(reponse2view.isChecked()){
               reponses.set(index,2);
               radioGroup.clearCheck();
           }else if(reponse3view.isChecked()) {
               reponses.set(index,3);
               radioGroup.clearCheck();
           }else reponses.set(index,0);

        Log.i("Test", String.valueOf(reponses.get(index)));

       }






    private void getQuestionsRep() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des questionsReponses et de mettre à jour le textView question et le radioGroup des questions
        class GetQuestionsRep extends AsyncTask<Void, Void, List<QuestionsReponses>> {

            @Override
            protected List<QuestionsReponses> doInBackground(Void... voids) {
                List<QuestionsReponses> questionsReponsesList = mDb.getAppDatabase()
                        .QuestionsReponsesDao()
                        .getAll();
                return questionsReponsesList;
            }

            @Override
            protected void onPostExecute(List<QuestionsReponses> questionsReponses) {
                super.onPostExecute(questionsReponses);

                if (index > 6){
                    for (int i = 0; i<=6; i++){
                        if (reponses.get(i) != questionsReponses.get(i).getReponseCorrect() || reponses.get(i) == 0){
                            erreurs++;
                        }
                    }
                    if(erreurs >0){
                        Intent intent = new Intent(CultureExo2Activity.this, CultureExo1ErreurActivity.class);
                        intent.putExtra(MathsEx2ErreursActivity.NB_ERR, erreurs);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(CultureExo2Activity.this, CultureExo1FelActivity.class);
                        startActivity(intent);
                    }

                } else{
                    QuestionView.setText(questionsReponses.get(index).getQuestion());

                    reponse1view.setText(questionsReponses.get(index).getReponse1());
                    reponse2view.setText(questionsReponses.get(index).getReponse2());
                    reponse3view.setText(questionsReponses.get(index).getReponse3());
                    switch(reponses.get(index)){
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
                            reponse1view.setChecked(false);
                            reponse2view.setChecked(false);
                            reponse3view.setChecked(false);
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
        erreurs =0;
    }

}
