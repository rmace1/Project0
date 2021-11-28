package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    Client client1;
    @BeforeEach
    void setUp() {
        client1 = new Client(1, "Robert", "Saggett");

    }

    @Test
    void getId() {
        assertEquals(client1.getId(), 1);
    }

    @Test
    void getFirstName() {
        assertEquals(client1.getFirstName(), "Robert");
    }

    @Test
    void setFirstName() {
        client1.setFirstName("Bob");

        assertEquals(client1.getFirstName(), "Bob");
    }

    @Test
    void getLastName() {
        assertEquals(client1.getLastName(), "Saggett");
    }

    @Test
    void setLastName() {
        client1.setLastName("Sagett");

        assertEquals(client1.getLastName(), "Sagett");
    }

    @Test
    void testToString() {
        String expectedClientToString = "Robert Saggett with id: 1";

        assertEquals(expectedClientToString, client1.toString());
    }
}