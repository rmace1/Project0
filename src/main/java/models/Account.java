package models;

/**
 * An account should be able to have funds deposited, withdrawn, and transferred.
 */
public class Account {
    private int id;
    private String name;
    private double balance;
    private Category category;
    private int clientId;

    public Account(){this.balance = 0;}

    public Account(String name, double startingBalance, Category category, int clientId){
        this.name = name;
        this.balance = startingBalance;
        this.category = category;
        this.clientId = clientId;
    }

    public Account(int id, String name, double startingBalance, Category category, int clientId){
        this.id = id;
        this.name = name;
        this.balance = startingBalance;
        this.category = category;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){ this.name = name;}

    public String getName(){ return this.name;}

    public void setBalance(double amount){balance = amount;}

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount){balance -= amount;}

    public void deposit(double amount){balance += amount;}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setClientId(int id){this.clientId = id;}

    public int getClientId(){return this.clientId;}

    @Override
    public String toString() {
        return "Account number: " + id + " Name: " + name + " has $" + balance + " funds and is categorized as " + category.toString();
    }
}
