package controllers;

import io.javalin.http.Context;
import jdk.nashorn.internal.runtime.ListAdapter;
import models.Client;

import java.util.ArrayList;
import java.util.List;

public class BankController {
    public static List<Client> clients = new ArrayList<>();

    public static void addClient(Context context) {
        Client client = new Client();

        client.setId(clients.size() + 1);
        clients.add(client);
        System.out.println("New models.Client added.");
    }
}

