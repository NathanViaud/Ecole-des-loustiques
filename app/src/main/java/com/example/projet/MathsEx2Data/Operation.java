package com.example.projet.MathsEx2Data;

import java.util.Random;

public class Operation {

    private int res;
    private int op1;
    private int op2;
    private int operande;

    public Operation(int operande, char type){
        this.operande = operande;
        this.op1 = getRandomNumberInRange(0, operande);
        this.op2 = getRandomNumberInRange(0, operande);
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