package com.example.joincafeteria;

public class Cliente extends Thread{

    String nombreCliente;
    int tiempoEspera;
    boolean atendidoCliente;

    public Cliente(String nombreCliente, int tiempoEspera, boolean atendidoCliente) {
        this.nombreCliente = nombreCliente;
        this.tiempoEspera = tiempoEspera;
        this.atendidoCliente = atendidoCliente;
    }

    @Override
    public void run() {
        super.run();
    }
}