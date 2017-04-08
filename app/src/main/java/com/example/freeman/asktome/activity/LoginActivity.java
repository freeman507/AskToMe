package com.example.freeman.asktome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.freeman.asktome.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.recovery_link);
    }
}
