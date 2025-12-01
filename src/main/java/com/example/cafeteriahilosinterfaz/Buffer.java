package com.example.cafeteriahilosinterfaz;

public class Buffer {
    private int cantidadCafe = 0;
    private int cantidadCafeMaxima = 5;

    public synchronized void add() {
        while(cantidadCafe >= cantidadCafeMaxima) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cantidadCafe++;
        System.out.println("El barita ha preparado más cafe! Cantidad total: " + cantidadCafe + "/" + cantidadCafeMaxima);
        notifyAll();
    }

    public synchronized int get() {
        return cantidadCafe;
    }

    public synchronized void reduce() {
        while(cantidadCafe == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cantidadCafe--;
        System.out.println("Los camareros han servidor un café! Cantidad total: " + cantidadCafe + "/" + cantidadCafeMaxima);
        notifyAll();
    }
}
