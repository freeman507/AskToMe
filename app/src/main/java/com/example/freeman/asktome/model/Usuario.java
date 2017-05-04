package com.example.freeman.asktome.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by freeman on 06/04/17.
 */

@IgnoreExtraProperties
public class Usuario {

    private String login;
    private String senha;

    public String getUsuario() {
        return login;
    }

    public void setUsuario(String usuario) {
        this.login = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
