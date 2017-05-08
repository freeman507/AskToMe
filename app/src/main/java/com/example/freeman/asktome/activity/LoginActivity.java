package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        database = FirebaseDatabase.getInstance().getReference("usuario");

        database.keepSynced(true);

        login = (EditText) findViewById(R.id.campo_user);
        password = (EditText) findViewById(R.id.campo_password);
        button = (Button) findViewById(R.id.btn_entrar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginText = login.getText().toString();
                final String passwordText = password.getText().toString();

                database.orderByChild("email").equalTo(loginText).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean existe = true;
                        if (dataSnapshot.exists()) {
                            Usuario usuario = dataSnapshot.getValue(Usuario.class);
                            if (passwordText.equals(usuario.getSenha())) {
                                existe = true;
                            }
                        }
                        if (existe) {
                            Intent intent = new Intent(LoginActivity.this, MenuPalestradoActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario ou senha inválidos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
