package com.example.freeman.asktome.model;

/**
 * Created by freeman on 06/04/17.
 */

public class Usuario {

    private Long _id;
    private String nome;
    private String sobreNome;
    private String telefone;
    private String email;
    private String senha;
    private boolean palestrante;

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

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPalestrante() {
        return palestrante;
    }

    public void setPalestrante(boolean palestrante) {
        this.palestrante = palestrante;
    }
}
