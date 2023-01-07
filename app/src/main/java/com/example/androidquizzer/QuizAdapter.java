package com.example.androidquizzer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidquizzer.databinding.QuizElementBinding;
import com.google.gson.GsonBuilder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizHolder> {
    private QuizElementBinding quizElementBinding;
    private List<Quiz> quizList;
    private final FragmentManager fragmentManager;

    public QuizAdapter(FragmentManager fragmentManager) {
        quizList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        quizElementBinding = QuizElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        View view = quizElementBinding.getRoot();
        QuizHolder holder = new QuizHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(QuizHolder holder, int position) {
        TextView text = holder.itemView.findViewById(R.id.quiz_name);
        holder.quiz = quizList.get(position);
        text.setText(quizList.get(position).getName());

        QuizScreen quizScreen = new QuizScreen(holder.quiz);

        holder.itemView.setOnClickListener(v -> {

            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.screen_fragment, quizScreen, null)
                    .addToBackStack(null)
                    .commit();

        });
    }

    public class QuizHolder extends RecyclerView.ViewHolder {

        private Quiz quiz;

        public QuizHolder(View view) {
            super(view);


        }


    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }
}
