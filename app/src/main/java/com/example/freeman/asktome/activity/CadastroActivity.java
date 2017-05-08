package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoSobrenome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private EditText campoSenha;
    private ToggleButton toggleButton;
    private Button button;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        database = FirebaseDatabase.getInstance().getReference("usuario");

        database.keepSynced(true);

        campoNome = (EditText) findViewById(R.id.campo_nome);
        campoSobrenome = (EditText) findViewById(R.id.campo_sobrenome);
        campoTelefone = (EditText) findViewById(R.id.campo_telefone);
        campoEmail = (EditText) findViewById(R.id.campo_email);
        campoSenha = (EditText) findViewById(R.id.campo_senha);
        toggleButton = (ToggleButton) findViewById(R.id.campo_palestrante);

        button = (Button) findViewById(R.id.btn_cadastrar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setNome(campoNome.getText().toString());
                usuario.setSobrenome(campoSobrenome.getText().toString());
                usuario.setTelefone(campoTelefone.getText().toString());
                usuario.setEmail(campoEmail.getText().toString());
                usuario.setSenha(campoSenha.getText().toString());
                boolean palestrante = toggleButton.getText().toString().equals("Sim");
                usuario.setPalestrante(palestrante);
                salvar(usuario);
            }
        });
    }

    private void salvar(final Usuario usuario) {
        String email = usuario.getEmail();
        database.orderByChild("email").equalTo(email.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean existe = false;
                if(dataSnapshot.exists()) {
                    existe = true;
                }
                if(!existe) {

                    String userId = database.push().getKey();
                    database.child(userId).setValue(usuario);

                    if(usuario.isPalestrante()) {
                        Intent intent = new Intent(CadastroActivity.this, MenuPalestranteActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(CadastroActivity.this, MenuPalestradoActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(CadastroActivity.this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
