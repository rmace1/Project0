package service;

import dao.AccountDao;
import models.Account;
import models.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sun.nio.cs.ext.MacCroatian;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    AccountDao accountDao = Mockito.mock(AccountDao.class);
    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAccount() {
        Account account = new Account("Savings", 100.00, Category.PERSONAL, 1);
        Mockito.when(accountDao.createAccount(account)).thenReturn(true);

        assertTrue(accountService.createAccount(account));
    }

    @Test
    void getAccounts() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);

        Mockito.when(accountService.getAccounts(1,0,0)).thenReturn(accountList);

        List<Account> actualResult = accountService.getAccounts(1,0,0);

        assertEquals(accountList.toString(), actualResult.toString());
    }

    @Test
    void getAccount() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);

        Mockito.when(accountService.getAccount(accountList.get(0).getId())).thenReturn(accountList.get(0));

        Account actualResult = accountService.getAccount(accountList.get(0).getId());

        assertEquals(accountList.get(0).toString(), actualResult.toString());
    }

    @Test
    void updateAccount() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);
        Account updatedAccount = accountList.get(1);
        updatedAccount.setName("College Fund");
        Mockito.when(accountService.updateAccount(updatedAccount)).thenReturn(updatedAccount);

        Account actualResult = accountService.updateAccount(updatedAccount);

        assertEquals(updatedAccount.toString(), actualResult.toString());
    }

    @Test
    void deleteAccount() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);

        Mockito.when(accountService.deleteAccount(accountList.get(0).getId())).thenReturn(true);

        assertTrue(accountService.deleteAccount(accountList.get(0).getId()));
    }

    @Test
    void transferFunds() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account("checking", 1000.00, Category.PERSONAL, 1));
        accountList.get(0).setId(1);
        accountList.get(1).setId(2);
        Account expectedResult = new Account("savings", 50.00, Category.PERSONAL, 1);

        Mockito.when(accountService.transferFunds(accountList.get(0).getId(), accountList.get(1).getId(), 50.0)).thenReturn(expectedResult);

        Account actualResult = accountService.transferFunds(accountList.get(0).getId(), accountList.get(1).getId(), 50.0);

        assertEquals(expectedResult.toString(), actualResult.toString());
    }
}