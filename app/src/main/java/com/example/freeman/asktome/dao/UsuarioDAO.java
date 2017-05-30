package com.example.freeman.asktome.dao;

import com.example.freeman.asktome.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by freeman on 29/05/17.
 */

public class UsuarioDAO {

    private static UsuarioDAO instance = null;
    private DatabaseReference database;

    private UsuarioDAO() {
        this.database = FirebaseDatabase.getInstance().getReference("usuario");
    }

    public DatabaseReference getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseReference database) {
        this.database = database;
    }

    public static UsuarioDAO getInstance() {
        if(instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

}
