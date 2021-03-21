package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MathsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths);

        Button bExo1 = (Button) findViewById(R.id.ex1);

        bExo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathsActivity.this, MathsExo1Activity.class );
                startActivity(intent);
            }
        });

    }

    public void Exercice2(View view){
        Intent intent = new Intent(this, MathsEx2Selection.class);
        startActivity(intent);
    }
}