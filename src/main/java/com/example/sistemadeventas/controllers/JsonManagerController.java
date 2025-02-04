package com.example.sistemadeventas.controllers;

import com.example.sistemadeventas.models.Cliente;
import com.example.sistemadeventas.models.ClienteMayorista;
import com.example.sistemadeventas.models.ClienteMinorista;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonManagerController {
    private final String filePath;

    public JsonManagerController(String filePath) {
        this.filePath = filePath;
    }

    public void guardarClientes(List<Cliente> clientes) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarMayoristas(List<ClienteMayorista> mayoristas) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), mayoristas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarMinoristas(List<ClienteMinorista> minoristas) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), minoristas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> cargarClientes() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Cliente> clientes = new ArrayList<>();

        try {
            clientes = objectMapper.readValue(new File(filePath), new TypeReference<List<Cliente>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public List<ClienteMayorista> cargarClienteMayorista() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ClienteMayorista> clientes = new ArrayList<>();

        try {
            clientes = objectMapper.readValue(new File(filePath), new TypeReference<List<ClienteMayorista>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public List<ClienteMinorista> cargarClienteMinorista() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ClienteMinorista> clientes = new ArrayList<>();

        try {
            clientes = objectMapper.readValue(new File(filePath), new TypeReference<List<ClienteMinorista>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientes;
    }
}