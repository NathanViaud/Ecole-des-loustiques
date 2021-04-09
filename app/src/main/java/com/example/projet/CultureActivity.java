package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CultureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);
    }

    public void Exercice1(View view){
        Intent intent = new Intent(this, CultureExo1Activity.class);
        startActivity(intent);
    }

    public void Exercice2(View view){
        Intent intent = new Intent(this, CultureExo2Activity.class);
        startActivity(intent);
    }
}