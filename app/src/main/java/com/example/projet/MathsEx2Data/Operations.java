package com.example.projet.MathsEx2Data;
import com.example.projet.MathsEx2Data.Operation;

import java.util.ArrayList;
import java.util.Vector;

public class Operations {
    private int size = 0;
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


    public void correction(){
        nb_erreurs = 0;
        for(int i =0;i<m_operations.size();i++){
            if(!m_operations.get(i).Correction(u_res.get(i))){
                nb_erreurs++;
            }
        }
    }

    public void addRes(int res){
        u_res.add(res);
        this.size ++;
    }

    public boolean existe(int i){
        if(i >= u_res.size()){
            return false;
        } else{
            return true;
        }
    }

    public void removeRes(int i){u_res.remove(i);}

    public int getNbErreurs(){ return this.nb_erreurs; }

    public int getNbReps(){
        return m_operations.size()-this.nb_erreurs;
    }

    public void setRes(int index, int res){
        try {
            u_res.set(index, res);
        } catch (IndexOutOfBoundsException e){
            u_res.add(res);
        }

    }

    public int getResAt(int index){
        return u_res.get(index);
    }

    public char getType(){return this.type;}


}
