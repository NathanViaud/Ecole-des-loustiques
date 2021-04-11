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

        picker.setMinValue(1);
        picker.setMaxValue(9);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = picker.getValue();
                String num = Integer.toString(n);
                Intent intent = new Intent(MathsExo1Activity.this, MathsExo1MultiplicationAcitivity.class);
                intent.putExtra((MathsExo1MultiplicationAcitivity.NUM_KEY),num);
                startActivity(intent);
            }
        });

    }
}
