package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ClientsConnection {
    private static List<Client> clients;
    private static ServerSocket serverSocket;

    private class ClientsConnectionAwaiting implements Runnable {
        @Override
        public void run() {
            Socket clientSocket;
            while(!Thread.interrupted()) {
                try {
                    clientSocket = serverSocket.accept(); // wait for clients to connect
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if(clientSocket!=null) { //danger
                    clients.add(new Client(clientSocket));
                }
            }
        }
    }

    static void closeClient(Client client) {
        client.closeSocket();
        clients.remove(client); //danger
    }

    public ClientsConnection(int port) {
        this.clients = new LinkedList<>();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WaitForClients();
    }

    private void WaitForClients() {
        Thread thread = new Thread(new ClientsConnectionAwaiting());
        thread.start();
    }

}
