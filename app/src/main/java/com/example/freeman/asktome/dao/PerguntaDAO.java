package com.example.freeman.asktome.dao;

import com.example.freeman.asktome.model.Pergunta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by freeman on 29/05/17.
 */

public class PerguntaDAO {

    private static PerguntaDAO instance = null;
    private DatabaseReference database;

    private PerguntaDAO() {
        this.database = FirebaseDatabase.getInstance().getReference("pergunta");
        this.database.keepSynced(true);
    }

    public DatabaseReference getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseReference database) {
        this.database = database;
    }

    public static PerguntaDAO getInstance() {
        if(instance == null) {
            instance = new PerguntaDAO();
        }
        return instance;
    }

    public void atualizaCodigoPalestra(final String codigo) {
        this.database.orderByChild("codigoPalestra").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey();
                    Pergunta value = data.getValue(Pergunta.class);
                    value.setCodigoPalestra(codigo);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(key, value);
                    database.updateChildren(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
