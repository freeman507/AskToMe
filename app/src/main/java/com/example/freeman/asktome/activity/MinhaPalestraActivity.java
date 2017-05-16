package com.example.freeman.asktome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Usuario;
import com.example.freeman.asktome.view.PalestraListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MinhaPalestraActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        setContentView(R.layout.activity_minha_palestra);
        listView = (ListView) findViewById(R.id.palestras_list);

        getPalestras();
    }

    private void atualizaLista(List<Palestra> palestras) {
        this.listView.setAdapter(new PalestraListAdapter(this, palestras));
    }

    private void getPalestras() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("palestra");
        database.orderByChild("emailPalestrante").equalTo(this.usuario.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
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
}
