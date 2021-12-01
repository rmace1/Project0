package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AccountDao;
import dao.ClientDao;
import io.javalin.http.Context;
import models.Client;
import service.ClientService;

import java.util.ArrayList;
import java.util.List;

//Contains endpoint logic
//Connects to dispatcher
public class ClientController {
    public static ClientService clientService = new ClientService(new ClientDao(), new AccountDao());

    public static List<Client> clients = new ArrayList<>();

    public static String convertToJson(Object obj){
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    public static void addClient(Context context) {
        //Using form data to receive new info for creation
        String fName = context.formParam("firstname");
        String lName = context.formParam("lastname");
        Client client = new Client(fName, lName);
        context.contentType("application/json");
        context.status(201);
        context.result(convertToJson(clientService.addClient(client)));
    }

    public static void getClients(Context context) {
        context.contentType("application/json");
        context.status(200);
        context.result(convertToJson(clientService.getClients()));
    }

    public static void getAClient(Context context) {
        Integer id = Integer.parseInt(context.pathParam("id"));

        context.contentType("application/json");
        Client client = clientService.getClient(id);
        if(client == null){
            context.status(404);
        }else{
            context.result(convertToJson(client));
        }

    }

    public static void updateClient(Context context) {
        //Using form data to receive new info for updatation
        Integer id = Integer.parseInt(context.pathParam("id"));
        String fName = context.formParam("firstname");
        String lName = context.formParam("lastname");
        Client client = new Client(id, fName, lName);
        //return the updated client to the result
        context.contentType("application/json");
        Client updatedClient = clientService.updateClient(client);
        if(updatedClient == null) {
            context.status(404);
        }else
        {
            context.result(convertToJson(updatedClient));
        }
    }

    public static void deleteClient(Context context) {
        Integer id = Integer.parseInt(context.pathParam("id"));
        context.contentType("application/json");
        boolean deleteSuccessful = clientService.deleteClient(id);
        if(deleteSuccessful){
            context.status(205);
        }else
        {
            context.status(404);
        }

    }


}

