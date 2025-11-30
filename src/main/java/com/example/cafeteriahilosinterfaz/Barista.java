package com.example.cafeteriahilosinterfaz;

import static java.lang.Thread.sleep;

public class Barista {
    private Buffer buffer;

    public Barista (Buffer buffer) {
        this.buffer =  buffer;
    }

    @Override
    public void run() throws InterruptedException {
        int tiempoPreparacion = (int) (Math.random() * 20000);
        sleep(tiempoPreparacion);
        System.out.println("El barista está preparando un café...");
        buffer.add();
    }
}
