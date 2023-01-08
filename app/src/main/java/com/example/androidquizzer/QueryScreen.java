package com.example.androidquizzer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidquizzer.databinding.QueryScreenBinding;
import com.google.gson.GsonBuilder;


public class QueryScreen extends Fragment {

    private QueryScreenBinding queryScreenBinding;
    private Quiz quiz;

    public QueryScreen(Quiz quiz) {
        super(R.layout.query_screen);

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

        queryScreenBinding.questionsText.setText(new GsonBuilder().setPrettyPrinting().create().toJson(quiz.getQuestionPairs()));
    }
}
