package com.example.sistemadeventas.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.sistemadeventas.models.Cliente;
import com.example.sistemadeventas.models.ClienteMayorista;
import com.example.sistemadeventas.models.ClienteMinorista;
import com.example.sistemadeventas.view.App;

public class RegisterController {
    @FXML
    private TextField cedulaRUCField;

    @FXML
    private TextField nombresField;

    @FXML
    private TextField apellidosField;

    @FXML
    private TextField correoField;

    @FXML
    private TextField direccionField;

    @FXML
    private TextField telefonoField;

    @FXML
    private Label errorLabel;

    @FXML
    private RadioButton minoristaRadio;

    @FXML
    private RadioButton mayoristaRadio;

    private ToggleGroup tipoClienteToggleGroup;

    // Listas para almacenar clientes, minoristas y mayoristas
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<ClienteMinorista> listaMinoristas = new ArrayList<>();
    private List<ClienteMayorista> listaMayoristas = new ArrayList<>();

    private static final String CLIENTES_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\clientes.json";
    private static final String MAYORISTAS_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\mayoristas.json";
    private static final String MINORISTAS_JSON_PATH = "sistemadeventas\\src\\main\\java\\com\\example\\sistemadeventas\\data\\minoristas.json";

    private JsonManagerController ClientesJsonManagerController = new JsonManagerController(CLIENTES_JSON_PATH);
    private JsonManagerController MayoristasJsonManagerController = new JsonManagerController(MAYORISTAS_JSON_PATH);
    private JsonManagerController MinoristasJsonManagerController = new JsonManagerController(MINORISTAS_JSON_PATH);

    @FXML
    private void initialize() {
        tipoClienteToggleGroup = new ToggleGroup();
        minoristaRadio.setToggleGroup(tipoClienteToggleGroup);
        mayoristaRadio.setToggleGroup(tipoClienteToggleGroup);

        // Leer datos existentes de los archivos y cargar las listas
        cargarDatosDesdeArchivo();
    }

    @FXML
    private void handleRegister() throws IOException {
        if (camposLlenos() && tipoClienteSeleccionado()) {
            String cedulaRUC = cedulaRUCField.getText();

            // Verificar si el cliente ya existe
            if (clienteYaExiste(cedulaRUC)) {
                mostrarError("El cliente con el mismo Cédula/RUC ya está registrado.");
                return;
            }

            // Obtiene los datos ddl edtTexts
            String nombres = nombresField.getText();
            String apellidos = apellidosField.getText();
            String correo = correoField.getText();
            String direccion = direccionField.getText();
            String telefono = telefonoField.getText();
            String tipoCliente = minoristaRadio.isSelected() ? "Minorista" : "Mayorista";

            // Crear objeto Cliente, ClienteMayorista o ClienteMinorista según el tipo
            Cliente clienteGeneral = new Cliente(cedulaRUC, nombres, apellidos, correo, direccion, telefono);
            listaClientes.add(clienteGeneral);
            ClientesJsonManagerController.guardarClientes(listaClientes);

            // Agregar el nuevo cliente a la lista específica (minoristas o mayoristas)
            if ("Mayorista".equals(tipoCliente)) {
                ClienteMayorista mayorista = new ClienteMayorista(cedulaRUC, nombres, apellidos, correo, direccion,
                        telefono, "Persona de Contacto");
                listaMayoristas.add(mayorista);
                MayoristasJsonManagerController.guardarMayoristas(listaMayoristas);
            } else {
                ClienteMinorista minorista = new ClienteMinorista(cedulaRUC, nombres, apellidos, correo, direccion,
                        telefono, 0);
                listaMinoristas.add(minorista);
                MinoristasJsonManagerController.guardarMinoristas(listaMinoristas);
            }

            
            System.out.println("Cliente registrado con éxito");

            // Cambiar a la vista de inicio de sesión
            App.setRoot("login");
        } else {
            mostrarError("Por favor, llene todos los campos y seleccione el tipo de cliente");
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        App.setRoot("login");
    }

    private void cargarDatosDesdeArchivo() {
        listaClientes = ClientesJsonManagerController.cargarClientes();
        listaMayoristas = MayoristasJsonManagerController.cargarClienteMayorista();
        listaMinoristas = MinoristasJsonManagerController.cargarClienteMinorista();

        // Imprimir los datos cargados por consola
        System.out.println("Datos cargados desde CLIENTES_JSON_PATH:");
        listaClientes.forEach(System.out::println);

        System.out.println("\nDatos cargados desde MINORISTAS_JSON_PATH:");
        listaMinoristas.forEach(System.out::println);

        System.out.println("\nDatos cargados desde MAYORISTAS_JSON_PATH:");
        listaMayoristas.forEach(System.out::println);
    }

    private boolean camposLlenos() {
        return !cedulaRUCField.getText().isEmpty()
                && !nombresField.getText().isEmpty()
                && !apellidosField.getText().isEmpty()
                && !correoField.getText().isEmpty()
                && !direccionField.getText().isEmpty()
                && !telefonoField.getText().isEmpty();
    }

    private boolean tipoClienteSeleccionado() {
        return minoristaRadio.isSelected() || mayoristaRadio.isSelected();
    }

    private void mostrarError(String mensaje) {
        errorLabel.setText(mensaje);
    }

    private boolean clienteYaExiste(String cedulaRUC) {
        // Verificar si el cliente ya existe en alguna de las listas
        return listaClientes.stream().anyMatch(cliente -> cliente.getCedulaRUC().equals(cedulaRUC))
                || listaMinoristas.stream().anyMatch(cliente -> cliente.getCedulaRUC().equals(cedulaRUC))
                || listaMayoristas.stream().anyMatch(cliente -> cliente.getCedulaRUC().equals(cedulaRUC));
    }
}