package com.example.cafeteriahilosinterfaz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HelloController {

    // Declaración de listas
    @FXML private VBox clientesEspera;
    @FXML private VBox clientesAtendiendo;
    @FXML private VBox clientesTerminados;

    // Función principal (Main.java)
    @FXML
    public void inicioCafeteria() throws InterruptedException {
        new Thread(() -> {
            // Lista de clientes
            ArrayList<Cliente> listaClientes = new ArrayList<>();

            // Lista camareros
            ArrayList<Camarero> listaCamareros = new ArrayList<>();

            // Clientes
            Cliente clienteUno = new Cliente("John Doe", 9000, listaClientes, this);
            Cliente clienteDos = new Cliente("Jane Doe", 15000, listaClientes, this);
            Cliente clienteTres = new Cliente("Pepe Pérez", 12000, listaClientes, this);
            Cliente clienteCuatro = new Cliente("Pepa Pérez", 13000, listaClientes, this);
            Cliente clienteCinco = new Cliente("Luis Garcia", 14000, listaClientes, this);
            Cliente clienteSeis = new Cliente("María Lopez", 11000, listaClientes, this);
            Cliente clienteSiete = new Cliente("Carlos Ruiz", 10000, listaClientes, this);
            Cliente clienteOcho = new Cliente("Ana Gomez", 16000, listaClientes, this);

            // Camareros
            Camarero camareroUno = new Camarero("Camarero Uno", listaClientes, this);
            Camarero camareroDos = new Camarero("Camarero Dos", listaClientes, this);

            listaCamareros.add(camareroUno);
            listaCamareros.add(camareroDos);

            camareroUno.start();
            camareroDos.start();

            // Inicio de clientes
            Cliente[] clientes = {clienteUno, clienteDos, clienteTres, clienteCuatro, clienteCinco, clienteSeis, clienteSiete, clienteOcho};
            for (Cliente c : clientes) {
                c.start();
                try{ // Los clientes tardan entre 1 y 3 segundos en ir llegando desde el primero
                    int retrasoClientes = (int) (Math.random() * 3000) + 1000;
                    Thread.sleep(retrasoClientes);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // Join de clientes
            for (Cliente c : clientes) {
                try {
                    c.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // Interrumpe a los camareros para salir del bucle
            for (Camarero camarero : listaCamareros) {
                camarero.interrupt();
            }

            for (Camarero camarero : listaCamareros) {
                try {
                    camarero.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    // Actualización de listas interfaz
    public void actualizarEstadoCliente(String nombreCliente, String estado) {
        Platform.runLater(() -> {
            eliminarClienteLista(clientesEspera, nombreCliente);
            eliminarClienteLista(clientesAtendiendo, nombreCliente);
            eliminarClienteLista(clientesTerminados, nombreCliente);

            Label etiqueta = new Label(nombreCliente);
            etiqueta.setMaxWidth(Double.MAX_VALUE);
            etiqueta.setAlignment(Pos.CENTER);

            switch (estado) {
                case "espera" -> {
                    etiqueta.setStyle("-fx-font-size: 25px; -fx-font-family: Poppins; -fx-text-alignment: center");
                    clientesEspera.getChildren().add(etiqueta);
                }
                case "atendido" -> {
                    etiqueta.setStyle("-fx-font-size: 25px; -fx-font-family: Poppins; -fx-text-alignment: center");
                    clientesAtendiendo.getChildren().add(etiqueta);
                }
                case "salidoEnfadado" -> {
                    etiqueta.setStyle("-fx-text-fill: #b84d4d; -fx-font-size: 25px; -fx-font-family: Poppins; -fx-text-alignment: center");
                    clientesTerminados.getChildren().add(etiqueta);
                }
                case "salidoContento" -> {
                    etiqueta.setStyle("-fx-text-fill: #57ac57; -fx-font-size: 25px; -fx-font-family: Poppins; -fx-text-alignment: center");
                    clientesTerminados.getChildren().add(etiqueta);
                }
            }
        });
    }

    private void eliminarClienteLista(VBox vbox, String nombreCliente) {
        vbox.getChildren().removeIf(node -> {
            if (node instanceof Label label) {
                return label.getText().equals(nombreCliente);
            }
            return false;
        });
    }
}
