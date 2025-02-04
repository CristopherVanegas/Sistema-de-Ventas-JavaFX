package com.example.sistemadeventas.models;

import java.util.List;

public class DetalleDePedidoCarrito {
    private List<Producto> productos;
    private double total;
    private double subtotal;

    public DetalleDePedidoCarrito() {
        // Constructor sin argumentos para deserialización
    }

    // Agregar un constructor que se adapte a la creación de un nuevo DetalleDePedidoCarrito
    public DetalleDePedidoCarrito(List<Producto> carrito) {
        this.productos = carrito;

        calcularSubtotal();
        calcularTotal();
        this.total = total;
        this.subtotal = subtotal;
    }

    // Getters y setters para los campos primitivos
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Method to calculate total and subTotal
    public void calcularSubtotal() {
        subtotal = 0.0;
        for (Producto producto : productos) {
            subtotal += producto.getPrecio();
        }
    }

    public void calcularTotal() {
        total = subtotal; // Puedes agregar lógica adicional aquí si es necesario
    }
}
