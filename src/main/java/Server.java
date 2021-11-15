import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;

/**
 * CRUD operations for Clients and Accounts
 * 1 Client can have many Accounts
 * Clients can deposit/withdraw money from an account
 * Funds can be transferred between accounts
 */
public class Server {
    Javalin server;
    ArrayList<Client> clients = new ArrayList<Client>();
    public Server(int port){
        server = Javalin.create(config -> config.addStaticFiles("/", Location.CLASSPATH)).start(port);
        serverRun();
    }

    public void serverRun(){

        //adds a basic client to the client list
        server.get("/addClient", context -> {
            Client client = new Client();
            client.setId(clients.size() + 1);
            clients.add(client);
            System.out.println("New Client added.");
        });

        //returns a list of all clients
        server.get("/clients", context -> {
           context.result(clients.toString());
        });

        //return the client id listed in the path
        server.get("/clients/{clientId}", context -> {
            Integer clientId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
            } catch (Exception ex)
            {
                clientId = 0;
            }
            if(clientId == 0){
                context.result(clients.toString());
            }
            else {
                context.result("the client id is: " + clientId.toString());
            }
        });

        //update client
        server.put("/clients/{clientId}", context -> {

        });

        //delete specified client
        server.delete("/delete/{clientId}", context -> {

        });

        //create new account for a client
        server.post("/clients/{clientId}/accounts/{accountId}", context -> {

        });

        //get all accounts for a client
        server.get("/clients//{clientId}/accounts", context -> {

        });

        //get list of filtered accounts for a client
        server.get("/clients//{clientId}/accounts", context -> {
            Integer amountLessThan = Integer.parseInt(context.queryParam("amountLessThan"));
            Integer amountGreaterThan = Integer.parseInt(context.queryParam("amountGreaterThan"));
            //String filterBy = context.queryParam("filterBy");
        });

        //get an account for a specific client
        server.get("/clients/{clientId}/accounts/{accountId}", context -> {

        });

        //update an account from a specific client
        server.put("/clients/{clientId}/accounts/{accountId}", context -> {

        });

        //delete an account from a specific client
        server.delete("/clients/{clientId}/accounts/{accountId}", context -> {

        });

        //withdraw or deposit a given amount
        server.patch("/clients/{clientId}/accounts/{accountId}", context -> {

        });

        //transfer funds from one account to another by the same client
        server.patch("/clients/{clientId}/accounts/{fromAccountId}/transfer/{toAccountId}", context -> {

        });


    }
}
