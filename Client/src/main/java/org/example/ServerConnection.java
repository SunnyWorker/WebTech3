package org.example;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private static Socket socket;
    private Server server;
    public ServerConnection(String localhost, int port, Account account) {
        try {
            this.socket = new Socket(localhost,port);
            this.server = new Server(socket,account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Соединение с сервером разорвано!");
    }



}
