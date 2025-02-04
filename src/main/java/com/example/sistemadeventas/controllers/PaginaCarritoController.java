package com.example.sistemadeventas.controllers;

import java.io.IOException;
import java.util.List;

import com.example.sistemadeventas.models.DetalleDePedidoCarrito;
import com.example.sistemadeventas.models.Pedido;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.util.Date;

public class PaginaCarritoController {
    public PaginaCarritoController() {
        // Constructor sin argumentos
    }

    @FXML
    private TableView<Pedido> tablaCarrito;
    @FXML
    private TableColumn<Pedido, Integer> idPedidoColumna;
    @FXML
    private TableColumn<Pedido, String> clienteColumna;
    @FXML
    private TableColumn<Pedido, Date> fechaColumna;
    @FXML
    private TableColumn<Pedido, String> formaDeEnvioColumna;
    @FXML
    private TableColumn<Pedido, String> estadoDelPedidoColumna;
    @FXML
    private TableColumn<Pedido, Void> accionesColumna;

    private List<Pedido> pedidos;
    int idPedido;
    String getCliente;
    Date getFecha;
    String getFormaDeEnvio;
    String getEstadoDelPedido;

    @FXML
    private Button btnComprar;

    @FXML
    private void initialize() {
        // Crear las columnas personalizadas
        TableColumn<Pedido, String> idPedidoColumna = new TableColumn<>("ID Pedido");
        idPedidoColumna.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        idPedidoColumna.setMinWidth(100); // Establece el ancho mínimo deseado

        TableColumn<Pedido, String> clienteColumna = new TableColumn<>("Cliente");
        clienteColumna.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        clienteColumna.setMinWidth(150); // Establece el ancho mínimo deseado

        TableColumn<Pedido, Date> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        fechaColumna.setMinWidth(150); // Establece el ancho mínimo deseado

        TableColumn<Pedido, Double> subTotalColumna = new TableColumn<>("Subtotal");
        subTotalColumna.setCellValueFactory(cellData -> {
            DetalleDePedidoCarrito detalleCarrito = cellData.getValue().getDetalleDePedidoCarrito();
            if (detalleCarrito != null) {
                return new SimpleDoubleProperty(detalleCarrito.getSubtotal()).asObject();
            } else {
                return new SimpleDoubleProperty(0.0).asObject();
            }
        });
        subTotalColumna.setMinWidth(100); // Establece el ancho mínimo deseado

        TableColumn<Pedido, Double> totalColumna = new TableColumn<>("Total");
        totalColumna.setCellValueFactory(cellData -> {
            DetalleDePedidoCarrito detalleCarrito = cellData.getValue().getDetalleDePedidoCarrito();
            if (detalleCarrito != null) {
                return new SimpleDoubleProperty(detalleCarrito.getTotal()).asObject();
            } else {
                return new SimpleDoubleProperty(0.0).asObject();
            }
        });
        totalColumna.setMinWidth(100); // Establece el ancho mínimo deseado

        TableColumn<Pedido, String> formaDeEnvioColumna = new TableColumn<>("Forma de Envío");
        formaDeEnvioColumna.setCellValueFactory(new PropertyValueFactory<>("formaDeEnvio"));
        formaDeEnvioColumna.setMinWidth(150); // Establece el ancho mínimo deseado

        TableColumn<Pedido, String> estadoDelPedidoColumna = new TableColumn<>("Estado");
        estadoDelPedidoColumna.setCellValueFactory(new PropertyValueFactory<>("estadoDelPedido"));
        estadoDelPedidoColumna.setMinWidth(100); // Establece el ancho mínimo deseado

        TableColumn<Pedido, Void> accionesColumna = new TableColumn<>("Acciones");
        accionesColumna.setMinWidth(250); // Establece el ancho mínimo deseado

        // Crear una celda de fábrica personalizada para la columna de acciones
        accionesColumna.setCellFactory(param -> new TableCell<Pedido, Void>() {
            private final Button verCarritoButton = new Button("Ver Carrito");
            private final Button comprarButton = new Button("Comprar");
            private final Button eliminarPedidoButton = new Button("Eliminar");

            {
                // Define los eventos para los botones en cada fila
                verCarritoButton.setOnAction(event -> {
                    Pedido pedido = getTableRow().getItem();
                    if (pedido != null) {
                        // Establece como pagina root PaginaCarrito
                        try {
                            // Agrega aquí la importación adecuada para App
                            com.example.sistemadeventas.view.App.setRoot("detalle_carrito");
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.err.println("Error al acceder a detalle_carrito: " + e.getMessage());
                        }
                    }
                });
                verCarritoButton.setStyle("-fx-background-color: lightblue;"); // Cambiar el color de fondo

                comprarButton.setOnAction(event -> {
                    Pedido pedido = getTableRow().getItem();
                    if (pedido != null) {
                        // Cambiar el estado del pedido a "Cancelado"
                        pedido.setEstadoDelPedido("Cancelado");
                        // Actualizar la vista de la tabla
                        tablaCarrito.refresh();
                        // Aquí puedes guardar los cambios en el archivo JSON si es necesario
                        ProductAndCategoryJSONController.guardarPedidos(pedidos);
                    }
                });

                comprarButton.setStyle("-fx-background-color: lightgreen;"); // Cambiar el color de fondo

                eliminarPedidoButton.setOnAction(event -> {
                    Pedido pedido = getTableRow().getItem();
                    if (pedido != null) {
                        // Lógica para "Eliminar Pedido" aquí
                        handleEliminarPedido(pedido);
                    }
                });
                eliminarPedidoButton.setStyle("-fx-background-color: lightcoral;"); // Cambiar el color de fondo
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Agregar los botones a la celda de la fila actual
                    HBox buttonsBox = new HBox(verCarritoButton, comprarButton, eliminarPedidoButton);
                    buttonsBox.setSpacing(5);
                    buttonsBox.setAlignment(Pos.CENTER); // Centra el contenido horizontalmente
                    setGraphic(buttonsBox);
                }
            }
        });

