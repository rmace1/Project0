package models;

/**
 * An account should be able to have funds deposited, withdrawn, and transferred.
 */
public class Account {
    private int id;
    private double balance;
    private Category category;

    public Account(){this.balance = 0;}

    public Account(double startingBalance){
        this.balance = startingBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public void withdraw(double amount){
        this.balance -= amount;
    }

    public void transfer(Account receivingAccount, double amountToTransfer){
        //balance validation
        this.balance -= amountToTransfer;
        receivingAccount.balance += amountToTransfer;
    }
}
