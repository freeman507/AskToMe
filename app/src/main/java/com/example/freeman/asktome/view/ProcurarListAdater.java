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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by freeman on 10/05/17.
 */

public class ProcurarListAdater extends BaseAdapter {

    private List<Palestra> palestras;
    private LayoutInflater inflater;

    public ProcurarListAdater(Context context, List<Palestra> palestras) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.palestras = palestras;
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
        View view = inflater.inflate(R.layout.linha_lista_procurar_palestras, null);

        TextView codigoTxtView = (TextView) view.findViewById(R.id.codigo_palestra_procurar);
        TextView tituloTxtView = (TextView) view.findViewById(R.id.titulo_palestra_procurar);
        TextView palestranteTxtView = (TextView) view.findViewById(R.id.nome_palestrante_procurar);
        TextView horaTxtView = (TextView) view.findViewById(R.id.hora_palestra_procurar);
        TextView localTxtView = (TextView) view.findViewById(R.id.local_palestra_procurar);

        codigoTxtView.setText(palestra.getCodigo());
        tituloTxtView.setText(palestra.getTitulo());
        palestranteTxtView.setText(palestra.getNomePalestrante());
        horaTxtView.setText(palestra.getData() + " - " + palestra.getHora());
        localTxtView.setText(palestra.getEndereco());

        return view;
    }

}
