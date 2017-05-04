package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.freeman.asktome.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference database;

    EditText login;
    EditText password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        database = FirebaseDatabase.getInstance().getReference("usuarios");

        database.keepSynced(true);

        database.orderByChild("login").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        Usuario usuario = new Usuario();
        usuario.setUsuario("freeman");
        usuario.setSenha("123456");

        String userId = database.push().getKey();

        database.child(userId).setValue(usuario);
        */

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

    public void cadastrar(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    public void recuperarSenha(View view) {
        System.out.println("teste");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.goOffline();
    }
}
