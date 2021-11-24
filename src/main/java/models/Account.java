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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){ this.name = name;}

    public String getName(){ return this.name;}

    public double getBalance() {
        return balance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setClientId(int id){this.clientId = id;}

    public int getClientId(){return this.clientId;}

    public void adjustBalance(double amount){
        this.balance += amount;
    }
}
