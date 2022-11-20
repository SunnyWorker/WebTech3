package org.example.client.io;

import org.webtech.io.SocketWriter;
import org.webtech.jaxb.StudentJAXB;

import java.io.OutputStream;

public class ClientWriter extends SocketWriter {
    public ClientWriter(OutputStream out, StudentJAXB studentJAXB) {
        super(out, studentJAXB);
    }

    public void sayHello(int role) {
        String hello = "";
        switch (role) {
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
        sendMessage(hello);
    }
}
