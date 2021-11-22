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
    boolean createAccount();
    List<Account> getAccounts();
    Account updateAccount(Account account);
    boolean deleteAccount(int accountid);
    Account withdraw(double amount);
    Account deposit(double amount);
    Account transferFunds(Account receivingAccount, double amount);
}
