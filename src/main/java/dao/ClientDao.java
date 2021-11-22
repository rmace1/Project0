package dao;

import models.Client;

import java.util.List;

public class ClientDao implements ClientDaoInterface {

    @Override
    public List<Client> getClients() {
        return null;
    }

    @Override
    public Client getClient(int id) {
        return null;
    }

    @Override
    public boolean createClient(Client newClient) {
        return false;
    }

    @Override
    public boolean deleteClient(int id) {
        return false;
    }

    @Override
    public Client updateClient(Client updateClient) {
        return null;
    }
}
