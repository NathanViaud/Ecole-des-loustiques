package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projet.MathsEx2Data.Operations;
import android.widget.Toast;

public class MathsEx2Activity extends AppCompatActivity {

    public static final String TYPE = "type";
    public static final String ORDRE = "ordre";

    private Operations m_operations;
    private int index = 0;
    private char type;
    private int ordre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_ex2);

        type = getIntent().getCharExtra(TYPE,'+');
        ordre = getIntent().getIntExtra(ORDRE,0);

        m_operations = new Operations(ordre, type);
        TextView calcul = findViewById(R.id.ex2Req);
        calcul.setText(m_operations.getOperation(index).getOp1() +" "+ m_operations.getType() +" " + m_operations.getOperation(index).getOp2()+ " = ");
    }

    public void Suivant(View view){
        EditText resView = findViewById(R.id.ex2Res);
        String resString = resView.getText().toString();
        int res;
        if(resString.matches("")){
            res = -1;
        }else{
            res = Integer.parseInt(resString);
        }
        if(m_operations.getOperation(index).Correction(res)){
            Toast.makeText(this, "Réussi", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Raté", Toast.LENGTH_SHORT).show();
        }
        m_operations.addRes(res);
        index++;
        maj();
    }

    public void Precedent(View view){
        if(index>0){
            index-=1;
            m_operations.removeRes();
            maj();
        }
    }


    public void maj(){
        EditText resView = findViewById(R.id.ex2Res);
        TextView calculView = findViewById(R.id.ex2Req);
        if(index <10) {
            resView.setText("");
            calculView.setText(m_operations.getOperation(index).getOp1() + " "+ m_operations.getType() + " " + m_operations.getOperation(index).getOp2() + "= ");
        } else{
            m_operations.correction();
            int erreur = m_operations.getNbErreurs();
            Intent intent = new Intent(this, MathsEx2FinActivity.class);
            intent.putExtra(MathsEx2FinActivity.NB_ERR, erreur);
            startActivity(intent);
        }
    }
}