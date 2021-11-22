package controllers;

import dao.ClientDao;
import io.javalin.http.Context;
import models.Client;
import service.ClientService;

import java.util.ArrayList;
import java.util.List;

//Contains endpoint logic
public class ClientController {
    public static ClientService clientService = new ClientService(new ClientDao());

    public static List<Client> clients = new ArrayList<>();

    public static void addClient(Context context) {
        Client client = new Client();

        //client.setId(clients.size() + 1);
        clients.add(client);
        System.out.println("New models.Client added.");
    }
}

