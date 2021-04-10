package com.example.projet.MathsEx1Data;

import java.util.ArrayList;
import java.util.Collections;

public class TableDeMultiplication {

    private static final int MAX_OPERATIONS = 10;

    private ArrayList<Multiplication> multiplications;

    public TableDeMultiplication(int table){

        multiplications =  new ArrayList<>();

        for (int i = 0; i < MAX_OPERATIONS; i++){

            int operande1 = table;
            int operande2 = i+1;

            Multiplication multiplication = new Multiplication(operande1, operande2);
            multiplications.add(multiplication);

        }
    }

    public ArrayList<Multiplication> getMultiplications() {
        return multiplications;
    }

    public Multiplication getMultiplication(int i){
        return multiplications.get(i);
    }

    public void shuffle(){
        Collections.shuffle(multiplications);
    }

    public int getNombreReponsesCorrectes(){
         int nbReponsesCorrecetes = 0;

         for (Multiplication multiplication  : multiplications){
             if (multiplication.isReponseCorrecte()){
                 nbReponsesCorrecetes++;
             }
         }
         return nbReponsesCorrecetes;
    }

    public int getNombreReponsesFausses(){
        return (MAX_OPERATIONS-getNombreReponsesCorrectes());
    }

}
