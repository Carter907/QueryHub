package com.example.androidquizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.androidquizzer.databinding.ActivityMainBinding;
import com.example.androidquizzer.databinding.HomeScreenBinding;
import com.example.androidquizzer.databinding.TitleScreenBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding appBinding;
    private HomeScreenBinding homeScreenBinding;
    private TitleScreenBinding titleScreenBinding;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appBinding = ActivityMainBinding.inflate(getLayoutInflater());
        homeScreenBinding = HomeScreenBinding.inflate(getLayoutInflater());
        View view = appBinding.getRoot();

        setContentView(view);

        ActionBar bar = getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.screen_fragment, TitleScreen.class, null)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (!fragmentManager.getFragments().get(0).getClass().equals(TitleScreen.class))
                fragmentManager.popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();


    }
}