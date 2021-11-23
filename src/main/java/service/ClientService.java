package service;

import dao.ClientDao;
import models.Client;

import java.util.List;

//Contains business logic
//Connects to controller
public class ClientService {
    ClientDao clientDao;

    public ClientService(ClientDao dao){
        clientDao = dao;
    }

    public boolean addClient(Client client){
        return clientDao.createClient(client);
    }

    public List<Client> getClients(){
        return clientDao.getClients();

    }

    public Client getClient(int id){

        return clientDao.getClient(id);
    }

    public Client updateClient(Client updatedClient){

        return clientDao.updateClient(updatedClient);
    }

    public boolean deleteClient(int id){

        return clientDao.deleteClient(id);
    }
}
