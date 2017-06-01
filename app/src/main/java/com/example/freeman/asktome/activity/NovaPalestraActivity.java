package com.example.freeman.asktome.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.dao.PalestraDAO;
import com.example.freeman.asktome.dao.PerguntaDAO;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Pergunta;
import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NovaPalestraActivity extends AppCompatActivity implements Button.OnClickListener {

    private EditText campoCodigo;
    private EditText campoTitulo;
    private EditText campoData;
    private EditText campoHora;
    private EditText campoDuracao;
    private EditText campoEndereço;
    private EditText campoDescricao;
    private Button cadastrarButton;

    private DatabaseReference database;

    private Usuario usuario;
    private Palestra palestra;
    private String acao;
    private String codigoAntigo;
    private PalestraDAO dao = PalestraDAO.getInstance();
    private PerguntaDAO perguntaDAO = PerguntaDAO.getInstance();
    private FragmentManager fragmentManager = getFragmentManager();

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    static final int DURING_DIALOG_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_palestra);


        database = FirebaseDatabase.getInstance().getReference("palestra");

        database.keepSynced(true);

        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.palestra = (Palestra) getIntent().getSerializableExtra("palestra");

        campoCodigo = (EditText) findViewById(R.id.codigo_palestra);
        campoTitulo = (EditText) findViewById(R.id.titulo_palestra);
        campoData = (EditText) findViewById(R.id.data_palestra);
        campoHora = (EditText) findViewById(R.id.hora_palestra);
        campoDuracao = (EditText) findViewById(R.id.duracao_palestra);
        campoEndereço = (EditText) findViewById(R.id.local_palestra);
        campoDescricao = (EditText) findViewById(R.id.descricao_palestra);
        cadastrarButton = (Button) findViewById(R.id.cadastrar_palestra_btn);

        campoData.setOnClickListener(this);

        this.acao = "nova";
        if(palestra != null) {
            campoCodigo.setText(palestra.getCodigo());
            campoTitulo.setText(palestra.getTitulo());
            campoDescricao.setText(palestra.getDescricao());
            campoEndereço.setText(palestra.getEndereco());
            campoData.setText(palestra.getData());
            campoHora.setText(palestra.getHora());
            campoDuracao.setText(palestra.getDuracao());
            cadastrarButton.setText("EDITAR");
            this.acao = "editar";
            this.codigoAntigo = palestra.getCodigo();
        }

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = campoCodigo.getText().toString();
                String titulo = campoTitulo.getText().toString();
                String descricao = campoDescricao.getText().toString();
                String endereco = campoEndereço.getText().toString();
                String data = campoData.getText().toString();
                String duracao = campoDuracao.getText().toString();
                String hora = campoHora.getText().toString();
                boolean cadastrar = true;

                if(codigo.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo codigo está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(titulo.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo titulo da palestra está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(endereco.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo endereço está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(data.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo data está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(duracao.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo duração da palestra está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }
                if(hora.isEmpty()) {
                    Toast.makeText(NovaPalestraActivity.this, "Campo hora da palestra está vazio", Toast.LENGTH_SHORT).show();
                    cadastrar = false;
                }

                if(cadastrar) {
                    Palestra palestra = new Palestra();
                    palestra.setNomePalestrante(usuario.getNome() + " " + usuario.getSobrenome());
                    palestra.setEmailPalestrante(usuario.getEmail());
                    palestra.setCodigo(codigo);
                    palestra.setTitulo(titulo);
                    palestra.setDescricao(descricao);
                    palestra.setEndereco(endereco);
                    palestra.setData(data);
                    palestra.setDuracao(duracao);
                    palestra.setHora(hora);
                    if(acao.equals("nova")) {
                        salvar(palestra);
                    } else {
                        editar(palestra);
                    }
                }
            }
        });
    }

    public void salvar(final Palestra palestra) {
        this.database.orderByChild("codigo").equalTo(palestra.getCodigo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    String userId = database.push().getKey();
                    database.child(userId).setValue(palestra);
                    voltarParaMenu();
                } else {
                    Toast.makeText(NovaPalestraActivity.this, "Codigo da palestra já está em uso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void editar(final Palestra palestra) {
        if (codigoAntigo.equals(palestra.getCodigo())) {
            String codigo = palestra.getCodigo();
            this.database.orderByChild("codigo").equalTo(codigo).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String key = data.getKey();
                        Map<String, Object> map = new HashMap<>();
                        map.put(key, palestra);
                        database.updateChildren(map);
                        break;
                    }
                    voltarParaMenu();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            this.database.orderByChild("codigo").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Palestra value = data.getValue(Palestra.class);
                        if(value.getCodigo().equals(palestra.getCodigo())) {
                            Toast.makeText(NovaPalestraActivity.this, "Codigo da palestra já está em uso", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put(data.getKey(), palestra);
                        database.updateChildren(map);
                        atualizaCodigoPalestra(palestra.getCodigo());
                        break;
                    }
                    voltarParaMenu();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void atualizaCodigoPalestra(final String codigoPalestra) {
        final DatabaseReference databasePergunta = perguntaDAO.getDatabase();
        databasePergunta.orderByChild("codigoPalestra").equalTo(this.codigoAntigo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Pergunta pergunta = data.getValue(Pergunta.class);
                    pergunta.setCodigoPalestra(codigoPalestra);
                    Map<String, Object> map = new HashMap<>();
                    map.put(data.getKey(), pergunta);
                    databasePergunta.updateChildren(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void voltarParaMenu() {
        Intent intent = new Intent(NovaPalestraActivity.this, MenuActivity.class);
        intent.putExtra("usuario", this.usuario);
        intent.putExtra("palestra", this.palestra);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == campoData)
            showDialog(DATE_DIALOG_ID);
        if (v == campoHora)
            showDialog(TIME_DIALOG_ID);
        if (v == campoDuracao)
            showDialog(DURING_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, hora, minuto, true);
            case DURING_DIALOG_ID:
                return new TimePickerDialog(this, mDuringSetListener, hora, minuto, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data = String.valueOf(dayOfMonth) + " /" + String.valueOf(monthOfYear+1) + " /" + String.valueOf(year);
            campoData.setText(data);
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            campoHora.setText(time);
        }
    };

    private TimePickerDialog.OnTimeSetListener mDuringSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            campoDuracao.setText(time);
        }
    };

}
