package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Date;

public class NovaPalestraActivity extends AppCompatActivity {

    private EditText campoCodigo;
    private EditText campoTitulo;
    private EditText campoData;
    private EditText campoHora;
    private EditText campoDuracao;
    private EditText campoEndereço;
    private EditText campoDescricao;
    private Button cadastrarButton;

    private DatabaseReference database;

    private Usuario usuario;
    private Palestra palestra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_palestra);

        database = FirebaseDatabase.getInstance().getReference("palestra");

        database.keepSynced(true);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        campoCodigo = (EditText) findViewById(R.id.codigo_palestra);
        campoTitulo = (EditText) findViewById(R.id.titulo_palestra);
        campoData = (EditText) findViewById(R.id.data_palestra);
        campoHora = (EditText) findViewById(R.id.hora_palestra);
        campoDuracao = (EditText) findViewById(R.id.duracao_palestra);
        campoEndereço = (EditText) findViewById(R.id.local_palestra);
        campoDescricao = (EditText) findViewById(R.id.descricao_palestra);
        cadastrarButton = (Button) findViewById(R.id.cadastrar_palestra_btn);

        if(palestra != null) {
            campoCodigo.setText(palestra.getCodigo());
            campoTitulo.setText(palestra.getTitulo());
            campoDescricao.setText(palestra.getDescricao());
            campoEndereço.setText(palestra.getEndereco());
            campoData.setText(palestra.getData());
            campoDuracao.setText(palestra.getDuracao());
        }

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = campoCodigo.getText().toString();
                String titulo = campoTitulo.getText().toString();
                String descricao = campoDescricao.getText().toString();
                String endereco = campoEndereço.getText().toString();
                String data = campoData.getText().toString();
                String duracao = campoDuracao.getText().toString();
                String hora = campoHora.getText().toString();
                boolean cadastrar = true;

                if(codigo.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo codigo está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(titulo.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo titulo da palestra está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(endereco.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo endereço está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(data.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo data está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(duracao.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo duração da palestra está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(hora.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo hora da palestra está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }

                if(cadastrar) {
                    Palestra palestra = new Palestra();
                    palestra.setNomePalestrante(usuario.getNome() + " " + usuario.getSobrenome());
                    palestra.setEmailPalestrante(usuario.getEmail());
                    palestra.setCodigo(codigo);
                    palestra.setTitulo(titulo);
                    palestra.setDescricao(descricao);
                    palestra.setEndereco(endereco);
                    palestra.setData(data);
                    palestra.setDuracao(duracao);
                    palestra.setHora(hora);
                    salvar(palestra);
                }
            }
        });
    }

    private void salvar(final Palestra palestra) {
        String codigo = palestra.getCodigo();
        database.orderByChild("codigo").equalTo(codigo.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean existe = false;
                if(dataSnapshot.exists()) {
                    existe = true;
                }
                if(!existe) {
                    String userId = database.push().getKey();
                    database.child(userId).setValue(palestra);
                    Intent intent = new Intent(NovaPalestraActivity.this, MenuActivity.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } else {
                    Toast.makeText(NovaPalestraActivity.this, "Código já cadastrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getStringTime(Date time) {
        int hours = time.getHours();
        int minutes = time.getMinutes();
        String hora = "";

        if(hours < 10) {
            hora += "0"+hours;
        } else {
            hora += hours;
        }
        hora += ":";
        if( minutes < 10) {
            hora += "0"+minutes+"/";
        } else {
            hora += minutes;
        }
        return hora;
    }

    private String getStringDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        String data = "";

        if(day < 10) {
            data += "0"+day;
        } else {
            data += day;
        }
        data += "/";
        if( month < 10) {
            data += "0"+month+"/";
        } else {
            data += month;
        }
        data += "/";
        return data + year;
    }
}
