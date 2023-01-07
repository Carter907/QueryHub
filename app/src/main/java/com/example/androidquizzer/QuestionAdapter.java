package com.example.androidquizzer;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidquizzer.databinding.EditQuestionBinding;
import com.example.androidquizzer.databinding.QuestionElementBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder>{

    private List<Question> questions;
    private QuestionElementBinding questionElementBinding;
    private EditQuestionBinding editQuestionBinding;
    private QuizDao dao;
    private Quiz quiz;


    public QuestionAdapter(List<Question> questions, Quiz quiz) {
        this.questions = questions;
        this.dao = QuizDatabase.Vendor.INSTANCE.getDb(null).getDao();
        this.quiz = quiz;
    }


    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int itemType) {

        questionElementBinding = QuestionElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        QuestionHolder holder = new QuestionHolder(questionElementBinding.getRoot());

        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {

        TextView questionText = holder.itemView.findViewById(R.id.question_text);
        TextView answerText = holder.itemView.findViewById(R.id.answer_text);
        Question ques = questions.get(position);
        questionText.setText(ques.getValue());
        answerText.setText(ques.getAnswer().getValue());

        holder.itemView.setOnClickListener(e -> {
            // each Snackbar needs a View, CharSequence, and a BaseTransientBottomBar

            editQuestionBinding = EditQuestionBinding.inflate(
                    LayoutInflater.from(holder.itemView.getContext()));
            EditText questionEdit = editQuestionBinding.newQuestionEditText;
            EditText answerEdit = editQuestionBinding.newAnswerEditText;
            questionEdit.setText(questionText.getText());
            answerEdit.setText(answerText.getText());
            new AlertDialog.Builder(holder.itemView.getContext())

                    .setTitle("Edit question answer pair")
                    .setMessage("Update the question or answer")
                    .setView(editQuestionBinding.getRoot())
                    .setPositiveButton("update", (intern, view) -> {

                        ques.setValue(questionEdit.getText().toString());
                        ques.setAnswer(ques.new Answer(answerEdit.getText().toString()));

                        QuizDatabase.service.execute(() -> dao.updateQuestions(questions, quiz.getName()));


                    })
                    .create().show();

        });


    }

    public class QuestionHolder extends RecyclerView.ViewHolder {

        public QuestionHolder(@NonNull View view) {
            super(view);
        }

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
