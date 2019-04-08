package com.example.rajeevjha.stackoverflow.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.rajeevjha.stackoverflow.models.Question;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface QuestionDao {

    // returns all list of all Questions
    @Query("Select * FROM question")
    LiveData<List<Question>> getAll();

    @Query("Delete From question")
    void deleteAll();

    // returns a Question item
    @Query("SELECT * FROM question WHERE _ID = :questionId")
    Question getQuestionById(int questionId);

    // returns Question of related tag and sort_category
    @Query("SELECT * FROM question WHERE tag = :tag AND sort_category = :sort ORDER BY _ID DESC")
    LiveData<List<Question>> getQuestionByTagAndSort(String tag, String sort);


    // inserts a row
    @Insert(onConflict = REPLACE)
    void insert(Question qu);


    // deletes a row
    @Delete
    void delete(Question qu);


}
