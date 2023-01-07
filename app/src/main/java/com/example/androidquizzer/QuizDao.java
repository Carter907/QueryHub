package com.example.androidquizzer;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuizDao {

    @Insert
    void insert(Quiz quiz);

    @Delete
    void delete(Quiz quiz);

    @Query("SELECT * FROM quiz_table ORDER BY id ASC")
    LiveData<List<Quiz>> getQuizzes();

    @Query("SELECT * FROM quiz_table WHERE name=:name")
    LiveData<List<Quiz>> getQuizzesByName(String name);

    @Query("UPDATE quiz_table SET questionPairs=:value WHERE name=:name")
    void updateQuestions(List<Question> value, String name);

    @Query("SELECT (questionPairs) FROM quiz_table WHERE name=:name")
    LiveData<String> getQuestionsFromQuiz(String name);

    @Query("DELETE FROM quiz_table")
    void deleteAll();


}
