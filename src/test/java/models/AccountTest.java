package models;

import models.Account;
import models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account account1;

    @BeforeEach
    void setup(){
    account1 = new Account();
    }

    @Test
    void getId() {
        assertEquals(account1.getId(), 0);
    }

    @Test
    void setId() {
        account1.setId(2);

        assertEquals(account1.getId(), 2);
    }

    @Test
    void getBalance() {
        assertEquals(account1.getBalance(), 0);
    }

    @Test
    void getEmptyCategory() {
        assertEquals(account1.getCategory(), null);
    }

    @Test
    void setCategory() {
        account1.setCategory(Category.PERSONAL);

        assertEquals(account1.getCategory(), Category.PERSONAL);
    }

    @Test
    void depositValidAmount() {
        assertEquals(account1.getBalance(), 0);

        account1.deposit(100.59);

        assertEquals(account1.getBalance(), 100.59);

    }

    @Test
    void depositInvalidAmount() {
    }//account1.deposit(-100);


    @Test
    void withdrawValidAmount() {
        account1.setBalance(100.00);

        account1.withdraw(50.0);

        assertEquals(account1.getBalance(), 50.0);
    }

    @Test
    void withdrawInvalidAmount() {
        //account1.withdraw(100000);

    }
}