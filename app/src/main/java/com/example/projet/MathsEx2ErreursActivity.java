package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MathsEx2ErreursActivity extends AppCompatActivity {

    public static final String NB_ERR = "nb_err";
    private int erreur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_ex2_erreurs);

        erreur = getIntent().getIntExtra(NB_ERR, 0);

        TextView viewErreur = findViewById(R.id.nb_erreurs);

        viewErreur.setText("Nombres d'erreurs : "+erreur);
    }

    public void Change(View view){
        Intent intent = new Intent(this, JouerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void Correct(View view){
        //MathsEx2Activity.maj();
        //MathsEx2Activity.correction = true;
        super.finish();
    }
}