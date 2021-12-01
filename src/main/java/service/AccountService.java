package service;

import dao.AccountDao;
import dao.AccountDaoInterface;
import dao.ClientDao;
import dao.ClientDaoInterface;
import models.Account;
import models.Category;
import models.Client;

import java.util.List;

//connects to controller
public class AccountService {
    AccountDaoInterface accountDao;
    ClientDaoInterface clientDao;
    public AccountService(AccountDaoInterface accountDao, ClientDaoInterface clientDao){
        this.accountDao = accountDao;
        this.clientDao = clientDao;
    }

    public boolean createAccount(Account newAccount){
        Client client = clientDao.getClient(newAccount.getClientId());
        if(client != null) {
            return accountDao.createAccount(newAccount);
        }else{
            return false;
        }

    }

    public List<Account> getAccounts(int clientId, double upperLimit, double lowerLimit){
        Client client = clientDao.getClient(clientId);
        if(client == null){return null;}
        if(upperLimit < 0 && lowerLimit < 0) {
            return accountDao.getAccounts(clientId);
        }else{
            return accountDao.getAccounts(clientId, upperLimit, lowerLimit);
        }
    }

    public Account getAccount(int accountId){
        Account account = accountDao.getAccount(accountId);
        return account;
    }

    public Account updateAccount(Account account){
        Client client = clientDao.getClient(account.getClientId());
        Account account1 = getAccount(account.getId());
        if(client == null || account1 == null || account.getClientId() != account1.getClientId()){return null;}
        return accountDao.updateAccount(account);
    }

    public Account updateAccountPartial(int accountId, String name, double balance, Category category, int clientId){
        Account account = getAccount(accountId);

        if(name != null){account.setName(name);}
        if(balance >= 0.00){account.setBalance(balance);}
        if(!category.equals(Category.NONE)){account.setCategory(category);}
        if(clientId != 0){account.setClientId(clientId);}

        return updateAccount(account);
    }

    public boolean deleteAccount(int accountId){
        return accountDao.deleteAccount(accountId);
    }

    public Account withdrawFromBalance(int accountId, double amount){
        Account account = getAccount(accountId);
        if(account.getBalance() < amount){
            return new Account(0, "Insufficient Funds.", 0.00, Category.PERSONAL, 0);
        }else {
            return accountDao.withdrawFromAccount(account, amount);
        }
    }

    public Account depositToBalance(int accountId, double amount){
        Account account = getAccount(accountId);
        if(account != null) {
            return accountDao.depositToAccount(account, amount);
        }else{
            return null;
        }
    }


    public Account transferFunds(int sendingAccountId, int receivingAccountId, double amount) {
        Account sendingAccount = getAccount(sendingAccountId);
        Account receivingAccount = getAccount(receivingAccountId);
        if(sendingAccount == null || receivingAccount == null){
            return new Account("Account doesn't exist.",0.0, Category.PERSONAL, 0);
        }else {
            sendingAccount = withdrawFromBalance(sendingAccountId, amount);
            depositToBalance(receivingAccountId, amount);
        }
        return sendingAccount;
    }
}
