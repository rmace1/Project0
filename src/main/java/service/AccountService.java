package service;

import dao.AccountDao;
import models.Account;

import java.util.List;

//connects to controller
public class AccountService {
    AccountDao accountDao;

    public AccountService(AccountDao accountDao){ this.accountDao = accountDao;}

    public boolean createAccount(Account newAccount){
        return accountDao.createAccount(newAccount);
    }

    public List<Account> getAccounts(int clientId, double upperLimit, double lowerLimit){
        return accountDao.getAccounts(clientId, upperLimit, lowerLimit);
    }

    public Account getAccount(Account account){
        return accountDao.getAccount(account);
    }

    public Account updateAccount(Account account){
        return accountDao.updateAccount(account);
    }

    public boolean deleteAccount(int accountId){
        return accountDao.deleteAccount(accountId);
    }

    public Account adjustBalance(int accountId, double amount){
        return accountDao.adjustBalance(accountId, amount);
    }

    public Account transferFunds(int sendingAccountId, int receivingAccountId, double amount) {
        return accountDao.transferFunds(sendingAccountId, receivingAccountId, amount);
    }
}
