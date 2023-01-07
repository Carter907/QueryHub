package com.example.androidquizzer;

import androidx.lifecycle.LiveData;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionListConverter {

    //    private LinkedHashSet<Question> questionPairs;
    @TypeConverter
    public String questionSetToString(List<Question> questions) {

        return new GsonBuilder().setPrettyPrinting().create().toJson(questions);
    }
    @TypeConverter
    public List<Question> stringToQuestionSet(String questions) {

        Type listType = new TypeToken<ArrayList<Question>>(){}.getType();

        return new Gson().fromJson(questions, listType);
    }





}
