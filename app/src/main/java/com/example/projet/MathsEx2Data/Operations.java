package com.example.projet.MathsEx2Data;
import android.view.inputmethod.CorrectionInfo;

import com.example.projet.MathsEx2Data.Operation;

import java.util.ArrayList;
import java.util.Vector;

public class Operations {
    private int size = 0;
    private final int operande;
    private final ArrayList<Operation> m_operations = new ArrayList<>();
    private int nb_erreurs = 0;
    private final ArrayList<Integer> u_res = new ArrayList<>();
    private final char type;


    //Création d'une liste de 10 Operations
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


    //Correction du modèle
    public void correction(){
        nb_erreurs = 0;
        for(int i =0;i<m_operations.size();i++){
            if(!m_operations.get(i).Correction(u_res.get(i))){
                nb_erreurs++;
            }
        }
    }

    public boolean existe(int i){
        return i < u_res.size();
    }

    public void removeRes(int i){u_res.remove(i);}

    public int getNbErreurs(){ return this.nb_erreurs; }

    public int getNbReps(){
        return m_operations.size()-this.nb_erreurs;
    }

    /*
    Permet de ne pas faire la différence entre ajouter une valeur
    si aucune n'as été renseignée et simplement changer la valeur à l'index si elle existe déjà
     */

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
