package com.example.freeman.asktome.dao;

import com.example.freeman.asktome.model.Palestra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by freeman on 29/05/17.
 */

public class PalestraDAO {

    private static PalestraDAO instance = null;
    private DatabaseReference database;
    private List<Palestra> palestras;

    private PalestraDAO() {
        this.database = FirebaseDatabase.getInstance().getReference("palestra");
        this.database.keepSynced(true);
    }

    public DatabaseReference getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseReference database) {
        this.database = database;
    }

    public List<Palestra> getPalestras() {
        return palestras;
    }

    public void setPalestras(List<Palestra> palestras) {
        this.palestras = palestras;
    }

    public static PalestraDAO getInstance() {
        if (instance == null) {
            instance = new PalestraDAO();
        }
        return instance;
    }

    public List<Palestra> search(final String codigo, final String titulo, final String palestrante) {
        this.palestras = new ArrayList<>();

        this.database.orderByChild("codigo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotPalestra : dataSnapshot.getChildren()) {
                    Palestra palestra = dataSnapshotPalestra.getValue(Palestra.class);
                    if (codigo.isEmpty() || !palestra.getCodigo().toLowerCase().equals(codigo.toLowerCase())) {
                        if (titulo.isEmpty() || !palestra.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                            if (palestrante.isEmpty() || !palestra.getNomePalestrante().toLowerCase().contains(palestrante.toLowerCase())) {
                                continue;
                            }
                        }
                    }
                    palestras.add(palestra);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return this.palestras;
    }
}
