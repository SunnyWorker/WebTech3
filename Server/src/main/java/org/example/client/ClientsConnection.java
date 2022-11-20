package org.example.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ClientsConnection {
    private static List<ClientService> clientServices;
    private static ServerSocket serverSocket;

    private class ClientsConnectionAwaiting implements Runnable {
        @Override
        public void run() {
            Socket clientSocket;
            while(!Thread.interrupted()) {
                try {
                    clientSocket = serverSocket.accept(); // wait for clients to connect
                } catch (IOException e) {
                    clientSocket = null;
                    continue;
                }

                if(clientSocket!=null) {
                    synchronized (clientServices) {
                        clientServices.add(new ClientService(clientSocket)); //danger
                    }
                    System.out.println("Клиент подключен!");
                }
            }
        }
    }

    public static void closeConnection(ClientService clientService) {
        clientService.closeSocket();
        synchronized (clientServices) {
            clientServices.remove(clientService); //danger
        }
    }

    public ClientsConnection(int port) {
        this.clientServices = new LinkedList<>();
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен. Порт для подключения - "+port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        waitForClients();
    }

    private void waitForClients() {
        Thread thread = new Thread(new ClientsConnectionAwaiting());
        thread.start();
    }

}
