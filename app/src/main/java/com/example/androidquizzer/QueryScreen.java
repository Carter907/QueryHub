package com.example.androidquizzer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidquizzer.databinding.QueryScreenBinding;
import com.example.androidquizzer.query_entities.WrittenTest;
import com.example.androidquizzer.query_entities.WrittenTestBuilder;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class QueryScreen extends Fragment {

    private QueryScreenBinding queryScreenBinding;
    private Quiz quiz;
    private ExecutorService service;
    private Lock waitForQuestionLock;
    private Lock waitForTestLock;
    private Condition waitForQuestionConn;
    private Condition waitForTestCondition;


    public QueryScreen(Quiz quiz) {
        super(R.layout.query_screen);
        service = Executors.newFixedThreadPool(2);
        waitForQuestionLock = new ReentrantLock();
        waitForQuestionConn = waitForQuestionLock.newCondition();
        waitForTestLock = new ReentrantLock();
        waitForTestCondition = waitForTestLock.newCondition();

        this.quiz = quiz;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle) {

        queryScreenBinding = QueryScreenBinding.inflate(inflater, view, false);
        return queryScreenBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        queryScreenBinding.submit.setOnClickListener(this::nextQuestion);

        WrittenTest test = new WrittenTestBuilder()
                .setQuestionsAnswerPairs(quiz.getQuestionPairs())
                .setTimedTest(TimeUnit.MINUTES, 2)
                .create();

        service.execute(() -> {
            List<RecursiveTask<Boolean>> questionAnswered = Stream.generate(() -> new RecursiveTask<Boolean>() {
                @Override
                protected Boolean compute() {

                    Question ques = test.getNextQuestion();
                    System.out.println(ques);
                    getActivity().runOnUiThread(() -> {
                        queryScreenBinding.questionsText.setText(ques.getValue());
                    });

                    boolean isCorrect;

                    waitForQuestionLock.lock();
                    try {
                        waitForQuestionConn.await();
                        isCorrect = queryScreenBinding.inputAnswer.getText().toString()
                                .equalsIgnoreCase(ques.getAnswer().getValue());
                        queryScreenBinding.inputAnswer.setText("");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        waitForQuestionLock.unlock();
                    }
                    return isCorrect;
                }
            }).limit(quiz.getQuestionPairs().size()).collect(Collectors.toList());

            test.startTest(questionAnswered);

            waitForTestLock.lock();
            getActivity().runOnUiThread(() -> {

                new AlertDialog.Builder(getContext())
                        .setTitle("Result")
                        .setMessage(String.format(
                                Locale.US,
                                "your score is %.2f%%",
                                test.getAnsweredCorrectly()*100))
                        .create().show();

            });
        });


    }

    public void nextQuestion(@NonNull View view) {
        waitForQuestionLock.lock();
        try {
            waitForQuestionConn.signal();
        } finally {
            waitForQuestionLock.unlock();
        }

    }
}
