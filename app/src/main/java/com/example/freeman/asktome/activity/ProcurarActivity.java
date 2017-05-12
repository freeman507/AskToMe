package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.freeman.asktome.FiltroActivity;
import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Usuario;
import com.example.freeman.asktome.view.ProcurarListAdater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProcurarActivity extends AppCompatActivity {

    private ListView listView;
    private List<Palestra> palestras;
    private List<Usuario> usuarios;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        listView = (ListView) findViewById(R.id.procurar_palestras);
        Button button = (Button) findViewById(R.id.btn_teste);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProcurarActivity.this, FiltroActivity.class);
                startActivity(intent);
            }
        });

        this.palestras = getPalestras();
        atualizaLista();
    }

    private List<Palestra> getPalestras() {
        final List<Palestra> palestras = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("palestra");;
        database.orderByChild("titulo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                palestras.add(dataSnapshot.getValue(Palestra.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return palestras;
    }

    private void atualizaLista() {
        this.listView.setAdapter(new ProcurarListAdater(this, this.palestras, this.usuarios));
    }

    private List<Usuario> getPalestrantes(List<Usuario> usuarios) {
        List<Usuario> palestrantes = new ArrayList<>();
        for (Usuario usuario: usuarios) {
            if(usuario.isPalestrante()) {
                palestrantes.add(usuario);
            }
        }
        return palestrantes;
    }
}
