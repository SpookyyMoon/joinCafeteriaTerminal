package com.example.cafeteriahilosinterfaz;

import java.util.List;

public class Camarero extends Thread {
    String nombreCamarero;
    List<Cliente> listaClientes;
    HelloController controller;
    Buffer buffer;

    public Camarero(String nombreCamarero, List<Cliente> listaClientes, HelloController controller, Buffer buffer) {
        this.nombreCamarero = nombreCamarero;
        this.listaClientes = listaClientes;
        this.controller = controller;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) { // Bucle activo hasta que se interrumpa
            Cliente cliente;

            synchronized (listaClientes) {
                while (listaClientes.isEmpty()) { // Si no hay clientes el camarero está en espera
                    try {
                        listaClientes.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                cliente = listaClientes.remove(0); // Borra el cliente atendido de la lista
            }

            servirCafe(cliente);
        }

        System.out.println(nombreCamarero + " ha terminado su turno.");
    }

    private void servirCafe(Cliente cliente) {
        System.out.println("El camarero " + nombreCamarero + " va a servir el café del cliente  " + cliente.nombreCliente);
        controller.actualizarEstadoCliente(cliente.nombreCliente, "atendido");
        buffer.reduce(); // Consume un café del buffer
        System.out.println("El camarero " + nombreCamarero + " ha servido el café del cliente " + cliente.nombreCliente);
        cliente.atendido = true;
        controller.actualizarEstadoCliente(cliente.nombreCliente, "salidoContento");
    }
}