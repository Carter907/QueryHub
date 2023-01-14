package com.example.androidquizzer.query_entities;

import com.example.androidquizzer.Question;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class WrittenTest extends RecursiveTask<Double> {

    private TimeUnit unit;
    private long time;
    private List<Question> questions;
    private List<RecursiveTask<Boolean>> questionAnswered;
    private Iterator<Question> questionIterator;
    private double answeredCorrectly;

    public WrittenTest(TimeUnit unit, long time, List<Question> questions) {
        this.unit = unit;
        this.time = time;
        this.questions = questions;
        this.questionIterator = questions.iterator();
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public long getTime() {
        return time;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<RecursiveTask<Boolean>> getQuestionAnswered() {
        return questionAnswered;
    }
    public Question getNextQuestion() {

        return questionIterator.next();
    }
    public Double startTest(List<RecursiveTask<Boolean>> questionsAnswers) {
        this.questionAnswered = questionsAnswers;

        return invoke();
    }

    @Override
    public Double compute() {

        double total = questions.size();
        double correct = 0;
        correct = questionAnswered.stream().mapToInt(e -> e.invoke() ? 1 : 0).sum();

        this.answeredCorrectly = correct / total;
        return this.answeredCorrectly;
    }

    public Double getAnsweredCorrectly() {

        return this.answeredCorrectly;
    }
}
