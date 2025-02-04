package com.example.sistemadeventas.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionData {
    @JsonProperty("cedulaRUC")
    private String cedulaRUC;

    // Constructor vacío necesario para la deserialización
    public SessionData() {
    }

    public SessionData(String cedulaRUC) {
        this.cedulaRUC = cedulaRUC;
    }

    public String getCedulaRUC() {
        return cedulaRUC;
    }

    public void setCedulaRUC(String cedulaRUC) {
        this.cedulaRUC = cedulaRUC;
    }
}
