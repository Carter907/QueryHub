package com.example.androidquizzer;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class AnswerClassConverter {

    @TypeConverter
    public String answerToString(Question.Answer answer) {
        return answer.toString();
    }
    @TypeConverter
    public Question.Answer stringToAnswer(String answer) {


        return new Gson().fromJson(answer, Question.Answer.class);
    }
}
