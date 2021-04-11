package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class MathsExo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On charge le XML pour créer l'arbre graphique
        setContentView(R.layout.activity_mathexo1);

        NumberPicker picker = findViewById(R.id.picker);
        Button button = findViewById(R.id.allerATable);

        picker.setMinValue(1);//valeur minimal et maximal du picker
        picker.setMaxValue(9);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = picker.getValue();//quand le boutton est cliqué on récupere le nombre selectionner
                String num = Integer.toString(n);//transformation en strign
                Intent intent = new Intent(MathsExo1Activity.this, MathsExo1MultiplicationAcitivity.class);
                intent.putExtra((MathsExo1MultiplicationAcitivity.NUM_KEY),num);//on passe en clé le numéro sélectionner
                startActivity(intent);//nouveau intent pour lancer l'activité qui affiche la table
            }
        });

    }
}
