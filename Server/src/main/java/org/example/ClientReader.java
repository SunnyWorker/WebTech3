package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class ClientReader {
    private InputStreamReader in;
    private Client client;
    public ClientReader(InputStream in, Client client) {
        this.in = new InputStreamReader(in);
        this.client = client;
    }
    String WaitForInput() {
        char[] buffer = new char[255];
        StringBuilder answer;
        answer = new StringBuilder();
        try {
            while(in.read(buffer)!=-1) {
                answer.append(Arrays.toString(buffer));
            }
        }
        catch (IOException ex) {}
        return answer.toString();
    }

}
