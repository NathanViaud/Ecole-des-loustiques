package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet.CultureEx1Data.Quizz;


public class CultureExo1Activity extends AppCompatActivity {

    private int index;
    private Quizz m_quizz;
    private ColorStateList defaultColor;

    private boolean correction = false;

    private boolean fin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_exo1);
        index =0;
        m_quizz = new Quizz();
        EditText resView = findViewById(R.id.CEx1Res);
        ImageView drapeau = findViewById(R.id.drapeau);

        String ressourceName = m_quizz.getDrapeau(index);
        int resID = getResources().getIdentifier(ressourceName , "drawable", this.getPackageName());
        drapeau.setImageResource(resID);
        defaultColor = resView.getTextColors();
        TextView drap = findViewById(R.id.Exercice1);
        drap.setText(ressourceName);
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

    public void maj(){
        EditText resView = findViewById(R.id.CEx1Res);
        TextView calculView = findViewById(R.id.ex2Req);
        TextView indexView = findViewById(R.id.index);
        resView.setTextColor(defaultColor);
        if(index <10) {
            correct();

        } else{
            int erreur = m_quizz.Correction();
            if(erreur>0){
                Intent intent = new Intent(this, CultureExo1ErreurActivity.class);
                intent.putExtra(MathsEx2ErreursActivity.NB_ERR, erreur);
                startActivity(intent);
                fin = true;
                index --;
            } else{
                Intent intent = new Intent(this, CultureExo1FelActivity.class);
                startActivity(intent);
            }

        }
    }

    public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        super.onKeyUp(keyCode, event);
        EditText resView = findViewById(R.id.CEx1Res);
        resView.setTextColor(defaultColor);
        return false;
    };

    public void correct (){
        EditText resView = findViewById(R.id.CEx1Res);
        ImageView drapeau = findViewById(R.id.drapeau);
        TextView indexView = findViewById(R.id.index);
        resView.setTextColor(defaultColor);
        resView.setText(m_quizz.getResAt(index));
        if(!m_quizz.correct(index) && correction == true){
            resView.setTextColor(Color.RED);
        } else if (m_quizz.correct(index) && correction == true){
            resView.setTextColor(Color.GREEN);
        }

        if(m_quizz.getResAt(index).equals("null")){
            resView.setText("");
        }
        String ressourceName = m_quizz.getDrapeau(index);
        int resID = getResources().getIdentifier(ressourceName , "drawable", this.getPackageName());
        drapeau.setImageResource(resID);
        indexView.setText(String.valueOf(index+1) + "/10");
        TextView drap = findViewById(R.id.Exercice1);
        drap.setText(ressourceName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fin){
            correction = true;
            View modeCorrection = findViewById(R.id.mode_correction);
            modeCorrection.setVisibility(View.VISIBLE);
            correct();
        }

    }
}