package com.example.projet.MathsEx2Data;

import java.util.Random;

public class Operation {

    private int res;
    private int op1;
    private int op2;
    private int operande;


    // Création d'une opération avec un type et un ordre d'opérande
    public Operation(int operande, char type){
        this.operande = operande;

        //Création des opérandes en fonction de l'ordre
        this.op1 = getRandomNumberInRange(1, operande);
        this.op2 = getRandomNumberInRange(1, operande);

        //Pas de résultat négatif en cas de soustraction
        if(this.op2 > this.op1){
            int sauv = op1;
            op1 = op2;
            op2 = sauv;
        }
        switch (type){
            case '+':
                this.res = op1+op2;
                break;
            case '-':
                this.res = op1-op2;
                break;
            case 'x':
                this.res = op1*op2;
                break;
            case '/':
                this.res = op1/op2;
                break;
        }

    }

    public int getOp1(){return this.op1;}

    public int getOp2(){return this.op2;}

    public boolean Correction(int res){
        if(res == this.res){
            return true;
        } else {
            return false;
        }
    }

    private static int getRandomNumberInRange(int min, int max){
        if(min>= max){
            throw new IllegalArgumentException("max doit etre plus grand que min");
        }

        Random r = new Random();
        return r.nextInt((max-min)+1)+min;
    }

}