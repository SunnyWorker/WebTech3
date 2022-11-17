package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ClientWriter {
    private OutputStreamWriter out;
    private Client client;
    public ClientWriter(OutputStream out, Client client) {
        this.out = new OutputStreamWriter(out);
        this.client = client;
    }

    void SayHello() {
        String hello = "";
        switch (client.role) {
            case 0:
                hello = "Добро пожаловать, гость!";
                break;
            case 1:
                hello = "Добрый день, пользователь.";
                break;
            case 2:
                hello = "Glad to see you, administrator...";
                break;
        }
        try {
            out.write(hello);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void WriteMessageForClient(String message) {
        try {
            out.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
