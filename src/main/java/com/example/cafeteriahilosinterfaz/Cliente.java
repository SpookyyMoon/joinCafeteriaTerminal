package com.example.cafeteriahilosinterfaz;

// Documentación empleada para el uso de notify, wait y synchronized: https://www.geeksforgeeks.org/java/java-notify-method-in-threads-synchronization-with-examples/

import java.util.List;

public class Cliente extends Thread {
    String nombreCliente;
    int tiempoEspera;
    boolean atendido = false;
    List<Cliente> listaClientes;
    HelloController controller;

    public Cliente(String nombreCliente, int tiempoEspera, List<Cliente> listaClientes, HelloController controller) {
        this.nombreCliente = nombreCliente;
        this.tiempoEspera = tiempoEspera;
        this.listaClientes = listaClientes;
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("El cliente " + nombreCliente + " ha llegado a la cafetería!");
        controller.actualizarEstadoCliente(nombreCliente, "espera");

        synchronized (listaClientes) { // Sincroniza la lista entre todos los hilos
            listaClientes.add(this); // Se coloca al final de la cola
            listaClientes.notifyAll(); // Notifica a los camareros en espera
        }

        try {
            Thread.sleep(tiempoEspera);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!atendido) {
            System.out.println("El cliente " + nombreCliente + " se ha marchado sin ser atendido, dejará una mala reseña!");
            controller.actualizarEstadoCliente(nombreCliente, "salidoEnfadado");
            synchronized (listaClientes) { // Elimina al cliente que se ha marchado de la lista
                listaClientes.remove(this);
            }
        }
    }
}