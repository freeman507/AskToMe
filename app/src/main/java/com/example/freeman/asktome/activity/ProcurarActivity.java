package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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
    private static final int VOLTAR = 0;
    private static final int FILTRAR = 1;
    private static final int ENTRAR = 2;
    private Palestra palestra;
    private List<Palestra> palestras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurar);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        this.listView = (ListView) findViewById(R.id.procurar_palestras);

        if(this.palestra != null) {
            getPalestras(this.palestra.getCodigo(), this.palestra.getTitulo(), this.palestra.getNomePalestrante());
        }

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Palestra palestra = palestras.get(position);
                Intent intent = new Intent(ProcurarActivity.this, StreamPerguntaUsuarioActivity.class);
                intent.putExtra("palestra", palestra);
                intent.putExtra("usuario", usuario);
                startActivityForResult(intent, ENTRAR);
            }
        });

    }

    private void getPalestras(final String codigo, final String titulo, final String palestrante) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("palestra");
        database.orderByChild("codigo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Palestra> palestras = new ArrayList<Palestra>();
                for (DataSnapshot dataSnapshotPalestra : dataSnapshot.getChildren()) {
                    Palestra palestra = dataSnapshotPalestra.getValue(Palestra.class);
                    if(codigo.isEmpty() || !palestra.getCodigo().toLowerCase().equals(codigo.toLowerCase())) {
                        if(titulo.isEmpty() || !palestra.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                            if(palestrante.isEmpty() || !palestra.getNomePalestrante().toLowerCase().contains(palestrante.toLowerCase())) {
                                continue;
                            }
                        }
                    }
                    palestras.add(palestra);
                }
                atualizaLista(palestras);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void atualizaLista(List<Palestra> palestras) {
        this.palestras = palestras;
        this.listView.setAdapter(new ProcurarListAdater(this, this.palestras));
        if(this.palestras != null && !this.palestras.isEmpty()) {
            LinearLayout msg = (LinearLayout) findViewById(R.id.procurar_msg);
            msg.removeAllViews();
        } else {
            Toast.makeText(ProcurarActivity.this, "Nenhuma palestra encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_filter:
                intent = new Intent(this, FiltroActivity.class);
                intent.putExtra("usuario", this.usuario);
                startActivityForResult(intent, FILTRAR);
                return true;
            case R.id.action_voltar:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("usuario", this.usuario);
                startActivityForResult(intent, VOLTAR);
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
