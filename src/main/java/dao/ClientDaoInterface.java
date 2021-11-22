package dao;

import models.Client;

import java.util.List;

public interface ClientDaoInterface {

    List<Client> getClients();
    Client getClient(int id);
    boolean createClient(Client newClient);
    boolean deleteClient(int id);
    Client updateClient(Client updateClient);
}
