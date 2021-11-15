/**
 * Contains a collection of accounts
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    //private ArrayList<Account> accounts = new ArrayList<Account>();

    public Client(){}

    public Client(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    @Override
    public String toString() {
        return firstName + " " + lastName + " with id: " + id;
    }
}
