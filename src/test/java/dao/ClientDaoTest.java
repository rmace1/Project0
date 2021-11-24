package dao;

import models.Client;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.H2Util;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Using H2 in memory database
class ClientDaoTest {
    ClientDao clientDao;

    @BeforeEach
    void setUp() {
        clientDao = new ClientDao(H2Util.url, H2Util.userName, H2Util.password);
        H2Util.createTable();
    }

    @AfterEach
    void tearDown() {
        H2Util.dropTable();
    }

    @Test
    void getClients() {
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Bob", "Saggett"));
        expectedResult.add(new Client(2, "Sherry", "Bobbins"));
        expectedResult.add(new Client(3, "Mary", "Kate"));
        clientDao.createClient(expectedResult.get(0));
        clientDao.createClient(expectedResult.get(1));
        clientDao.createClient(expectedResult.get(2));

        List<Client> actualResult = clientDao.getClients();

        assertEquals(expectedResult.toString(), actualResult.toString());

    }

    @Test
    void getClient() {
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Bob", "Saggett"));
        expectedResult.add(new Client(2, "Sherry", "Bobbins"));
        expectedResult.add(new Client(3, "Mary", "Kate"));
        clientDao.createClient(expectedResult.get(0));
        clientDao.createClient(expectedResult.get(1));
        clientDao.createClient(expectedResult.get(2));

        Client actualResult = clientDao.getClient(1);

        assertEquals(expectedResult.get(0).toString(), actualResult.toString());
    }

    @Test
    void createClient() {
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Bob", "Saggett"));
        expectedResult.add(new Client(2, "Sherry", "Bobbins"));
        expectedResult.add(new Client(3, "Mary", "Kate"));
        clientDao.createClient(expectedResult.get(0));
        clientDao.createClient(expectedResult.get(1));
        clientDao.createClient(expectedResult.get(2));

        int actualResult = clientDao.getClients().size();

        assertEquals(expectedResult.size(), actualResult);
    }

    @Test
    void deleteClient() {
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Bob", "Saggett"));
        expectedResult.add(new Client(2, "Sherry", "Bobbins"));
        expectedResult.add(new Client(3, "Mary", "Kate"));
        clientDao.createClient(expectedResult.get(0));
        clientDao.createClient(expectedResult.get(1));
        clientDao.createClient(expectedResult.get(2));

        clientDao.deleteClient(1);

        assertNull(clientDao.getClient(1));
    }

    @Test
    void updateClient() {
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Bob", "Saggett"));
        expectedResult.add(new Client(2, "Sherry", "Bobbins"));
        expectedResult.add(new Client(3, "Mary", "Kate"));
        clientDao.createClient(expectedResult.get(0));
        clientDao.createClient(expectedResult.get(1));
        clientDao.createClient(expectedResult.get(2));
        Client expectedClient = new Client(1, "Robert", "Saggett");

        Client actualResult = clientDao.updateClient(expectedClient);

        assertEquals(expectedClient.toString(), actualResult.toString());


    }
}