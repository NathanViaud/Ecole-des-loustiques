package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projet.BaseDeDonnée.User;

public class JouerActivity extends AppCompatActivity {

    private Button profil;
    private User userC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        profil = findViewById(R.id.profilU);
        userC = ((MyApplication) this.getApplication()).getUserCourrant();

        if (userC == null){
            profil.setVisibility(View.INVISIBLE);
        }else profil.setVisibility(View.VISIBLE);

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JouerActivity.this,GestionCompteActivity.class );
                startActivity(intent);
            }
        });
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