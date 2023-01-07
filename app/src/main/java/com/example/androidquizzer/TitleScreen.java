package com.example.androidquizzer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.androidquizzer.databinding.TitleScreenBinding;

public class TitleScreen extends Fragment {

    private TitleScreenBinding titleScreenBinding;

    public TitleScreen() {
        super(R.layout.title_screen);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        titleScreenBinding = TitleScreenBinding.inflate(inflater, container, false);

        return titleScreenBinding.getRoot();
    }

    @Override
    public void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);

    }

    @Override
    public void onStart() {
        super.onStart();
        titleScreenBinding.titleContinueButton.setOnClickListener(e -> {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.screen_fragment, HomeScreen.class, null)
                    .addToBackStack(null)
                    .commit();
        });

    }

}
