package com.example.androidquizzer.query_entities;

import com.example.androidquizzer.Question;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WrittenTestBuilder {


    private List<Question> questions;
    private TimeUnit unit;
    private long time;
    private RecursiveTask<Boolean> questionAnsweredTask;

    public WrittenTestBuilder() {

    }
    public WrittenTestBuilder setQuestionsAnswerPairs(List<Question> questions) {
        this.questions = questions;

        return this;
    }
    public WrittenTestBuilder setTimedTest(TimeUnit unit, long time) {
        this.unit = unit;
        this.time = time;

        return this;
    }


    public WrittenTest create() {


        return new WrittenTest(unit, time, questions);
    }
}
