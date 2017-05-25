package com.example.freeman.asktome.model;

import java.io.Serializable;

/**
 * Created by freeman on 22/05/17.
 */

public class Pergunta implements Serializable {

    private String codigoPalestra;
    private String emailUsuario;
    private String nomeUsuario;
    private String pergunta;
    private boolean respondida;
    private Long timestamp;

    public Pergunta() {
    }

    public String getCodigoPalestra() {
        return codigoPalestra;
    }

    public void setCodigoPalestra(String codigoPalestra) {
        this.codigoPalestra = codigoPalestra;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
