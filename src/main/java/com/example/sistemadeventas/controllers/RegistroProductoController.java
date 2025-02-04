package com.example.sistemadeventas.controllers;

import com.example.sistemadeventas.models.Categoria;
import com.example.sistemadeventas.models.Producto;
import com.example.sistemadeventas.view.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;

import java.io.IOException;
import java.util.List;

public class RegistroProductoController {
    private List<Categoria> categorias = ProductAndCategoryJSONController.cargarCategoriasDesdeJSON();
    private List<Producto> productos = ProductAndCategoryJSONController.cargarProductosDesdeJSON();

    @FXML
    private TextField nombreField;

    @FXML
    private TextField precioField;

    @FXML
    private ComboBox<String> categoriaComboBox; // Usamos ComboBox<String> para representar el nombre de la categoría

    @FXML
    private TextField imagenPathField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button seleccionarImagenButton; // Asegúrate de que el ID del botón coincida con el del FXML

    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(null); // Abre el diálogo de selección de archivo

        if (selectedFile != null) {
            // Obtiene la ruta del archivo seleccionado y la muestra en el campo de texto
            imagenPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void initialize() {
        // Agregar el mensaje predeterminado "Seleccione una opción" al ComboBox
        categoriaComboBox.getItems().add("Seleccione");

        // Cargar las categorías reales en el ComboBox (excepto el mensaje
        // predeterminado)
        for (Categoria categoria : categorias) {
            categoriaComboBox.getItems().add(categoria.getNombre());
        }

        // Establecer "Seleccione una opción" como elemento seleccionado por defecto
        categoriaComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleProductRegister() {
        // Obtener los valores ingresados por el usuario
        String nombre = nombreField.getText();
        String precioStr = precioField.getText();
        String nombreCategoria = categoriaComboBox.getValue(); // Obtener el nombre de la categoría seleccionada
        String imagenPath = imagenPathField.getText();

        // Validar los campos ingresados (puedes agregar validaciones aquí)

        // Convertir el precio a double
        double precio = Double.parseDouble(precioStr);

        // Buscar la instancia de Categoria correspondiente al nombre seleccionado
        Categoria categoriaSeleccionada = null;
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equals(nombreCategoria)) {
                categoriaSeleccionada = categoria;
                break;
            }
        }

        // Verificar si se encontró una categoría válida
        if (categoriaSeleccionada == null) {
            errorLabel.setText("Por favor, seleccione una categoría válida.");
            return;
        }

        // Crear un nuevo producto con los datos ingresados
        productos.add(new Producto(productos.size() + 1000, nombre, precio, categoriaSeleccionada, imagenPath));

        // Guarda la nueva lista de productos en el .json
        ProductAndCategoryJSONController.guardarCategoriasYProductosEnJSON(categorias, productos);

        try {
            App.setRoot("adminProductManager");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        try {
            App.setRoot("adminProductManager");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
