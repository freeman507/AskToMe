package com.example.freeman.asktome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.view.PalestraListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MinhaPalestraActivity extends AppCompatActivity {

    private ListView listView;
    private List<Palestra> palestras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_palestra);
        listView = (ListView) findViewById(R.id.palestras_list);
        this.palestras = new ArrayList<>();
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        atualizaLista();
    }

    private void atualizaLista() {
        this.listView.setAdapter(new PalestraListAdapter(this, this.palestras));
    }

    private Palestra createPalestra() {
        Palestra palestra = new Palestra();
        palestra.setTitulo("teste");
        palestra.setHora("10:00");
        palestra.setData("10/10/2010");
        palestra.setCodigo("teste");
        return palestra;
    }
}
