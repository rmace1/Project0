package service;

import dao.ClientDao;
import models.Client;

import java.util.List;

public class ClientService {
    ClientDao clientDao;

    public ClientService(ClientDao dao){
        clientDao = dao;
    }

    public List<Client> getClients(){
        //bankDao.getClients();
        return null;
    }

    public Client getClient(int id){

        return null;
    }
}
