package service;

import dao.AccountDao;
import dao.ClientDao;
import models.Account;
import models.Category;
import models.Client;
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
    ClientDao clientDao = Mockito.mock(ClientDao.class);
    AccountService accountService = Mockito.mock(AccountService.class);

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountDao, clientDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAccount() {
        Account account = new Account("Savings", 100.00, Category.PERSONAL, 1);
        Mockito.when(clientDao.getClient(1)).thenReturn(new Client(1, "bob", "Saggs"));
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

        Client bob = new Client(1, "bob", "saggs");

        Mockito.when(clientDao.getClient(1)).thenReturn(bob);
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
        Mockito.when(clientDao.getClient(updatedAccount.getClientId())).thenReturn(new Client(1, "bob", "saggs"));
        Mockito.when(accountService.getAccount(updatedAccount.getId())).thenReturn(updatedAccount);
        Mockito.when(accountDao.updateAccount(updatedAccount)).thenReturn(updatedAccount);

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

    //TODO: Needs separated into separate tests
    @Test
    void updateAccountPartial(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, "savings", 100.0, Category.PERSONAL, 1));
        accountList.add(new Account(2, "checking", 1000.00, Category.PERSONAL, 1));
        accountList.add(new Account(3, "college", 2500.0, Category.PERSONAL, 1));
        accountList.add(new Account(4, "car shop", 10000.00, Category.PERSONAL, 1));

        Account updatedAccountName = accountList.get(0);
        updatedAccountName.setName("College Fund");
        Account updatedAccountBalance = accountList.get(1);
        updatedAccountBalance.setBalance(1500.00);
        Account updatedAccountCategory = accountList.get(2);
        updatedAccountCategory.setCategory(Category.BUSINESS);
        Account updatedAccountClient = accountList.get(3);
        updatedAccountClient.setClientId(2);

        Mockito.when(accountService.getAccount(updatedAccountName.getId())).thenReturn(updatedAccountName);
        Mockito.when(accountService.getAccount(updatedAccountBalance.getId())).thenReturn(updatedAccountBalance);
        Mockito.when(accountService.getAccount(updatedAccountCategory.getId())).thenReturn(updatedAccountCategory);

        Mockito.when(clientDao.getClient(updatedAccountName.getClientId())).thenReturn(new Client(1, "bob", "Saggs"));
        Mockito.when(clientDao.getClient(updatedAccountBalance.getClientId())).thenReturn(new Client(1, "bob", "Saggs"));
        Mockito.when(clientDao.getClient(updatedAccountCategory.getClientId())).thenReturn(new Client(1, "bob", "Saggs"));

        Mockito.when(accountDao.updateAccount(updatedAccountName)).thenReturn(updatedAccountName);
        Mockito.when(accountDao.updateAccount(updatedAccountBalance)).thenReturn(updatedAccountBalance);
        Mockito.when(accountDao.updateAccount(updatedAccountCategory)).thenReturn(updatedAccountCategory);

        Account actualResultName = accountService.updateAccountPartial(updatedAccountName.getId(), "College Fund", -1.00, Category.NONE, 0);
        Account actualResultBalance = accountService.updateAccountPartial(updatedAccountBalance.getId(), "", 1500.00, Category.NONE, 0);
        Account actualResultCategory = accountService.updateAccountPartial(updatedAccountCategory.getId(), "", -1.00, Category.BUSINESS, 0);

        assertEquals(updatedAccountName.toString(), actualResultName.toString());
        assertEquals(updatedAccountBalance.toString(), actualResultBalance.toString());
        assertEquals(updatedAccountCategory.toString(), actualResultCategory.toString());
    }
}