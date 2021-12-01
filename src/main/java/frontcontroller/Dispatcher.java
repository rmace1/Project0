package frontcontroller;

import controllers.AccountController;
import controllers.ClientController;
import io.javalin.Javalin;
import models.Client;

import java.util.ArrayList;

import static io.javalin.apibuilder.ApiBuilder.*;

//contains endpoint methods
public class Dispatcher {
    ArrayList<Client> clients = new ArrayList<Client>();

    public Dispatcher(Javalin app){

        app.routes(() -> {
           path("/clients", () ->{
               get(ClientController::getClients); //get all clients
               post(ClientController::addClient); //create a new client

               path("/{id}", () -> {
                   get(ClientController::getAClient); //get a specific client
                   put(ClientController::updateClient); //updated a client's info
                   delete(ClientController::deleteClient); //delete a client
                   path("/accounts", () -> {
                       get(AccountController::getAccounts); //get all accounts a client owns; accepts query params for filtering
                       post(AccountController::addAccount); //create an account for a client
                       //path("/accounts?")
                       path("/{accountId}", () -> {
                           get(AccountController::getAccount); //gets a specific account
                           put(AccountController::updateAccount); //update the specified account
                           patch(AccountController::updatePartialAccount); //updates a limited number of fields on an account;  Also used for withdraws and deposits
                           delete(AccountController::deleteAccount); //delete the account
                           //patch(AccountController::adjustBalance); //deposit or withdraw money from an account
                           path("/transfer/{transferId}", () -> {
                               patch(AccountController::transferFunds); //transfer from current account to specified account
                           });
                       });
               });

               });
           });
        });

        /*//adds a basic client to the client list
        app.get("/addClient", ClientController::addClient);

        *//*context -> {
            Client client = new Client();
            client.setId(clients.size() + 1);
            clients.add(client);
            System.out.println("New models.Client added.");
        });*//*

        //returns a list of all clients
        app.get("/clients", ClientController::getClients);

        *//*        context -> {
            //context.result(clients.toString());
            System.out.println("teeheeheehee");
            context.result(clients.toString());
        });*//*

        //return the client id listed in the path
        app.get("/clients/{clientId}", context -> {
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
        app.put("/clients/{clientId}", context -> {
            Integer clientId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
            } catch (Exception ex)
            {
                clientId = 0;
            }

        });

        //delete specified client
        app.delete("/delete/{clientId}", context -> {
            Integer clientId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
            } catch (Exception ex)
            {
                clientId = 0;
            }

        });

        //create new account for a client
        app.post("/clients/{clientId}/accounts/{accountId}", context -> {
            Integer clientId;
            Integer accountId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
                accountId = Integer.parseInt(context.pathParam("accountId"));
            } catch (Exception ex)
            {
                clientId = 0;
                accountId = 0;
            }

        });

        //get all accounts for a client
        //get list of filtered accounts for a client
        app.get("/clients/{clientId}/accounts", context -> {
            Integer clientId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
            } catch (Exception ex)
            {
                clientId = 0;
            }
        });

        //get an account for a specific client
        app.get("/clients/{clientId}/accounts/{accountId}", context -> {
            Integer clientId;
            Integer accountId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
                accountId = Integer.parseInt(context.pathParam("accountId"));
            } catch (Exception ex)
            {
                clientId = 0;
                accountId = 0;
            }

        });

        //update an account from a specific client
        app.put("/clients/{clientId}/accounts/{accountId}", context -> {
            Integer clientId;
            Integer accountId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
                accountId = Integer.parseInt(context.pathParam("accountId"));
            } catch (Exception ex)
            {
                clientId = 0;
                accountId = 0;
            }

        });

        //delete an account from a specific client
        app.delete("/clients/{clientId}/accounts/{accountId}", context -> {
            Integer clientId;
            Integer accountId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
                accountId = Integer.parseInt(context.pathParam("accountId"));
            } catch (Exception ex)
            {
                clientId = 0;
                accountId = 0;
            }

        });

        //withdraw or deposit a given amount
        app.patch("/clients/{clientId}/accounts/{accountId}", context -> {
            Integer clientId;
            Integer accountId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
                accountId = Integer.parseInt(context.pathParam("accountId"));
            } catch (Exception ex)
            {
                clientId = 0;
                accountId = 0;
            }

        });

        //transfer funds from one account to another by the same client
        app.patch("/clients/{clientId}/accounts/{fromAccountId}/transfer/{toAccountId}", context -> {
            Integer clientId;
            Integer accountId;

            try {
                clientId = Integer.parseInt(context.pathParam("clientId"));
                accountId = Integer.parseInt(context.pathParam("accountId"));
            } catch (Exception ex)
            {
                clientId = 0;
                accountId = 0;
            }

        });
*/

    }
}
