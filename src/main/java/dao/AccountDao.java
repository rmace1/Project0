package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Account;

import javax.annotation.processing.Filer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class AccountDao implements AccountDaoInterface {
    public AccountDao() {
    }

    @Override
    public boolean createAccount() {
        return false;
    }

    @Override
    public List<Account> getAccounts() {
        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        return null;
    }

    @Override
    public boolean deleteAccount(int accountid) {
        return false;
    }

    @Override
    public Account withdraw(double amount) {
        return null;
    }

    @Override
    public Account deposit(double amount) {
        return null;
    }

    @Override
    public Account transferFunds(Account receivingAccount, double amount) {
        return null;
    }
}
