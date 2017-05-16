package com.example.freeman.asktome.activity;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private List<Usuario> usuarios;
    private Usuario usuario;
    private static final int FILTRAR = 1;
    private Palestra palestra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        listView = (ListView) findViewById(R.id.procurar_palestras);

        if(this.palestra != null) {
            getPalestras(this.palestra.getCodigo());
        } else {
            getPalestras();
        }

    }

    private void getPalestras(String codigo) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("palestra");
        database.orderByChild("codigo").equalTo(codigo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Palestra> palestras = new ArrayList<Palestra>();
                for (DataSnapshot dataSnapshotPalestra : dataSnapshot.getChildren()) {
                    palestras.add(dataSnapshotPalestra.getValue(Palestra.class));
                }
                atualizaLista(palestras);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPalestras() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("palestra");
        database.orderByChild("titulo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Palestra> palestras = new ArrayList<Palestra>();
                for (DataSnapshot dataSnapshotPalestra : dataSnapshot.getChildren()) {
                    palestras.add(dataSnapshotPalestra.getValue(Palestra.class));
                }
                atualizaLista(palestras);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void atualizaLista(List<Palestra> palestras) {
        this.listView.setAdapter(new ProcurarListAdater(this, palestras));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                startActivityForResult(new Intent(this, FiltroActivity.class), FILTRAR);
                return true;

            case R.id.action_remove_filter:
                getPalestras();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtro,menu);
        return true;
    }
}
