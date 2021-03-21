package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MathsEx2FinActivity extends AppCompatActivity {

    public static final String NB_ERR = "nb_err";
    private int erreur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_ex2_fin);

        erreur = getIntent().getIntExtra(NB_ERR, 0);

        TextView viewErreur = findViewById(R.id.nb_erreurs);

        viewErreur.setText("Nombres d'erreurs : "+erreur);
    }
}