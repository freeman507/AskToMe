package com.example.freeman.asktome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.freeman.asktome.activity.MenuActivity;
import com.example.freeman.asktome.activity.MinhaPalestraActivity;
import com.example.freeman.asktome.activity.ProcurarActivity;
import com.example.freeman.asktome.model.Palestra;

public class FiltroActivity extends AppCompatActivity {

    private EditText campoCodigoPalestra;
    private EditText campoTitutloPalestra;
    private EditText campoNomePalestrante;
    private EditText campoDataPalestra;
    private EditText campoHoraPalestra;
    private Button btnFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        campoCodigoPalestra = (EditText) findViewById(R.id.filtro_codigo);
        campoTitutloPalestra = (EditText) findViewById(R.id.filtro_titulo);
        campoNomePalestrante = (EditText) findViewById(R.id.filtro_palestrante);
        campoDataPalestra = (EditText) findViewById(R.id.filtro_data);
        campoHoraPalestra = (EditText) findViewById(R.id.filtro_hora);
        btnFiltrar = (Button) findViewById(R.id.filtro_btn_filtrar);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Palestra palestra = new Palestra();
                palestra.setCodigo(campoCodigoPalestra.getText().toString());
                palestra.setTitulo(campoTitutloPalestra.getText().toString());
                palestra.setNomePalestrante(campoNomePalestrante.getText().toString());
                palestra.setData(campoDataPalestra.getText().toString());
                palestra.setHora(campoHoraPalestra.getText().toString());

                Intent intent = new Intent(FiltroActivity.this, ProcurarActivity.class);
                intent.putExtra("palestra", palestra);
                startActivity(intent);
            }
        });
    }
}
