import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    ServerListener listening = new ServerListener();

    public ServerThread(Socket socket, ServerSocket server, ServerListener listen) {
        this.client = socket;
        this.server = server;
        this.listening = listen;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception {

        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        listening.addClient(client);

        for (;;) {
            stringaRicevuta = inDalClient.readLine();
            if (stringaRicevuta == null || stringaRicevuta.equals("FINE")) {
                outVersoClient.writeBytes(stringaRicevuta + "(=>server in chiusura ...)" + '\n');
                System.out.println("6 Echo sul server in chiusura :" + stringaRicevuta);
                break;
            } else if(stringaRicevuta.equals("STOP")) { //controllo sulla stringa STOP
                outVersoClient.writeBytes(stringaRicevuta + "(=>server in chiusura ...)" + '\n');
                System.out.println("6 Echo sul server in chiusura :" + stringaRicevuta);
                //parte il thread che spegne tutti i client
                listening.start();
                break;
            } else {
                outVersoClient.writeBytes(stringaRicevuta + " (ricevuta e ritrasmessa)" + '\n');
                System.out.println("6 Echo sul server :" + stringaRicevuta);
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 chiusura socket" + client);
        client.close();

        if (stringaRicevuta.equals("STOP")) {
            System.out.println("10 chiusura SERVER: " +  server);
            server.close();
        }

    }
}
