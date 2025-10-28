import java.util.List;

public class Camarero extends Thread {
    String nombreCamarero;
    List<Cliente> listaClientes;

    public Camarero(String nombreCamarero, List<Cliente> listaClientes) {
        this.nombreCamarero = nombreCamarero;
        this.listaClientes = listaClientes;
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

            prepararCafe(cliente);
        }

        System.out.println(nombreCamarero + " ha terminado su turno.");
    }

    private void prepararCafe(Cliente cliente) {
        int tiempoPreparacion = (int) (Math.random() * 20000);
        System.out.println(nombreCamarero + " está preparando el café de " + cliente.nombreCliente + ".");

        try {
            Thread.sleep(tiempoPreparacion);
        } catch (InterruptedException e) {
            return;
        }

        if (tiempoPreparacion <= cliente.tiempoEspera) {
            cliente.atendido = true;
            System.out.println(nombreCamarero + " ha servido el café a " + cliente.nombreCliente + ".");
        } else {
            System.out.println(nombreCamarero + " ha terminado el café, pero " + cliente.nombreCliente + " ya se había ido.");
        }
    }
}
