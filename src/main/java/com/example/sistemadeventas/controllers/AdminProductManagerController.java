package com.example.sistemadeventas.controllers;

import com.example.sistemadeventas.models.Categoria;
import com.example.sistemadeventas.models.Cliente;
import com.example.sistemadeventas.models.Pedido;
import com.example.sistemadeventas.models.Producto;
import com.example.sistemadeventas.models.SessionData;
import com.example.sistemadeventas.view.App;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminProductManagerController {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    // private List<DetalleDePedidoCarrito> detalleDePedidoCarritos = new
    // ArrayList<>();
    private List<Producto> productos = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private static List<Producto> carrito = new ArrayList<>(); // Lista para almacenar los productos del carrito
    private static SessionData sessionData = null;
    private static String cedulaRUCSession = null;
    private static Cliente clienteEncontrado = null;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private ComboBox<Categoria> comboBoxCategorias;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane alertPane;

    private Alert alert;

    public AdminProductManagerController() {
        // Inicializa las categorias por defecto
        categorias = ProductAndCategoryJSONController.cargarCategoriasDesdeJSON();

        // Inicializa productos
        productos = ProductAndCategoryJSONController.cargarProductosDesdeJSON();
    }

    @FXML
    private void initialize() {
        // 1ra Columna Imagenes
        TableColumn<Producto, String> imagenColumna = new TableColumn<>("Imagen");
        imagenColumna.setCellValueFactory(new PropertyValueFactory<>("imagenPath"));
        imagenColumna.setMinWidth(100);
        imagenColumna.setMaxWidth(100);

        imagenColumna.setCellFactory(column -> {
            return new TableCell<Producto, String>() {
                final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        // Cargar la imagen desde la ruta
                        Image image = new Image(new File(item).toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        setGraphic(imageView);
                    }
                }
            };
        });
        // Aplicar estilos de fuente a los elementos
        tablaProductos.setStyle("-fx-font-size: 16;");
        comboBoxCategorias.setStyle("-fx-font-size: 16;");

        // 2da Columna Precio
        TableColumn<Producto, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreColumna.prefWidthProperty().bind(tablaProductos.widthProperty().divide(4)); // Divide el ancho en 4
                                                                                          // columnas
        // 3ra Columna Precio
        TableColumn<Producto, Double> precioColumna = new TableColumn<>("Precio");
        precioColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        precioColumna.prefWidthProperty().bind(tablaProductos.widthProperty().divide(4)); // Divide el ancho en 4
                                                                                          // columnas
        // 4ta Columna Categoria
        TableColumn<Producto, String> categoriaColumna = new TableColumn<>("Categoría");
        categoriaColumna.setCellValueFactory(cellData -> cellData.getValue().getCategoria().nombreProperty());
        categoriaColumna.prefWidthProperty().bind(tablaProductos.widthProperty().divide(4)); // Divide el ancho en 4

        // 5ta Columna Carrito
        TableColumn<Producto, Button> agregarCarritoColumna = new TableColumn<>("Acción");
        agregarCarritoColumna
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(new Button("Eliminar Producto")));
        agregarCarritoColumna.prefWidthProperty().bind(tablaProductos.widthProperty().divide(4)); // Divide el ancho en

        agregarCarritoColumna.setCellFactory(column -> {
            return new TableCell<Producto, Button>() {
                final Button button = new Button("Eliminar Producto");

                {
                    button.setOnAction(event -> {
                        Producto producto = getTableView().getItems().get(getIndex());
                        handleEliminarProducto(producto);
                    });
                    button.setStyle("-fx-background-color: lightcoral;"); // Cambiar el color de fondo
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }

                private void handleEliminarProducto(Producto producto) {
                    // Obtener la lista actual de productos de la tabla
                    ObservableList<Producto> productosEnTabla = tablaProductos.getItems();

                    // Eliminar el producto de la lista
                    productosEnTabla.remove(producto);

                    // Si deseas eliminarlo también de la lista de productos global, puedes hacerlo
                    // así:
                    productos.remove(producto);

                    // Actualizar la tabla
                    tablaProductos.setItems(productosEnTabla);

                    // Guardo la lista productos en el .json
                    ProductAndCategoryJSONController.guardarCategoriasYProductosEnJSON(categorias, productosEnTabla);

                    // Mostrar una alerta o realizar cualquier otra acción que necesites
                    mostrarAlerta("Producto eliminado exitosamente.");
                }
            };
        });

        // Styles
        imagenColumna.setStyle("-fx-alignment: CENTER;");
        nombreColumna.setStyle("-fx-alignment: CENTER;");
        precioColumna.setStyle("-fx-alignment: CENTER;");
        categoriaColumna.setStyle("-fx-alignment: CENTER;");
        agregarCarritoColumna.setStyle("-fx-alignment: CENTER;");

        tablaProductos.getColumns().addAll(imagenColumna, nombreColumna, precioColumna, categoriaColumna,
                agregarCarritoColumna);
        tablaProductos.setItems(FXCollections.observableArrayList(productos));

        // Configurar la tabla para que se ajuste automáticamente al contenido
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Configura combobox
        comboBoxCategorias.setItems(FXCollections.observableArrayList(categorias));
        comboBoxCategorias.getItems().add(0, new Categoria(0, "Todos"));
        comboBoxCategorias.setValue(comboBoxCategorias.getItems().get(0));

        comboBoxCategorias.setOnAction(event -> filtrarProductosPorCategoria());
    }

    @FXML
    private void handleAddProduct() {
        // Navegar a la pantalla de registro
        try {
            App.setRoot("registroProducto");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void filtrarProductosPorCategoria() {
        Categoria categoriaSeleccionada = comboBoxCategorias.getValue();
        if (categoriaSeleccionada == null || categoriaSeleccionada.getId() == 0) {
            tablaProductos.setItems(FXCollections.observableArrayList(productos));
        } else {
            List<Producto> productosFiltrados = productos.stream()
                    .filter(producto -> producto.getCategoria().getId() == categoriaSeleccionada.getId())
                    .collect(Collectors.toList());
            tablaProductos.setItems(FXCollections.observableArrayList(productosFiltrados));
        }
    }

    private void mostrarAlerta(String mensaje) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        try {
            File sessionFile = new File("src/main/java/com/example/sistemadeventas/data/session.json");
            if (sessionFile.exists()) {
                sessionFile.delete();
                System.out.println("Sesión y carrito cerrados correctamente.");
            } else {
                System.err.println("El archivo de sesión no existe.");
            }

            // Agrega aquí la importación adecuada para App
            com.example.sistemadeventas.view.App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cerrar sesión: " + e.getMessage());
        }
    }
}
