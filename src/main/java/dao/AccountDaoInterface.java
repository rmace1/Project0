package dao;

import models.Account;

import java.util.List;

public interface AccountDaoInterface {
    //create account
    //get all accounts
    //update account
    //delete account
    //withdraw/deposit amount into account
    //transfer funds z from current account into account y
    boolean createAccount(Account newAccount);
    List<Account> getAccounts(int clientId);
    List<Account> getAccounts(int clientId, double upperLimit, double lowerLimit);
    Account getAccount(int accountId);
    Account updateAccount(Account account);
    boolean deleteAccount(int accountid);
    Account withdrawFromAccount(Account account, double amount);
    Account depositToAccount(Account account, double amount);
}
