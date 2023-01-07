package com.example.androidquizzer;

import static androidx.core.os.LocaleListCompat.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;


import com.example.androidquizzer.databinding.AddQuizBinding;
import com.example.androidquizzer.databinding.HomeScreenBinding;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

public class HomeScreen extends Fragment {

    private HomeScreenBinding homeScreenBinding;
    private AddQuizBinding addQuizBinding;

    public HomeScreen() {
        super(R.layout.home_screen);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {

        homeScreenBinding = HomeScreenBinding.inflate(inflater, parent, false);
        return homeScreenBinding.getRoot();
    }
    @Override
    public void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);


    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView quizRecycler = homeScreenBinding.quizzesRecycler;
        QuizAdapter adapter = new QuizAdapter(getActivity().getSupportFragmentManager());
        quizRecycler.setAdapter(adapter);

        QuizDatabase db = QuizDatabase.Vendor.INSTANCE.getDb(getActivity().getApplicationContext());

        quizRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        db.getDao().getQuizzes().observe(this,
                quizzes -> {
                    adapter.getQuizList().clear();
                    adapter.getQuizList().addAll(quizzes);
                    adapter.notifyDataSetChanged();
                });

        homeScreenBinding.addQuizButton.setOnClickListener(view -> {

            addQuizBinding = AddQuizBinding.inflate(getLayoutInflater());

            EditText nameField = addQuizBinding.quizNameField;

            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Add new Query")
                    .setMessage("set the name of your new quiz (you can add new questions and answers by clicking the new label)")
                    .setView(addQuizBinding.getRoot())
                    .setPositiveButton("add quiz", (dialogInterface, v) -> {
                        Quiz quiz = new Quiz(nameField.getText().toString());
                        QuizDatabase.service.execute(() -> db.getDao().insert(quiz));
                    })
                    .create();

            dialog.show();


        });
    }
}
