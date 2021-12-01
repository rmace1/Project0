package service;

import dao.AccountDao;
import dao.AccountDaoInterface;
import dao.ClientDao;
import models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    ClientDao clientDao = Mockito.mock(ClientDao.class);
    AccountDao accountDao = Mockito.mock(AccountDao.class);
    ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientDao, accountDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addClient() {
        Client client = new Client(1, "Bob", "Sagget");
        Mockito.when(clientDao.createClient(client)).thenReturn(true);

        Boolean actualValue = clientService.addClient(client);

        assertTrue(actualValue);
    }

    @Test
    void getClients() {
        //arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1,"Bob", "Sagget"));
        clients.add(new Client(2, "Sandra", "Bullion"));
        List<Client> expectedValue = clients;

        //Mockito when/thens
        Mockito.when(clientDao.getClients()).thenReturn(clients);

        //act
        List<Client> actualValue = clientService.getClients();

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getClient() {
        //arrange
        Client expectedValue = new Client(1, "Bob", "Sagget");
        Mockito.when(clientDao.getClient(expectedValue.getId())).thenReturn(expectedValue);

        //act
        Client actualValue = clientService.getClient(expectedValue.getId());

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void updateClient() {
        Client expectedResult = new Client(1, "Robert", "Sagett");
        Mockito.when(clientDao.updateClient(expectedResult)).thenReturn(expectedResult);

        //assert
        Client actualResult = clientDao.updateClient(expectedResult);
        //Used to verify a method runs, and can specify the number of times it should have ran
        Mockito.verify(clientDao, Mockito.times(1)).updateClient(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteClient() {
        int clientId = 1;
        Mockito.when(clientDao.deleteClient(clientId)).thenReturn(true);

        boolean actualResult = clientService.deleteClient(clientId);

        Mockito.verify(clientDao, Mockito.times(1)).deleteClient(clientId);
        assertTrue(actualResult);
    }
}