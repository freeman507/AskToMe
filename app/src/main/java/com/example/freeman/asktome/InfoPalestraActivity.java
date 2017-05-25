package com.example.freeman.asktome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Usuario;

public class InfoPalestraActivity extends AppCompatActivity {

    private Usuario usuario;
    private Palestra palestra;

    private TextView tituloPalestra;
    private TextView descPalestra;
    private TextView palestrantePalestra;
    private TextView localPalestra;
    private TextView dataHoraPalestra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_palestra);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        this.tituloPalestra = (TextView) findViewById(R.id.info_titulo_palestra);
        this.descPalestra = (TextView) findViewById(R.id.info_desc_palestra);
        this.palestrantePalestra = (TextView) findViewById(R.id.info_palestrante_palestra);
        this.localPalestra = (TextView) findViewById(R.id.info_local_palestra);
        this.dataHoraPalestra = (TextView) findViewById(R.id.info_data_hora_palestra);

        this.tituloPalestra.setText(this.palestra.getTitulo());
        String descricao = this.palestra.getDescricao();
        this.descPalestra.setText(descricao == null || descricao.isEmpty() ? "" : this.palestra.getDescricao());
        this.palestrantePalestra.setText(this.palestra.getNomePalestrante());
        this.localPalestra.setText(this.palestra.getEndereco());
        this.dataHoraPalestra.setText(this.palestra.getData() + " " + this.palestra.getHora());
    }
}
