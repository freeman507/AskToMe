package com.example.freeman.asktome.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.freeman.asktome.R;
import com.example.freeman.asktome.activity.MinhaPalestraActivity;
import com.example.freeman.asktome.model.Palestra;

import java.util.List;

/**
 * Created by freeman on 09/05/17.
 */
public class PalestraListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Palestra> palestras;

    public PalestraListAdapter(Context context, List<Palestra> palestras) {
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
        View view = inflater.inflate(R.layout.linha_lista_palestras, null);
        TextView titulo = (TextView) view.findViewById(R.id.linha_titulo_palestra);
        TextView hora = (TextView) view.findViewById(R.id.linha_hora_palestra);
        TextView local = (TextView) view.findViewById(R.id.linha_local_palestra);
        Palestra palestra = this.palestras.get(position);
        titulo.setText(palestra.getTitulo());
        hora.setText(palestra.getData() + " - "+ palestra.getHora());
        local.setText(palestra.getEndereco());
        return view;
    }
}
