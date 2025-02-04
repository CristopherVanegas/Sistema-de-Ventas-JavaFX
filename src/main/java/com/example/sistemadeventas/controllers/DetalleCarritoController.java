package com.example.sistemadeventas.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.example.sistemadeventas.models.Cliente;
import com.example.sistemadeventas.models.DetalleDePedidoCarrito;
import com.example.sistemadeventas.models.Pedido;
import com.example.sistemadeventas.models.Producto;
import com.example.sistemadeventas.models.SessionData;
import com.example.sistemadeventas.controllers.ProductAndCategoryJSONController;

public class DetalleCarritoController implements Initializable {

    @FXML
    private ComboBox<String> comboBoxCategorias;
    @FXML
    private Button btnVerCarrito;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> idColumna;
    @FXML
    private TableColumn<Producto, String> nombreColumna;
    @FXML
    private TableColumn<Producto, Double> precioColumna;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar las columnas de la tabla para mostrar productos del carrito
        TableColumn<Producto, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreColumna.setMinWidth(150);

        TableColumn<Producto, Double> precioColumna = new TableColumn<>("Precio");
        precioColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        precioColumna.setMinWidth(100);

        TableColumn<Producto, String> imagenColumna = new TableColumn<>("Imagen");
        imagenColumna.setCellValueFactory(new PropertyValueFactory<>("imagenPath"));
        imagenColumna.setMinWidth(100);

        // Configura la tabla para que se ajuste automáticamente al contenido
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Agrega las columnas a la tabla
        tablaProductos.getColumns().addAll(nombreColumna, precioColumna, imagenColumna);

        // Obtén la lista de productos del carrito
        List<Producto> productosCarrito = obtenerProductosDePedidosDelCliente(ProductAndCategoryJSONController.cargarSessionDataDesdeJSON(), ProductAndCategoryJSONController.cargarClientes(), ProductAndCategoryJSONController.cargarPedidos());

        // Crea una ObservableList a partir de la lista de productos del carrito
        ObservableList<Producto> productosObservable = FXCollections.observableArrayList(productosCarrito);

        // Configura la fuente y tamaño de fuente para la tabla
        Font font = new Font("Arial", 16);
        tablaProductos
                .setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");

        // Establece la lista de productos en la tabla
        tablaProductos.setItems(productosObservable);
    }

    private List<Producto> obtenerProductosDePedidosDelCliente(SessionData sessionData, List<Cliente> clientes, List<Pedido> pedidos) {
        // Buscar al cliente correspondiente utilizando el cedulaRUC de la SessionData
        Cliente clienteActual = clientes.stream()
                .filter(cliente -> cliente.getCedulaRUC().equals(sessionData.getCedulaRUC()))
                .findFirst()
                .orElse(null);
    
        if (clienteActual == null) {
            // No se encontró un cliente con el cedulaRUC de la SessionData
            return new ArrayList<>(); // Devolvemos una lista vacía en este caso
        }
    
        // Filtrar la lista de pedidos para obtener solo los pedidos del cliente actual
        List<Pedido> pedidosDelCliente = pedidos.stream()
                .filter(pedido -> pedido.getCliente().equals(clienteActual.getNombres() + clienteActual.getApellidos()))
                .collect(Collectors.toList());
    
        // Crear una lista para almacenar todos los productos de los pedidos del cliente
        List<Producto> todosLosProductosDelCliente = new ArrayList<>();
    
        // Recorrer todas las listas de productos de los pedidos y agregarlos a la lista
        pedidosDelCliente.forEach(pedido -> {
            List<Producto> productosDelPedido = pedido.getDetalleDePedidoCarrito().getProductos();
            todosLosProductosDelCliente.addAll(productosDelPedido);
        });
    
        return todosLosProductosDelCliente;
    }

    @FXML
    private void handleGoBack() {
        // Establece como pagina root PaginaCarrito
        try {
            // Agrega aquí la importación adecuada para App
            com.example.sistemadeventas.view.App.setRoot("PaginaCarrito");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al acceder a PaginaCompraProductos: " + e.getMessage());
        }
    }
}
