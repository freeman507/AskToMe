package com.example.freeman.asktome.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by freeman on 06/04/17.
 */

@IgnoreExtraProperties
public class Usuario implements Serializable {

    private Long _id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String senha;
    private boolean palestrante;

    public Usuario() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPalestrante() {
        return palestrante;
    }

    public void setPalestrante(boolean palestrante) {
        this.palestrante = palestrante;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
