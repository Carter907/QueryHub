package com.example.androidquizzer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidquizzer.databinding.AddQuestionBinding;
import com.example.androidquizzer.databinding.QuizScreenBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveTask;

public class QuizScreen extends Fragment {

    private QuizScreenBinding quizScreenBinding;
    private AddQuestionBinding addQuestionBinding;
    private Quiz quiz;

    public QuizScreen(Quiz quiz) {
        super(R.layout.quiz_screen);

        this.quiz = quiz;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        quizScreenBinding = QuizScreenBinding.inflate(inflater, container, false);
        return quizScreenBinding.getRoot();
    }

    @Override
    public void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);



    }

    @Override
    public void onStart() {
        super.onStart();


        RecyclerView questionsView = quizScreenBinding.questionRecycler;

        QuestionAdapter adapter = new QuestionAdapter(quiz.getQuestionPairs(), quiz);

        questionsView.setLayoutManager(new LinearLayoutManager(getContext()));
        questionsView.setAdapter(adapter);




        FloatingActionButton button = quizScreenBinding.addQuestionAndAnswer;
        QuizDatabase db = QuizDatabase.Vendor.INSTANCE.getDb(getActivity().getApplicationContext());

        QuizDao dao = db.getDao();


        QuizDatabase.service.execute(() -> {


            getActivity().runOnUiThread(() -> dao.getQuestionsFromQuiz(quiz.getName()).observe(this, stringQuests -> {
                List<Question> questions = new QuestionListConverter().stringToQuestionSet(stringQuests);

                adapter.getQuestions().clear();
                quiz.setQuestionPairs(questions);
                adapter.setQuestions(questions);
                adapter.notifyDataSetChanged();

            }));

        });





        button.setOnClickListener(e -> {

            addQuestionBinding = AddQuestionBinding.inflate(getLayoutInflater());

            EditText questionField = addQuestionBinding.questionEditText;
            EditText answerField = addQuestionBinding.answerEditText;

            new AlertDialog.Builder(getContext())
                    .setTitle("Add a Question and Answer")
                    .setView(addQuestionBinding.getRoot())
                    .setMessage("please enter a question and an answer")
                    .setPositiveButton("add to quiz", (interfa, view) -> {


                        QuizDatabase.service.execute(() -> {

                            Question question = new Question(questionField.getText().toString());
                            Question.Answer answer = question.new Answer(answerField.getText().toString());
                            List<Question> newQuestions = quiz.getQuestionPairs();
                            newQuestions.add(question);
                            dao.updateQuestions(newQuestions, quiz.getName());

                        });



                    })
                    .create().show();
        });


    }


}
