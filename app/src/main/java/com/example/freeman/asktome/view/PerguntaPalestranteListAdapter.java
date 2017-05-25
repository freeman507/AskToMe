package com.example.freeman.asktome.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.activity.CadastroActivity;
import com.example.freeman.asktome.activity.MenuActivity;
import com.example.freeman.asktome.model.Pergunta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by freeman on 23/05/17.
 */
public class PerguntaPalestranteListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Pergunta> perguntas;

    public PerguntaPalestranteListAdapter(Context context, List<Pergunta> perguntas) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.perguntas = perguntas;
    }

    @Override
    public int getCount() {
        return this.perguntas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.perguntas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.linha_lista_perguntas_palestrante, null);
        TextView nomeUsuario = (TextView) view.findViewById(R.id.nome_usuario_palestrante_txt);
        TextView perguntaUsuario = (TextView) view.findViewById(R.id.pergunta_palestrante_txt);
        final Switch respondida = (Switch) view.findViewById(R.id.respondida);

        final Pergunta pergunta = this.perguntas.get(position);
        nomeUsuario.setText(pergunta.getNomeUsuario());
        perguntaUsuario.setText(pergunta.getPergunta());
        respondida.setChecked(pergunta.isRespondida());

        respondida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pergunta.setRespondida(respondida.isChecked());
                editarPergunta(pergunta);
            }
        });
        return view;
    }

    private void editarPergunta(final Pergunta pergunta) {

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("pergunta");
        final Long timestamp = pergunta.getTimestamp();
        database.orderByChild("timestamp").equalTo(timestamp).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot data : children) {
                    Pergunta value = data.getValue(Pergunta.class);
                    if(value.getTimestamp().equals(timestamp) && value.getEmailUsuario().equals(pergunta.getEmailUsuario())) {
                        Map<String, Object> update = new HashMap<String, Object>();
                        update.put(data.getKey(), pergunta);
                        database.updateChildren(update);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
