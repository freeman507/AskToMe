package com.example.freeman.asktome.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by freeman on 09/05/17.
 */

public class Palestra implements Serializable {

    private Long _id;
    private Long idPalestrante;
    private String codigo;
    private String titulo;
    private String data;
    private String hora;
    private String duracao;
    private String endereco;
    private String descricao;

    public Palestra() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getIdPalestrante() {
        return idPalestrante;
    }

    public void setIdPalestrante(Long idPalestrante) {
        this.idPalestrante = idPalestrante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
