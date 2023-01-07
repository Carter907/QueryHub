package com.example.androidquizzer;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Entity(tableName = "quiz_table")
public class Quiz {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "questionPairs")
    private List<Question> questionPairs;
    @ColumnInfo(name = "name")
    private String name;
    public Quiz(@NonNull String name, List<Question> questionPairs) {
        this.name = name;
        this.questionPairs = questionPairs;
    }
    @Ignore
    public Quiz(@NonNull String name) {
        this.name = name;
        this.questionPairs = new ArrayList<>();
    }

    public List<Question> getQuestionPairs() {
        return questionPairs;
    }

    public void setQuestionPairs(List<Question> questionPairs) {
        this.questionPairs = questionPairs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
