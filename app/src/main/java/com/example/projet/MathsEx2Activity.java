package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projet.MathsEx2Data.Operations;

import org.w3c.dom.Text;

public class MathsEx2Activity extends AppCompatActivity {

    public static final String TYPE = "type";
    public static final String ORDRE = "ordre";

    private Operations m_operations;
    private int index = 0;
    private char type;
    private int ordre;
    private ColorStateList defaultColor;

    private boolean correction = false;

    private boolean fin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_ex2);

        type = getIntent().getCharExtra(TYPE,'+');
        ordre = getIntent().getIntExtra(ORDRE,0);

        m_operations = new Operations(ordre, type);
        TextView calcul = findViewById(R.id.ex2Req);
        EditText resView = findViewById(R.id.ex2Res);
        calcul.setText(m_operations.getOperation(index).getOp1() +" "+ m_operations.getType() +" " + m_operations.getOperation(index).getOp2()+ " = ");
        defaultColor = resView.getTextColors();
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
        //if(m_operations.getOperation(index).Correction(res)){
            //Toast.makeText(this, "Réussi", Toast.LENGTH_SHORT).show();
        //} else{
            //Toast.makeText(this, "Raté", Toast.LENGTH_SHORT).show();
        //}
        m_operations.setRes(index, res);
        index++;
        maj();
    }

    public void Precedent(View view){
        EditText resView = findViewById(R.id.ex2Res);
        String resString = resView.getText().toString();
        int res;
        if(resString.matches("")){
            res = -1;
        }else{
            res = Integer.parseInt(resString);
        }
        m_operations.setRes(index, res);
        if(index>0){
            index-=1;
            maj();
        }
    }


    public void maj(){
        EditText resView = findViewById(R.id.ex2Res);
        TextView calculView = findViewById(R.id.ex2Req);
        TextView indexView = findViewById(R.id.index);
        resView.setTextColor(defaultColor);
        if(index <10) {
            correct();

        } else{
            m_operations.correction();
            int erreur = m_operations.getNbErreurs();
            if(erreur>0){
                Intent intent = new Intent(this, MathsEx2ErreursActivity.class);
                intent.putExtra(MathsEx2ErreursActivity.NB_ERR, erreur);
                startActivity(intent);
                fin = true;
                index --;
            } else{
                Intent intent = new Intent(this, MathsEx2FelActivity.class);
                startActivity(intent);
            }

        }
    }


    /*
    * Enlever la couleur rouge de l'input lorsqu'on écrit dedans
    * */
    public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        super.onKeyUp(keyCode, event);
        EditText resView = findViewById(R.id.ex2Res);
        resView.setTextColor(defaultColor);
        return false;
    };

    public void correct (){
        EditText resView = findViewById(R.id.ex2Res);
        TextView calculView = findViewById(R.id.ex2Req);
        TextView indexView = findViewById(R.id.index);
        resView.setTextColor(defaultColor);
            if(m_operations.existe(index) && m_operations.getResAt(index)!=-1){
                resView.setText(String.valueOf(m_operations.getResAt(index)));
                if(!m_operations.getOperation(index).Correction(m_operations.getResAt(index)) && correction == true){
                    resView.setTextColor(Color.RED);
                } else if (m_operations.getOperation(index).Correction(m_operations.getResAt(index)) && correction == true){
                    resView.setTextColor(Color.GREEN);
                }

            } else{
                resView.setText("");
            }
            calculView.setText(m_operations.getOperation(index).getOp1() + " "+ m_operations.getType() + " " + m_operations.getOperation(index).getOp2() + "= ");
            indexView.setText(String.valueOf(index+1) + "/10");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fin){
            correction = true;

            View modeCorrection = findViewById(R.id.mode_correction);
            modeCorrection.setVisibility(View.VISIBLE);

            correct();
        }

    }
}