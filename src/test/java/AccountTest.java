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
        account1.deposit(100);

        assertEquals(account1.getBalance(), 100);
    }

    @Test
    void depositInvalidAmount() {
        account1.deposit(-100);
    }

    @Test
    void withdrawValidAmount() {
        Account account = new Account(200);
        account.withdraw(100);

        assertEquals(account.getBalance(), 100);
    }

    @Test
    void withdrawInvalidAmount() {
        account1.withdraw(100000);

    }

    @Test
    void transferValidAmount() {
        Account account2 = new Account(100);
        account1.deposit(100);
        account2.transfer(account1, 100);

        assertEquals(account2.getBalance(), 0);
        assertEquals(account1.getBalance(), 200);
    }

    @Test
    void transferInvalidAmount() {
        Account account2 = new Account(100);
        account1.deposit(100);

        account2.transfer(account1, 1000);
    }
}