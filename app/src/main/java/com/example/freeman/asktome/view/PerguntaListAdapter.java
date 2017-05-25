package com.example.freeman.asktome.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Pergunta;

import java.util.List;

/**
 * Created by freeman on 23/05/17.
 */

public class PerguntaListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Pergunta> perguntas;

    public PerguntaListAdapter(Context context, List<Pergunta> perguntas) {
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
        View view = inflater.inflate(R.layout.linha_lista_perguntas, null);
        TextView nomeUsuario = (TextView) view.findViewById(R.id.nome_usuario_txt);
        TextView perguntaUsuario = (TextView) view.findViewById(R.id.pergunta_usuario_txt);
        Pergunta pergunta = this.perguntas.get(position);
        nomeUsuario.setText(pergunta.getNomeUsuario());
        perguntaUsuario.setText(pergunta.getPergunta());
        return view;
    }
}
