package org.example.server;

import org.example.Account;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private static Socket socket;
    private ServerService serverService;
    public ServerConnection(String localhost, int port, Account account) {
        try {
            this.socket = new Socket(localhost,port);
            this.serverService = new ServerService(socket,account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Соединение с сервером разорвано!");
    }



}
