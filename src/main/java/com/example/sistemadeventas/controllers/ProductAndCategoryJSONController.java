package com.example.sistemadeventas.controllers;

import com.example.sistemadeventas.models.Categoria;
import com.example.sistemadeventas.models.Cliente;
import com.example.sistemadeventas.models.Pedido;
import com.example.sistemadeventas.models.Producto;
import com.example.sistemadeventas.models.SessionData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductAndCategoryJSONController {
    private static final String CLIENTES_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\clientes.json";
    private static final String CATEGORIAS_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\categorias.json";
    private static final String PRODUCTOS_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\productos.json";
    private static final String PEDIDOS_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\pedidos.json";
    private static final String DETALLE_PEDIDO_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\carrito-detalle-pedido.json";
    private static final String SESSION_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\session.json";

    public static SessionData cargarSessionDataDesdeJSON() {
        // Crear un ObjectMapper para deserializar el JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el archivo JSON y deserializarlo en un objeto SessionData
            SessionData sessionData = objectMapper.readValue(new File(SESSION_JSON_PATH), SessionData.class);
            return sessionData;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Manejo de errores (puedes personalizarlo según tus necesidades)
        }
    }

    public static void guardarPedidos(List<Pedido> pedido) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(PEDIDOS_JSON_PATH), pedido);
            System.out.println("pedido guardado en archivo JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar la lista de usuarios en el archivo JSON: " + e.getMessage());
        }
    }

    public static List<Pedido> cargarPedidos() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Pedido> pedidos = new ArrayList<>();
        try {
            pedidos = objectMapper.readValue(new File(PEDIDOS_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Pedido.class));
            System.out.println("pedidos cargados desde archivo JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la lista de pedidos desde el archivo JSON: " + e.getMessage());
        }
        return pedidos;
    }

    public static void guardarClientes(List<Cliente> clientes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(CLIENTES_JSON_PATH), clientes);
            System.out.println("Clientes guardado en archivo JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar la lista de usuarios en el archivo JSON: " + e.getMessage());
        }
    }

    public static List<Cliente> cargarClientes() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Cliente> clientes = new ArrayList<>();
        try {
            clientes = objectMapper.readValue(new File(CLIENTES_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Cliente.class));
            System.out.println("clientes cargados desde archivo JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la lista de clientes desde el archivo JSON: " + e.getMessage());
        }
        return clientes;
    }

    public static void guardarCarritoEnJSON(List<Producto> carrito) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(DETALLE_PEDIDO_JSON_PATH), carrito);
            System.out.println("Carrito guardado en archivo JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar el carrito en el archivo JSON: " + e.getMessage());
        }
    }

    public static List<Producto> cargarCarritoDesdeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Producto> carrito = new ArrayList<>();
        try {
            carrito = objectMapper.readValue(new File(DETALLE_PEDIDO_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Producto.class));
            System.out.println("Carrito cargado desde archivo JSON.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el carrito desde el archivo JSON: " + e.getMessage());
        }
        return carrito;
    }

    public static void guardarCategoriasYProductosEnJSON(List<Categoria> categorias, List<Producto> productos) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serializa categorías a JSON y guárdalas en un archivo
            objectMapper.writeValue(new File(CATEGORIAS_JSON_PATH), categorias);

            // Serializa productos a JSON y guárdalos en un archivo
            objectMapper.writeValue(new File(PRODUCTOS_JSON_PATH), productos);

            System.out.println("Datos guardados en archivos JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Categoria> cargarCategoriasDesdeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Deserializa categorías desde un archivo JSON
            return objectMapper.readValue(
                    new File(CATEGORIAS_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Categoria.class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Producto> cargarProductosDesdeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Deserializa productos desde un archivo JSON
            return objectMapper.readValue(
                    new File(PRODUCTOS_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Producto.class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
