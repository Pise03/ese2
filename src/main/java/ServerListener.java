import java.net.*;
import java.util.Vector;

public class ServerListener extends Thread {
    //creato vettore dove mi salvo tutti i socket dei client
    Vector <Socket> lista = new Vector<Socket>();

    // costruttore
    public ServerListener() {
    }

    //runnable dove vengono chiusi tutti i client dentro il vector
    public void run() {
        try {
            for (Socket client : lista) { //faccio un foreach dove vado a chiudere tutti i socket dei client
                client.close();
                System.out.println("Eliminato Socket: " + client);
            }
        } catch (Exception e) {
            //in caso di errore
            System.out.println("Nessun elemento dentro il vettore, istanza prima un client.");
        }
        
    }
 //aggiungo un client al vettore lista
    public void addClient(Socket x){
        lista.add(x);
    }

}
