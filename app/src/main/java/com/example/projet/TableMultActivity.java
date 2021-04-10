package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projet.MathsEx1Data.Multiplication;
import com.example.projet.MathsEx1Data.TableDeMultiplication;

import java.util.ArrayList;






public class TableMultActivity extends AppCompatActivity {

    public static final String NUM_KEY= "num_KEY";

    private TableDeMultiplication tableDeMultiplication;
    private LinearLayout operationsTview;
    private Button validerView;
    private ArrayList<EditText> reponsesUtilisateursTview;

    private boolean mode_correction = false;
    private boolean fin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_mult);

        String stbl = getIntent().getStringExtra(NUM_KEY);
        int table = Integer.parseInt(stbl);

        tableDeMultiplication = new TableDeMultiplication(table);

        tableDeMultiplication.shuffle();

        operationsTview = findViewById(R.id.operationsT);
        validerView = findViewById(R.id.valider);
        reponsesUtilisateursTview = new ArrayList<>();
        majGraphique();



        validerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = 0;
                for(EditText reponsesUtilisateursTview : reponsesUtilisateursTview){
                    if (!TextUtils.isEmpty(reponsesUtilisateursTview.getText())){
                        int reponseUtilisateur = Integer.parseInt(reponsesUtilisateursTview.getText().toString());
                        tableDeMultiplication.getMultiplication(i).setReponseUtilisateur(reponseUtilisateur);

                    }
                    i++;
                }
                if (tableDeMultiplication.getNombreReponsesCorrectes()==10){
                    Intent TbltMultVIC = new Intent(TableMultActivity.this,FelicitationActivity.class);
                    startActivity(TbltMultVIC);
                }else {
                Intent TbltMultDEF = new Intent(TableMultActivity.this,ErreurActivity.class);
                fin = true;
                String repFausses = Integer.toString(tableDeMultiplication.getNombreReponsesFausses());
                TbltMultDEF.putExtra(ErreurActivity.NUM_KEYE, repFausses);
                startActivity(TbltMultDEF);}
            }
        });

    }

    private void majGraphique() {
        LinearLayout operationsTview = findViewById(R.id.operationsT);
        operationsTview.removeAllViews();

        for (Multiplication multiplication : tableDeMultiplication.getMultiplications()) {

            LinearLayout ligneViewTmplt = (LinearLayout) getLayoutInflater().inflate(R.layout.templt_ligne, null);

            TextView operande1View = ligneViewTmplt.findViewById(R.id.operande1);
            TextView operande2View = ligneViewTmplt.findViewById(R.id.operande2);
            String operande1Chaine = Integer.toString(multiplication.getOperande1());
            String operande2Chaine = Integer.toString(multiplication.getOperande2());
            operande1View.setText(operande1Chaine);
            operande2View.setText(operande2Chaine);
            EditText resView = ligneViewTmplt.findViewById(R.id.templt_res);
            reponsesUtilisateursTview.add(resView);
            operationsTview.addView(ligneViewTmplt);

        }

    }

    // Permet de mettre en couleur(rouge ou vert) lors du mode Correction
    private void majCorrection(){
        for (int i=0; i<tableDeMultiplication.getMultiplications().size(); i++) {
            Multiplication m_mult = tableDeMultiplication.getMultiplication(i);
            if (m_mult.existReponseUtilisateur()) {
                if (m_mult.isReponseCorrecte()) {
                    reponsesUtilisateursTview.get(i).setTextColor(Color.GREEN);

                } else {
                    reponsesUtilisateursTview.get(i).setTextColor(Color.RED);
                }

            }

        }
    }


    //Activation du mode correction
    @Override
    protected void onStart() {
        super.onStart();
        if(fin){
            mode_correction = true;
            View modeCorrection = findViewById(R.id.mode_correction);
            modeCorrection.setVisibility(View.VISIBLE);
            majCorrection();
        }
    }

}