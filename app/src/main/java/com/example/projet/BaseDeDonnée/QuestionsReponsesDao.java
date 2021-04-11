package com.example.projet.BaseDeDonnée;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionsReponsesDao {
    @Query("SELECT * FROM QuestionsReponses")
    List<QuestionsReponses> getAll();

    @Insert
    void insert(QuestionsReponses questRep);

    @Insert
    long[] insertAll(QuestionsReponses... questRep);

    @Delete
    void delete(QuestionsReponses questRep);

    @Update
    void update(QuestionsReponses questRep);
}

