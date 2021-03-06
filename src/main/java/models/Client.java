package models;

/**
 * Contains a collection of accounts
 * Modeled as the patron of the bank and not the employee managing the accounts
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    //private ArrayList<models.Account> accounts = new ArrayList<models.Account>();

    public Client(){}

    public Client(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getId() {return id;}

    //public void setId(int id) {this.id = id;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    @Override
    public String toString() {
        return firstName + " " + lastName + " with id: " + id;
    }
}
