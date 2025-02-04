package com.example.sistemadeventas.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Obtiene la pantalla principal
        Screen screen = Screen.getPrimary();

        // Obtiene las dimensiones de la pantalla
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Calcula el ancho y alto deseado para la ventana
        double windowWidth = screenWidth;
        double windowHeight = screenHeight;

        // Carga la escena inicial
        scene = new Scene(loadFXML("login"), windowWidth, windowHeight);

        // Asigna la escena al escenario principal
        stage.setScene(scene);

        // Establece el título de la ventana
        stage.setTitle("Sistema de Ventas");

        // Muestra la ventana con el tamaño máximo
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/example/sistemadeventas/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
