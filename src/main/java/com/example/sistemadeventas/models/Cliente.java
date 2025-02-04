package com.example.sistemadeventas.models;

import java.util.ArrayList;
import java.util.List;


public class Cliente {
    private String cedulaRUC;
    private String nombres;
    private String apellidos;
    private String correo;
    private String direccion;
    private String telefono;
    private String codigoCliente; // Asignado de manera única
    private List<Pedido> listaPedidos; // Lista de pedidos asociados al cliente

    // Constructor con argumentos
    public Cliente(String cedulaRUC, String nombres, String apellidos, String correo, String direccion,
            String telefono) {
        this.cedulaRUC = cedulaRUC;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        // Lógica para asignar un código de cliente único (puedes implementarla según tu
        // necesidad)
        this.codigoCliente = generarCodigoUnico();
        this.listaPedidos = new ArrayList<>(); // Inicializa la lista de pedidos
    }

    // Constructor sin argumentos
    public Cliente() {
        // Constructor sin argumentos
    }

    // Getters y setters para cada atributo
    public String getCedulaRUC() {
        return cedulaRUC;
    }

    public void setCedulaRUC(String cedulaRUC) {
        this.cedulaRUC = cedulaRUC;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    private String generarCodigoUnico() {
        // Lógica para generar un código único (puedes implementarla según tu necesidad)
        // Ejemplo simple: Puedes concatenar cedulaRUC con algún valor aleatorio.
        return cedulaRUC + "_" + Math.random();
    }
}
