package org.example;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ClientReader clientReader;
    private ClientWriter clientWriter;
    int role; // 0 - no rights, 1 - read only, 2 - full control

    public Client(Socket socket) {
        this.socket = socket;
        try {
            clientReader = new ClientReader(socket.getInputStream(),this);
            clientWriter = new ClientWriter(socket.getOutputStream(),this);
            waitForCommand();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ClientRequests implements Runnable {
        @Override
        public void run() {
            role = Integer.parseInt(clientReader.WaitForInput());
            clientWriter.SayHello();
            for(;;) {
                int command = Integer.parseInt(clientReader.WaitForInput());
                if (command == 4) { //закрытие связи
                    ClientsConnection.closeClient(Client.this);
                    break;
                } else if (role > 0 && command == 1) { // запрос дела

                } else if (role > 1) {
                    if (command == 2) { //изменение дела

                    }
                    if (command == 3) { //создание нового дела

                    }
                } else clientWriter.WriteMessageForClient("Ваши права доступа не позволяют выполнить данную задачу.");
            }
        }
    }

    private void waitForCommand() {
        Thread thread = new Thread(new ClientRequests());
        thread.start();
    }

    void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
