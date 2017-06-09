package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Usuario;

public class FiltroActivity extends AppCompatActivity {

    private EditText campoCodigoPalestra;
    private EditText campoTitutloPalestra;
    private EditText campoNomePalestrante;
    private Button btnFiltrar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        campoCodigoPalestra = (EditText) findViewById(R.id.filtro_codigo);
        campoTitutloPalestra = (EditText) findViewById(R.id.filtro_titulo);
        campoNomePalestrante = (EditText) findViewById(R.id.filtro_palestrante);
        btnFiltrar = (Button) findViewById(R.id.filtro_btn_filtrar);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Palestra palestra = new Palestra();
                palestra.setCodigo(campoCodigoPalestra.getText().toString());
                palestra.setTitulo(campoTitutloPalestra.getText().toString());
                palestra.setNomePalestrante(campoNomePalestrante.getText().toString());

                Intent intent = new Intent(FiltroActivity.this, ProcurarActivity.class);
                intent.putExtra("palestra", palestra);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_voltar:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("usuario", this.usuario);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.procurar,menu);
        return true;
    }
}
