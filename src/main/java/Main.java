import frontcontroller.FrontController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * CRUD operations for Clients and Accounts
 * 1 models.Client can have many Accounts
 * Clients can deposit/withdraw money from an account
 * Funds can be transferred between accounts
 */
public class Main {
    public static void main(String[] args) {
        Javalin server = Javalin.create().start(9000);
        new FrontController(server);


    }
}
