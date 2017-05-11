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

        this.usuarios = new ArrayList<>();
        this.usuarios.add(createUsuario());
        this.usuarios.add(createUsuario());
        this.usuarios.add(createUsuario());
        this.usuarios.add(createUsuario());
        this.usuarios.add(createUsuario());

        this.palestras = new ArrayList<>();
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        this.palestras.add(createPalestra());
        atualizaLista();
    }

    private void atualizaLista() {
        this.listView.setAdapter(new ProcurarListAdater(this, this.palestras, this.usuarios));
    }

    private Palestra createPalestra() {
        Palestra palestra = new Palestra();
        palestra.setIdPalestrante((long) this.palestras.size()+1);
        palestra.setTitulo("teste");
        palestra.setHora("10:00");
        palestra.setData("10/10/2010");
        palestra.setCodigo("teste");
        return palestra;
    }

    private Usuario createUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome");
        usuario.setEmail("Email");
        usuario.setPalestrante(true);
        usuario.setSobrenome("sobrenome");
        usuario.setSenha("123");
        usuario.setTelefone("Telefone");
        usuario.set_id((long) (this.usuarios.size() + 1));
        return usuario;
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
