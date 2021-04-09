package com.example.projet.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class QuestionsReponses implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "reponse1")
    private  String reponse1;

    @ColumnInfo(name = "reponse2")
    private  String reponse2;

    @ColumnInfo(name = "reponse3")
    private  String reponse3;

    @ColumnInfo(name = "reponseCorrect")
    private String reponseCorrect;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponseCorrect() {
        return reponseCorrect;
    }

    public void setReponseCorrect(String reponseCorrect) {
        this.reponseCorrect = reponseCorrect;
    }

    public String getReponse1() { return reponse1; }

    public void setReponse1(String reponse1) { this.reponse1 = reponse1; }

    public String getReponse2() { return reponse2; }

    public void setReponse2(String reponse2) { this.reponse2 = reponse2; }

    public String getReponse3() { return reponse3; }

    public void setReponse3(String reponse3) { this.reponse3 = reponse3; }
}

