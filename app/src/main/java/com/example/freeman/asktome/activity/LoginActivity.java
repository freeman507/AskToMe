package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.freeman.asktome.MainActivity;
import com.example.freeman.asktome.R;

public class LoginActivity extends AppCompatActivity {


    EditText login;
    EditText password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.campo_user);
        password = (EditText) findViewById(R.id.campo_password);
        button = (Button) findViewById(R.id.btn_entrar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginText = login.getText().toString();
                String passwordText = password.getText().toString();

                if(loginText.equals("aluno") && passwordText.equals("123")) {
                    Intent intent = new Intent(LoginActivity.this, MenuPalestrado.class);
                    startActivity(intent);
                }
            }
        });
    }
}
