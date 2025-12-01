package com.example.cafeteriahilosinterfaz;

public class Barista extends Thread{
    private Buffer buffer;
    private HelloController controller;

    public Barista (Buffer buffer, HelloController controller) {
        this.buffer =  buffer;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                int tiempoPreparacion = (int) (Math.random() * 20000);
                Thread.sleep(tiempoPreparacion);
                System.out.println("El barista está preparando un café...");
                buffer.add();
                controller.actualizarEstadoBarista("Café preparado (" + buffer.get() + "/5)");
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
