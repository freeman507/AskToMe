package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private List<Palestra> palestras;
    private static int ENTRAR = 1;
    private static int SAIR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        setContentView(R.layout.activity_minha_palestra);
        listView = (ListView) findViewById(R.id.palestras_list);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Palestra palestra = palestras.get(position);
                Intent intent = new Intent(MinhaPalestraActivity.this, StreamPerguntaPalestranteActivity.class);
                intent.putExtra("palestra", palestra);
                intent.putExtra("usuario", usuario);
                startActivityForResult(intent, ENTRAR);
            }
        });

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
                palestras = new ArrayList<Palestra>();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_minha_palestra_sair:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("usuario", this.usuario);
                startActivityForResult(intent, SAIR);
                return true;
            case R.id.action_minha_palestra_nova:
                intent = new Intent(this, NovaPalestraActivity.class);
                intent.putExtra("usuario", this.usuario);
                startActivityForResult(intent, SAIR);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.minha_palestra,menu);
        return true;
    }
}
