package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MathsExo1ErreurActivity extends AppCompatActivity {

    public static final String NUM_KEY_ERR = "num_KEY_ERR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreur);

        Button retourTbl = (Button)findViewById(R.id.corRep);
        Button chgTbl = (Button)findViewById(R.id.chgmtTabl);
        TextView nb = (TextView)findViewById(R.id.nbE);

        String numEr = getIntent().getStringExtra(NUM_KEY_ERR);
        nb.setText("Nombre d'erreur(s) : "+numEr);

        retourTbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        chgTbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MathsExo1ErreurActivity.this, JouerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}