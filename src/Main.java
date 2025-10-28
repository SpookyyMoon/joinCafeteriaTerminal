import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Lista de clientes
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        // Lista camareros
        ArrayList<Camarero> listaCamareros = new ArrayList<>();

        // Clientes
        Cliente clienteUno = new Cliente("John Doe", 9000, listaClientes);
        Cliente clienteDos = new Cliente("Jane Doe", 15000, listaClientes);
        Cliente clienteTres = new Cliente("Pepe Pérez", 12000, listaClientes);
        Cliente clienteCuatro = new Cliente("Pepa Pérez", 13000, listaClientes);
        Cliente clienteCinco = new Cliente("Luis Garcia", 14000, listaClientes);
        Cliente clienteSeis = new Cliente("María Lopez", 11000, listaClientes);
        Cliente clienteSiete = new Cliente("Carlos Ruiz", 10000, listaClientes);
        Cliente clienteOcho = new Cliente("Ana Gomez", 16000, listaClientes);

        // Camareros
        Camarero camareroUno = new Camarero("Camarero Uno", listaClientes);
        Camarero camareroDos = new Camarero("Camarero Dos", listaClientes);

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
            c.join();
        }

        // Interrumpe a los camareros para salir del bucle
        for (Camarero camarero : listaCamareros) {
            camarero.interrupt();
        }

        for (Camarero camarero : listaCamareros) {
            camarero.join();
        }
    }
}
