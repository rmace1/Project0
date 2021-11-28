package dao;

import models.Account;
import models.Category;
import models.Client;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.awt.geom.AreaOp;
import util.H2Util;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {
    AccountDao accountDao;
    ClientDao clientDao;

    @BeforeEach
    void setUp() {
        clientDao = new ClientDao(H2Util.url, H2Util.userName, H2Util.password);
        accountDao = new AccountDao(H2Util.url, H2Util.userName, H2Util.password);
        H2Util.createClientTable();
        H2Util.createAccountTable();
        Client client = new Client("Robert", "Saggett");
        clientDao.createClient(client);
    }

    @AfterEach
    void tearDown() {
        H2Util.dropAccountTable();
        H2Util.dropClientTable();
    }

    @Test
    void createAccount() {
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        expectedResult.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountDao.createAccount(expectedResult.get(0));
        accountDao.createAccount(expectedResult.get(1));

        int actualResult = accountDao.getAccounts(1,0,0).size();

        assertEquals(expectedResult.size(), actualResult);
    }

    @Test
    void getAccounts() {
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        expectedResult.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountDao.createAccount(expectedResult.get(0));
        accountDao.createAccount(expectedResult.get(1));

        int actualResult = accountDao.getAccounts(1,0,0).size();

        assertEquals(expectedResult.size(), actualResult);
    }

    @Test
    void getAccount() {
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        expectedResult.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        expectedResult.get(0).setId(1);
        expectedResult.get(1).setId(2);
        accountDao.createAccount(expectedResult.get(0));
        accountDao.createAccount(expectedResult.get(1));

        Account actualResult = accountDao.getAccount(expectedResult.get(0).getId());

        assertEquals(expectedResult.get(0).toString(), actualResult.toString());
    }

    @Test
    void updateAccount() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);
        accountDao.createAccount(accountList.get(0));
        accountDao.createAccount(accountList.get(1));
        Account expectedResult = new Account("college fund", 1000.00, Category.PERSONAL, 1);
        expectedResult.setId(1);

        Account actualResult = accountDao.updateAccount(expectedResult);

        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void deleteAccount() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);
        accountDao.createAccount(accountList.get(0));
        accountDao.createAccount(accountList.get(1));
        Account expectedResult = new Account("college fund", 1000.00, Category.PERSONAL, 1);
        expectedResult.setId(1);

        boolean actualResult = accountDao.deleteAccount(1);
        List<Account> actualList = accountDao.getAccounts(1,0,0);

        assertTrue(actualResult);
        assertEquals(accountList.size() - 1, actualList.size());
    }

    @Test
    void withdrawFromBalance() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1,"savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account(2,"checking", 1000.00, Category.PERSONAL, 1));
        accountDao.createAccount(accountList.get(0));
        accountDao.createAccount(accountList.get(1));
        Account expectedResult = new Account(2,"checking", 500.00, Category.PERSONAL, 1);

        Account actualResult = accountDao.withdrawFromAccount(2, 500);

        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void depositToBalance() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1,"savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account(2,"checking", 1000.00, Category.PERSONAL, 1));
        accountDao.createAccount(accountList.get(0));
        accountDao.createAccount(accountList.get(1));
        Account expectedResult = new Account(2,"checking", 500.00, Category.PERSONAL, 1);

        Account actualResult = accountDao.withdrawFromAccount(2, -500);

        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void transferFunds() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);
        accountDao.createAccount(accountList.get(0));
        accountDao.createAccount(accountList.get(1));
        Account expectedResult1 = new Account("checking", 500.00, Category.PERSONAL, 1);
        expectedResult1.setId(2);
        Account expectedResult2 = new Account("savings", 600.00, Category.PERSONAL, 1);
        expectedResult2.setId(1);

        Account actualResult2 = accountDao.transferFunds(2,1, 500.00);
        Account actualResult1 = accountDao.getAccount(accountList.get(0).getId());

        assertEquals(expectedResult2.toString(), actualResult1.toString());
        assertEquals(expectedResult1.toString(), actualResult2.toString());
    }
}