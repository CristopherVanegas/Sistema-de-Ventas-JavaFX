package com.example.sistemadeventas.models;

public class ClienteMayorista extends Cliente {
    private String personaContacto;

    // Constructor con argumentos
    public ClienteMayorista(String cedulaRUC, String nombres, String apellidos, String correo, String direccion, String telefono, String personaContacto) {
        super(cedulaRUC, nombres, apellidos, correo, direccion, telefono);
        this.personaContacto = personaContacto;
    }

    // Constructor sin argumentos
    public ClienteMayorista() {
        // Constructor sin argumentos
    }

    // Getter y setter para el atributo adicional
    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }
}
