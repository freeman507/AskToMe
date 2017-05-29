package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Pergunta;
import com.example.freeman.asktome.model.Usuario;
import com.example.freeman.asktome.view.PerguntaListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StreamPerguntaUsuarioActivity extends AppCompatActivity {

    private Button perguntarBtn;

    private Usuario usuario;
    private Palestra palestra;
    private List<Pergunta> perguntas;
    private ListView listView;
    private static int INFO = 1;
    private static int SAIR = 2;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_pergunta_usuario);

        database = FirebaseDatabase.getInstance().getReference("pergunta");
        database.keepSynced(true);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        this.perguntarBtn = (Button) findViewById(R.id.perguntar_btn);
        this.listView = (ListView) findViewById(R.id.lista_perguntas);

        this.perguntarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreamPerguntaUsuarioActivity.this, PerguntarActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("palestra", palestra);
                startActivity(intent);
            }
        });

        getPerguntas();
    }

    private void atualizaLista(List<Pergunta> perguntas) {
        this.listView.setAdapter(new PerguntaListAdapter(this, perguntas));
    }

    private void getPerguntas() {

        database.orderByChild("codigoPalestra").equalTo(this.palestra.getCodigo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                perguntas = new ArrayList<Pergunta>();
                for (DataSnapshot dataSnapshotPalestra : dataSnapshot.getChildren()) {
                    perguntas.add(dataSnapshotPalestra.getValue(Pergunta.class));
                }
                atualizaLista(perguntas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_atualizar_palestra:
                getPerguntas();
                return true;
            case R.id.action_menu_palestra:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("usuario", this.usuario);
                intent.putExtra("palestra", this.palestra);
                startActivityForResult(intent, SAIR);
                return true;
            case R.id.action_info_palestra:
                intent = new Intent(this, InfoPalestraActivity.class);
                intent.putExtra("usuario", this.usuario);
                intent.putExtra("palestra", this.palestra);
                startActivityForResult(intent, INFO);
                return true;
            case R.id.action_sair:
                intent = new Intent(this, ProcurarActivity.class);
                intent.putExtra("usuario", this.usuario);
                intent.putExtra("palestra", this.palestra);
                startActivityForResult(intent, SAIR);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usuario_palestra,menu);
        return true;
    }
}
