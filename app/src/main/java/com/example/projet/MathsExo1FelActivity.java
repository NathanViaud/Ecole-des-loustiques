package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MathsExo1FelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felicitation);

        Button chgTbl = (Button)findViewById(R.id.chgmtTabl);
        Button retourExo = (Button)findViewById(R.id.choixExo);

        chgTbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MathsExo1FelActivity.this, MathsExo1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //on retourne au choix de la table
                startActivity(intent);
            }
        });

        retourExo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MathsExo1FelActivity.this, JouerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// on retourne au menu Jouer, (on supprime le dessus de la pile)
                startActivity(intent);
            }
        });
    }
}