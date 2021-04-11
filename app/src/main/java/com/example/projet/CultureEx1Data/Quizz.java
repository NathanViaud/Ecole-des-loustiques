package com.example.projet.CultureEx1Data;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Random;

public class Quizz {


    //Liste de tous les drapeaux possible
    private ArrayList<String> drapeaux = new ArrayList<String>() {{

        add("france");
        add("allemagne");
        add("argentine");
        add("belgique");
        add("bresil");
        add("canada");
        add("chine");
        add("espagne");
        add("inde");
        add("irlande");
        add("italie");
        add("japon");
        add("portugal");
        add("russie");
        add("royaume_unis");
        add("etats_unis");

    }};
    private ArrayList<String> m_quizz;

    private ArrayList<String> res;

    public Quizz(){
        res = new ArrayList<>();
        m_quizz = new ArrayList<>();
        //Création d'un array de 10 drapeaux choisis au hasard parmis l'array drapeaux
        for(int i=0;i<10;i++){
            int value = getRandomNumberInRange(0, drapeaux.size()-1);
            while(m_quizz.contains(drapeaux.get(value))){
                value = getRandomNumberInRange(0, drapeaux.size()-1);
            }
            m_quizz.add(drapeaux.get(value));
            res.add("null");
        }
    }

    public String getDrapeau(int index){
        return m_quizz.get(index);
    }

    public int getNbErreurs(){
        int nb_erreurs =0;

        // comparaison ignorant les accents et les majuscules
        final Collator instance = Collator.getInstance();
        instance.setStrength(Collator.NO_DECOMPOSITION);


        for(int i=0;i<m_quizz.size();i++){
            if(instance.compare(m_quizz.get(i), res.get(i))>0){
                nb_erreurs++;
            }
        }
        return nb_erreurs;
    }

    public void addRes(String res){
        this.res.add(res);
    }

    public int getNbReps(){
        return m_quizz.size()-getNbErreurs();
    }

    public void setRes(int index, String res){
        // Les noms des pays sont stockés avec des _ à la place des -
        res = res.replace("-","_");

        this.res.set(index, res);
    }

    public String getResAt(int index){
        // l'affichage doit être avec - et non _
        return res.get(index).replace("_","-");
    }

    private static int getRandomNumberInRange(int min, int max){
        if(min>= max){
            throw new IllegalArgumentException("max doit etre plus grand que min");
        }

        Random r = new Random();
        return r.nextInt((max-min)+1)+min;
    }

    public boolean correct(int index){
        // Comparaison ignorant les accents et les majuscules
        final Collator instance = Collator.getInstance();
        instance.setStrength(Collator.NO_DECOMPOSITION);

        return instance.compare(res.get(index), m_quizz.get(index)) == 0;
    }
}
