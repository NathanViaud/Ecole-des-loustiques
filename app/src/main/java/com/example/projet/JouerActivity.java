package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JouerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);
    }
    public void maths(View view){
        Intent intent = new Intent(this,MathsActivity.class );
        startActivity(intent);
    }
    public void culture(View view){
        Intent intent = new Intent(this,CultureActivity.class );
        startActivity(intent);
    }
}