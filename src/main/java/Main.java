import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * CRUD operations for Clients and Accounts
 * 1 Client can have many Accounts
 * Clients can deposit/withdraw money from an account
 * Funds can be transferred between accounts
 */
public class Main {
    public static void main(String[] args) {
        //Javalin server = Javalin.create(config -> config.addStaticFiles("/", Location.CLASSPATH)).start(9000);
        Server srvr = new Server(9000);
        /*server.get("/backdoor", context -> {
            context.result("Sneaky backdoor");
            System.out.println("Sneaky backdoor found.");
        });*/

    }
}
