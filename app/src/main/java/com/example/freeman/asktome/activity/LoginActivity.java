package com.example.freeman.asktome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {

    private DatabaseReference database;
    private EditText loginEditTxt;
    private EditText passwordEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int flags = getIntent().getFlags();

        if(flags != 0) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        database = FirebaseDatabase.getInstance().getReference("usuario");
        database.keepSynced(true);

        TextView t = (TextView) findViewById(R.id.login_txt_cadastro);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        loginEditTxt = (EditText) findViewById(R.id.login_campo_login);
        passwordEditTxt = (EditText) findViewById(R.id.login_campo_senha);
        Button button = (Button) findViewById(R.id.login_btn_entrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginEditTxt.getText().toString();
                final String senha = passwordEditTxt.getText().toString();

                database.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Usuario usuario = null;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnapshotUsuario : dataSnapshot.getChildren()) {
                                usuario = dataSnapshotUsuario.getValue(Usuario.class);
                                break;
                            }
                        }

                        if (usuario != null && usuario.getSenha().equals(senha)) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("usuario", usuario);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseDatabase.getInstance().goOffline();
    }
}
