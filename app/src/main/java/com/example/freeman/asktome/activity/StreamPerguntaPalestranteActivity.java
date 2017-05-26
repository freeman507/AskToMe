package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Pergunta;
import com.example.freeman.asktome.model.Usuario;
import com.example.freeman.asktome.view.PerguntaListAdapter;
import com.example.freeman.asktome.view.PerguntaPalestranteListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StreamPerguntaPalestranteActivity extends AppCompatActivity {

    private ListView listView;

    private Palestra palestra;
    private Usuario usuario;
    private List<Pergunta> perguntas;
    private static int EDITAR = 1;
    private static int SAIR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_pergunta_palestrante);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        this.listView = (ListView) findViewById(R.id.lista_pergunta_palestrante);

        getPerguntas();
    }

    private void atualizaLista(List<Pergunta> perguntas) {
        this.listView.setAdapter(new PerguntaPalestranteListAdapter(this, perguntas));
    }

    private void getPerguntas() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("pergunta");
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
            case R.id.action_palestra_editar:
                intent = new Intent(this, NovaPalestraActivity.class);
                intent.putExtra("usuario", this.usuario);
                intent.putExtra("palestra", this.palestra);
                startActivityForResult(intent, EDITAR);
                return true;
            case R.id.action_palestrante_sair:
                intent = new Intent(this, MinhaPalestraActivity.class);
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
        getMenuInflater().inflate(R.menu.palestrante_palestra,menu);
        return true;
    }
}
