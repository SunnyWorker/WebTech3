package org.example;

import org.example.server.ServerConnection;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        account.setRole(2);
        ServerConnection serverConnection = new ServerConnection("localhost",25000,account);
    }
}