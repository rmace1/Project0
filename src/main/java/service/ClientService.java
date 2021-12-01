package service;

import dao.AccountDao;
import dao.AccountDaoInterface;
import dao.ClientDao;
import models.Account;
import models.Client;

import java.util.List;

//Contains business logic
//Connects to controller
public class ClientService {
    ClientDao clientDao;
    AccountDao accountDao;

    public ClientService(ClientDao clientDao, AccountDao accountDao){
        this.clientDao = clientDao;
        this.accountDao = accountDao;
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
        List<Account> accounts = accountDao.getAccounts(id);
        if(accounts.size() > 0){
            for(Account account : accounts){
                accountDao.deleteAccount(account.getId());
            }
        }
        return clientDao.deleteClient(id);
    }
}
