package com.example.projet.MathsEx2Data;
import com.example.projet.MathsEx2Data.Operation;

import java.util.ArrayList;
import java.util.Vector;

public class Operations {
    private int index =0;
    private int operande;
    private ArrayList<Operation> m_operations = new ArrayList<>();
    private int nb_erreurs = 0;
    private ArrayList<Integer> u_res = new ArrayList<>();
    private char type;

    public Operations(int operande, char type){
        this.operande = operande;
        this.type = type;
        for(int i =0;i<10;i++){
            Operation item = new Operation(operande, type);
            m_operations.add(item);
        }
    }

    public Operation getOperation(int index){
        return m_operations.get(index);
    }

    public void MAJ(){
        this.index ++;
    }

    public void correction(){
        for(int i =0;i<m_operations.size();i++){
            if(!m_operations.get(i).Correction(u_res.get(i))){
                nb_erreurs++;
            }
        }
    }

    public void addRes(int res){
        u_res.add(res);
    }

    public void removeRes(){u_res.remove(0);}

    public int getNbErreurs(){
        return this.nb_erreurs;
    }

    public char getType(){return this.type;}


}
