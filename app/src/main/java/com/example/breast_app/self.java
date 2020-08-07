package com.example.breast_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class self extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
    }

    public void goToMid(View view) {
        startActivity(new Intent(getApplicationContext(), self_mid.class));
    }

    public void back(View view) {
        onBackPressed();
    }
}
