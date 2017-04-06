package com.example.freeman.asktome.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diego on 17/03/2017.
 */

public class DataSource extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "carro.db";

    private static final String TABELA_CARRO = "create table usuario(\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\tnome text not null,\n" +
            "\tsobre_nome text not null,\n" +
            "\ttelefone text not null,\n" +
            "\temail text not null\n" +
            "\tsenha text not null\n" +
            "\tpalestrante boolean not null\n" +
            ");\n";

    public DataSource(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABELA_CARRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
