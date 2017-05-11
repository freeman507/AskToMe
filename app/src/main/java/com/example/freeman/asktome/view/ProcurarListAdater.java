package com.example.freeman.asktome.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.activity.ProcurarActivity;
import com.example.freeman.asktome.model.Palestra;
import com.example.freeman.asktome.model.Usuario;

import java.util.List;

/**
 * Created by freeman on 10/05/17.
 */

public class ProcurarListAdater extends BaseAdapter {

    private List<Palestra> palestras;
    private List<Usuario> palestrantes;
    private LayoutInflater inflater;

    public ProcurarListAdater(Context context, List<Palestra> palestras, List<Usuario> palestrantes) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.palestras = palestras;
        this.palestrantes = palestrantes;
    }

    @Override
    public int getCount() {
        return this.palestras.size();
    }

    @Override
    public Object getItem(int position) {
        return this.palestras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Palestra palestra = this.palestras.get(position);
        Usuario palestrante = filterByIdPalestrante(palestra.getIdPalestrante(), this.palestrantes);
        View view = inflater.inflate(R.layout.linha_lista_procurar_palestras, null);

        TextView tituloTxtView = (TextView) view.findViewById(R.id.titulo_palestra_procurar);
        TextView palestranteTxtView = (TextView) view.findViewById(R.id.nome_palestrante_procurar);
        TextView horaTxtView = (TextView) view.findViewById(R.id.hora_palestra_procurar);

        tituloTxtView.setText(palestra.getTitulo());
        palestranteTxtView.setText(palestrante.getNome() + " " + palestrante.getSobrenome());
        horaTxtView.setText(palestra.getData() + " - " + palestra.getHora());

        return view;
    }

    private Usuario filterByIdPalestrante(Long idPalestrante, List<Usuario> palestrantes) {
        for (Usuario palestrante: palestrantes) {
            if(palestrante.get_id().equals(idPalestrante)) {
                return palestrante;
            }
        }
        return null;
    }
}
