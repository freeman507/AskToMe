package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Pergunta;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class PerguntarActivity extends AppCompatActivity {

    private DatabaseReference database;

    private Usuario usuario;
    private Palestra palestra;

    private EditText perguntaEditTxt;
    private Switch switchAnonimo;
    private Button perguntarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntar);

        database = FirebaseDatabase.getInstance().getReference("pergunta");

        database.keepSynced(true);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        this.perguntaEditTxt = (EditText) findViewById(R.id.perguntar_pergunta);
        this.switchAnonimo = (Switch) findViewById(R.id.perguntar_anonimo);
        this.perguntarBtn = (Button) findViewById(R.id.perguntar_button);

        this.perguntarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = perguntaEditTxt.getText().toString();
                boolean checked = switchAnonimo.isChecked();

                Pergunta pergunta = new Pergunta();
                pergunta.setCodigoPalestra(palestra.getCodigo());
                pergunta.setEmailUsuario(usuario.getEmail());
                pergunta.setNomeUsuario(checked ? "Anônimo" : usuario.getNome() + " " + usuario.getSobrenome());
                pergunta.setPergunta(string);
                pergunta.setRespondida(false);
                pergunta.setTimestamp(new Date().getTime());
                salvar(pergunta);
            }
        });
    }

    private void salvar(final Pergunta pergunta) {
        database.orderByChild("codigoPalestra").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userId = database.push().getKey();
                database.child(userId).setValue(pergunta);
                Intent intent = new Intent(PerguntarActivity.this, StreamPerguntaUsuarioActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("palestra", palestra);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
