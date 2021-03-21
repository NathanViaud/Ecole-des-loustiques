package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MathsEx2Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_ex2_selection);
    }

    public void Valider(View view){
        char type = '+';
        int ordre = 10 ;



        RadioButton bDix = findViewById(R.id.dix);
        RadioButton bCent = findViewById(R.id.cent);
        RadioButton bMille = findViewById(R.id.mille);

        RadioButton bPlus = findViewById(R.id.plus);
        RadioButton bMoins = findViewById(R.id.moins);
        RadioButton bFois = findViewById(R.id.fois);
        RadioButton bDiv = findViewById(R.id.div);

        final int  dix = bDix.getId();
        final int cent = bCent.getId();
        final int mille = bMille.getId();



        if (bDix.isChecked()){
            ordre = 10;
        } else if(bCent.isChecked()){
            ordre = 100;
        } else if(bMille.isChecked()){
            ordre = 1000;
        }

        if (bPlus.isChecked()){
            type = '+';
        } else if(bMoins.isChecked()){
            type = '-';
        } else if(bFois.isChecked()){
            type = 'x';
        } else if(bDiv.isChecked()){
            type = '/';
        }

        Intent intent = new Intent(this, MathsEx2Activity.class);
        intent.putExtra(MathsEx2Activity.TYPE, type);
        intent.putExtra(MathsEx2Activity.ORDRE, ordre);
        startActivity(intent);
    }
}