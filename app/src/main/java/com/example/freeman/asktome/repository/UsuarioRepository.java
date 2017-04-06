package com.example.freeman.asktome.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.freeman.asktome.datasource.DataSource;
import com.example.freeman.asktome.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 17/03/2017.
 */

public class UsuarioRepository {

    private SQLiteDatabase db;

    public UsuarioRepository(Context context) {

        this.db = new DataSource(context).getWritableDatabase();
    }

    public void insert(Usuario usuario) {

        ContentValues values = new ContentValues();
        values.put(CarroContrato.CarroEntry.COLUNA_NOME, carro.getNome());
        values.put(CarroContrato.CarroEntry.COLUNA_PLACA, carro.getPlaca());
        values.put(CarroContrato.CarroEntry.COLUNA_ANO, carro.getAno());

        Long id = db.insert(CarroContrato.CarroEntry.NOME_TABELA, null, values);

        carro.setId(id);
    }

    public void update(Carro carro) {

        ContentValues values = new ContentValues();
        values.put(CarroContrato.CarroEntry.COLUNA_NOME, carro.getNome());
        values.put(CarroContrato.CarroEntry.COLUNA_PLACA, carro.getPlaca());
        values.put(CarroContrato.CarroEntry.COLUNA_ANO, carro.getAno());

        String where = CarroContrato.CarroEntry._ID + " = ?";
        String[] parametros = new String[]{String.valueOf(carro.getId())};

        db.update(CarroContrato.CarroEntry.NOME_TABELA, values, where, parametros);
    }

    public void delete(Carro carro) {

        String where = CarroContrato.CarroEntry._ID + " = ?";
        String[] parametros = new String[]{String.valueOf(carro.getId())};

        db.delete(CarroContrato.CarroEntry.NOME_TABELA, where, parametros);
    }

    public List<Carro> findAll() {

        String[] colunas = new String[]{
                CarroContrato.CarroEntry._ID,
                CarroContrato.CarroEntry.COLUNA_NOME,
                CarroContrato.CarroEntry.COLUNA_PLACA,
                CarroContrato.CarroEntry.COLUNA_ANO
        };

        List<Carro> carros = new ArrayList<>();

        String ordenacao = CarroContrato.CarroEntry.COLUNA_ANO + " DESC";

        Cursor cursor = db.query(
                CarroContrato.CarroEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                ordenacao
        );

        if (cursor.moveToFirst()) {

            int indexId = cursor.getColumnIndex(CarroContrato.CarroEntry._ID);
            int indexNome = cursor.getColumnIndex(CarroContrato.CarroEntry.COLUNA_NOME);
            int indexPlaca = cursor.getColumnIndex(CarroContrato.CarroEntry.COLUNA_PLACA);
            int indexAno = cursor.getColumnIndex(CarroContrato.CarroEntry.COLUNA_ANO);

            do {

                Carro carro = new Carro();
                carro.setId(cursor.getLong(indexId));
                carro.setNome(cursor.getString(indexNome));
                carro.setPlaca(cursor.getString(indexPlaca));
                carro.setAno(cursor.getInt(indexAno));

                carros.add(carro);

            } while (cursor.moveToNext());
        }

        return carros;
    }

    public void close() {
        this.db.close();
    }

    public Carro findOne(Long id) {
        Carro carro = null;
        String[] colunas = new String[]{
                CarroContrato.CarroEntry._ID,
                CarroContrato.CarroEntry.COLUNA_NOME,
                CarroContrato.CarroEntry.COLUNA_PLACA,
                CarroContrato.CarroEntry.COLUNA_ANO
        };

        String where = CarroContrato.CarroEntry._ID + " = ?";

        String[] argumentos = new String[] {
                String.valueOf(id)
        };

        Cursor cursor = db.query(
                false,
                CarroContrato.CarroEntry.NOME_TABELA,
                colunas,
                where,
                argumentos,
                null,
                null,
                null,
                null
        );

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            carro = new Carro();
            carro.setId(cursor.getLong(0));
            carro.setNome(cursor.getString(1));
            carro.setPlaca(cursor.getString(2));
            carro.setAno(cursor.getInt(3));
        }
        return carro;
    }
}