        // Establecer estilo de fuente para las celdas
        setFontStyleForTableColumn(idPedidoColumna);
        setFontStyleForTableColumn(clienteColumna);
        setFontStyleForTableColumn(fechaColumna);
        setFontStyleForTableColumn(formaDeEnvioColumna);
        setFontStyleForTableColumn(subTotalColumna);
        setFontStyleForTableColumn(totalColumna);
        setFontStyleForTableColumn(estadoDelPedidoColumna);
        setFontStyleForTableColumn(accionesColumna);

        // Cargar los datos de los pedidos desde tu controlador de JSON o donde los
        // tengas
        pedidos = ProductAndCategoryJSONController.cargarPedidos();
        ObservableList<Pedido> listaPedidos = FXCollections.observableArrayList(pedidos);

        // Enlazar la lista a la tabla
        tablaCarrito.setItems(listaPedidos);

        tablaCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Agregar las columnas personalizadas a la tabla
        tablaCarrito.getColumns().addAll(
                idPedidoColumna,
                clienteColumna,
                fechaColumna,
                formaDeEnvioColumna,
                subTotalColumna, // Usa la columna de subTotal actualizada
                totalColumna, // Usa la columna de total actualizada
                estadoDelPedidoColumna,
                accionesColumna);
    }

    private void handleEliminarPedido(Pedido pedido) {
        if (pedido != null) {
            // Lógica para eliminar el pedido
            pedidos.remove(pedido);
            // Guardar la nueva lista de pedidos
            ProductAndCategoryJSONController.guardarPedidos(pedidos);
            // Actualizar la tabla
            tablaCarrito.getItems().remove(pedido);
        } else {
            // Muestra un mensaje de error o aviso al usuario
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un pedido para eliminar.");
            alert.showAndWait();
        }
    }

    private void setFontStyleForTableColumn(TableColumn<?, ?> column) {
        // Crear un estilo de fuente y aplicarlo a la columna
        Font font = new Font("Arial", 16);
        column.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
    }

    @FXML
    private void handleGoBack() {
        // Establece como pagina root PaginaCarrito
        try {
            // Agrega aquí la importación adecuada para App
            com.example.sistemadeventas.view.App.setRoot("PaginaCompraProductos");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al acceder a PaginaCompraProductos: " + e.getMessage());
        }
    }
}