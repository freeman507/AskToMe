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
}
