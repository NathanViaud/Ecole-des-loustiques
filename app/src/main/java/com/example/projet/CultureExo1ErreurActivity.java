package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CultureExo1ErreurActivity extends AppCompatActivity {

    public static final String NB_ERR = "nb_err";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_exo1_erreur);

        int erreur = getIntent().getIntExtra(NB_ERR, 0);

        TextView viewErreur = findViewById(R.id.nb_erreurs);

        viewErreur.setText("Nombres d'erreurs : "+ erreur);
    }
    public void Change(View view){
        Intent intent = new Intent(this, JouerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void Correct(View view){
        super.finish();
    }
}