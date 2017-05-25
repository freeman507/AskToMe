package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Usuario;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout layoutPalestrante;
    private ImageButton perfilButton;
    private ImageButton procurarButton;
    private ImageButton novaPalestraButton;
    private ImageButton minhasPalestrasButton;
    private Usuario usuario;
    private static final int LOG_OUT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        layoutPalestrante = (LinearLayout) findViewById(R.id.layout_palestrante);
        perfilButton = (ImageButton) findViewById(R.id.perfil_btn);
        procurarButton = (ImageButton) findViewById(R.id.procurar_btn);
        novaPalestraButton = (ImageButton) findViewById(R.id.nova_palestra_btn);
        minhasPalestrasButton = (ImageButton) findViewById(R.id.minhas_palestras_btn);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        if(this.usuario != null && !this.usuario.isPalestrante()) {
            layoutPalestrante.setVisibility(View.INVISIBLE);
        }

        perfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CadastroActivity.class);
                intent.putExtra("usuario", MenuActivity.this.usuario);
                startActivity(intent);
            }
        });

        novaPalestraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NovaPalestraActivity.class);
                intent.putExtra("usuario", MenuActivity.this.usuario);
                startActivity(intent);
            }
        });

        minhasPalestrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MinhaPalestraActivity.class);
                intent.putExtra("usuario", MenuActivity.this.usuario);
                startActivity(intent);
            }
        });

        procurarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProcurarActivity.class);
                intent.putExtra("usuario", MenuActivity.this.usuario);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_voltar ) {
            finish();
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, LOG_OUT);
                return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
