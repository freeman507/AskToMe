package com.example.freeman.asktome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoSenhaConfirmar;
    private Switch campoPalestrante;
    private Button button;
    private Usuario usuario;
    private String acao;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        database = FirebaseDatabase.getInstance().getReference("usuario");

        database.keepSynced(true);

        campoNome = (EditText) findViewById(R.id.campo_nome);
        campoEmail = (EditText) findViewById(R.id.campo_email);
        campoSenha = (EditText) findViewById(R.id.campo_senha);
        campoSenhaConfirmar = (EditText) findViewById(R.id.campo_repetir_senha);
        campoPalestrante = (Switch) findViewById(R.id.campo_palestrante);
        button = (Button) findViewById(R.id.cadastrar_btn);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if(usuario != null) {
            this.acao = "editar";
            button.setText("Alterar");
            campoNome.setText(usuario.getNome());
            campoEmail.setText(usuario.getEmail());
            campoEmail.setEnabled(false);
            campoEmail.setTextColor(getResources().getColor(R.color.colorSecundary));
            campoSenha.setText(usuario.getSenha());
            if(usuario.isPalestrante()) {
                campoPalestrante.setChecked(true);
            } else {
                campoPalestrante.setChecked(false);
            }
        } else {
            acao = "cadastrar";
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = campoNome.getText().toString();
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                String repetirSenha = campoSenhaConfirmar.getText().toString();
                boolean cadastrar = true;
                if(nome.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo nome está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = p.matcher(email);
                if (!m.find()) {
                    Toast.makeText(CadastroActivity.this, "Informe um e-mail valido", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(senha.isEmpty() || senha.length() < 6) {
                    Toast.makeText(CadastroActivity.this, "A senha deve ter pelo menos 6 digitos", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(repetirSenha.isEmpty() || repetirSenha.length() < 6 || !repetirSenha.equals(senha)) {
                    Toast.makeText(CadastroActivity.this, "Confirmar senha está errado", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(cadastrar) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    boolean palestrante = campoPalestrante.isChecked();
                    usuario.setPalestrante(palestrante);
                    salvar(usuario, acao);
                }
            }
        });
    }

    private void salvar(final Usuario usuario, final String acao) {
        String email = usuario.getEmail();
        database.orderByChild("email").equalTo(email.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean existe = false;
                if(dataSnapshot.exists()) {
                    if(acao.equals("editar")) {
                        editar(dataSnapshot, usuario);
                        Intent intent = new Intent(CadastroActivity.this, MenuActivity.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        return;
                    }
                    existe = true;
                }
                if(!existe) {
                    String userId = database.push().getKey();
                    database.child(userId).setValue(usuario);
                    Intent intent = new Intent(CadastroActivity.this, usuario.isPalestrante() ? MinhaPalestraActivity.class : FiltroActivity.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } else {
                    Toast.makeText(CadastroActivity.this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void editar(DataSnapshot dataSnapshot, Usuario usuario) {
        for(DataSnapshot data : dataSnapshot.getChildren()) {
            Usuario value = data.getValue(Usuario.class);
            if(usuario.getEmail().equals(value.getEmail())) {
                Map<String, Object> update = new HashMap<String, Object>();
                update.put(data.getKey(), usuario);
                this.database.updateChildren(update);
                break;
            }
        }
    }
}
