package com.example.projet.BaseDeDonnée;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "score1M")
    private Integer score1M;

    @ColumnInfo(name = "score2M")
    private Integer score2M;

    @ColumnInfo(name = "score1G")
    private Integer score1G;

    @ColumnInfo(name = "score2G")
    private Integer score2G;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() { return prenom; }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getScore1G() { return score1G; }

    public void setScore1G(Integer score1G) {
        if (this.score1G == null) {
            this.score1G = score1G;
        } else if (score1G > this.score1G) {
            this.score1G = score1G;
        }
    }

    public Integer getScore1M() { return score1M; }

    public void setScore1M(Integer score1M) {
        if (this.score1M == null) {
            this.score1M = score1M;
        } else if(score1M > this.score1M){
            this.score1M = score1M;
        }
    }

    public Integer getScore2G() { return score2G; }

    public void setScore2G(Integer score2G) {
        if (this.score2G == null) {
            this.score2G = score2G;
        } else if (score2G > this.score2G) {
            this.score2G = score2G;
        }

    }

    public Integer getScore2M() { return score2M; }

    public void setScore2M(Integer score2M) {
        if (this.score2M == null) {
            this.score2M = score2M;
        } else if (score2M > this.score2M) {
            this.score2M = score2M;
        }
    }
}
