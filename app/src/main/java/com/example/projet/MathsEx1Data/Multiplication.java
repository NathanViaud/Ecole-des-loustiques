package com.example.projet.MathsEx1Data;

public class Multiplication {

    private final int operande1;
    private final int operande2;
    private Integer reponseUtilisateur;

    public Multiplication(int operande1, int operande2){
        this.operande1 = operande1;
        this.operande2 = operande2;
    }

    public int getOperande1(){
        return operande1;
    }

    public int getOperande2(){
        return operande2;
    }

    public int getReponseUtilisateur(){
        return reponseUtilisateur;
    }

    public void setReponseUtilisateur(int reponseUtilisateur){
        this.reponseUtilisateur = reponseUtilisateur;
    }

    public boolean existReponseUtilisateur(){
        return reponseUtilisateur != null;
    }

    public boolean isReponseCorrecte(){
        return existReponseUtilisateur() && ((operande1 * operande2) == reponseUtilisateur);
    }

}
