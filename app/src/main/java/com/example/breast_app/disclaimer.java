package com.example.breast_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.breast_app.ui.disclaimer.DisclaimerFragment;

public class disclaimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DisclaimerFragment.newInstance())
                    .commitNow();
        }
    }
}
