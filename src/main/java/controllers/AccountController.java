package controllers;

import dao.AccountDao;
import dao.ClientDao;
import io.javalin.http.Context;
import models.Account;
import models.Category;
import models.Client;
import org.apache.log4j.Logger;
import service.AccountService;
import service.ClientService;

import java.util.List;
import java.util.Locale;

//connects to dispatcher
public class AccountController {
    public static AccountService accountService = new AccountService(new AccountDao(), new ClientDao());
    public static ClientService clientService = new ClientService(new ClientDao(), new AccountDao());
    public static Logger log = Logger.getLogger(ClientDao.class);

    //get all accounts belonging to client
    public static void getAccounts(Context context) {
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Double upperLimit;
        Double lowerLimit;
        try {
            upperLimit = Double.parseDouble(context.queryParam("amountLessThan"));
            lowerLimit = Double.parseDouble(context.queryParam("amountGreaterThan"));
        } catch (Exception e)
        {
            upperLimit = -1.0;
            lowerLimit = -1.0;
        }
        List<Account> accountList = accountService.getAccounts(clientId, upperLimit, lowerLimit);
        if(accountList == null)
        {
            context.status(404);
            context.result("Account not found.");
        }else
        {
            context.contentType("application/json");
            context.result(ClientController.convertToJson(accountList));
        }

    }

    public static void addAccount(Context context) {
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        String name = context.formParam("name");
        Double balance = Double.parseDouble(context.formParam("balance"));
        Category category = Category.valueOf(context.formParam("category").toUpperCase());
        Account newAccount = new Account(name, balance, category, clientId);

        boolean successful = accountService.createAccount(newAccount);
        if(successful) {
            context.contentType("application/json");
            context.status(201);
            context.result(ClientController.convertToJson(successful));
        }else{
            context.contentType("application/json");
            context.status(404);
            context.result(ClientController.convertToJson(successful));
        }
    }

    //get one account from a client by id
    public static void getAccount(Context context) {
        Account newAccount = new Account();
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Integer id = Integer.parseInt(context.pathParam("accountId"));
        newAccount.setId(id);
        newAccount.setClientId(clientId);

        Client client = clientService.getClient(clientId);
        Account account = accountService.getAccount(id);
        if(account == null || client == null){
            context.status(404);
            context.result("Client or account are not found.");
            log.error("Account or client not found.");

        }else if (account.getClientId() != clientId){
            context.status(404);
            context.result("Account/Client combination not found.");
        }else{
            context.contentType("application/json");
            context.result(ClientController.convertToJson(account));
        }
    }

    public static void updateAccount(Context context) {
        Integer clientId = 0;
        Integer accountId = 0;
        String name = "";
        Double balance = 0.0;
        Category category = null;
        Account account = null;
        Account updatedAccount = null;
        try {
            clientId = Integer.parseInt(context.pathParam("id"));
            accountId = Integer.parseInt(context.pathParam("accountId"));
            name = context.formParam("name");
            balance = Double.parseDouble(context.formParam("balance"));
            category = Category.valueOf(context.formParam("category").toUpperCase());
            account = new Account(accountId, name, balance, category, clientId);

            updatedAccount = accountService.updateAccount(account);
        }catch(Exception e)
        {
            //e.printStackTrace();
            context.result("Error with input.  Check form or url for invalid or missing input.");
            return;
        }

        if(updatedAccount == null){
            context.status(404);
            context.result("Account or Client not found.");
        }else {
            context.contentType("application/json");
            context.result(ClientController.convertToJson(updatedAccount));
        }
    }

    public static void deleteAccount(Context context) {
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Client client = clientService.getClient(clientId);
        Account account = accountService.getAccount(accountId);
        boolean successful = false;
        if((client != null && account != null) && client.getId() == account.getClientId()) {
            successful = accountService.deleteAccount(accountId);
        }
        if(successful) {
            context.contentType("application/json");
            context.result(ClientController.convertToJson(successful));
        }else {
            context.status(404);
            context.result("Account or client not found.");
        }
    }

    public static void adjustBalance(Context context) {
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));
        Double withdrawAmount;
        Double depositAmount;
        Account account = null;
        try{
            withdrawAmount = Double.parseDouble(context.formParam("withdraw"));
            account = accountService.withdrawFromBalance(accountId, withdrawAmount);
        } catch (Exception e)
        {
            //withdrawAmount = -1.0;
        }
        try{
            depositAmount = Double.parseDouble(context.formParam("deposit"));
            account = accountService.depositToBalance(accountId, depositAmount);
        } catch(Exception e)
        {
            //depositAmount = -1.0;
        }

        if(account == null){
            context.status(404);
        }else if(account.getId() == 0){
            context.status(422);
            log.error("Insufficient funds in account.");
        }else{
            context.contentType("application/json");
            context.result(ClientController.convertToJson(account));
        }
    }

    public static void transferFunds(Context context) {
        Integer fromAccountId = Integer.parseInt(context.pathParam("accountId"));
        Integer toAccountId = Integer.parseInt(context.pathParam("transferId"));
        Double transferAmount = Double.parseDouble(context.formParam("amount"));

        Account account = accountService.transferFunds(fromAccountId, toAccountId, transferAmount);
        if(account == null){
            context.status(404);
        }else if(account.getId() == 0) {
            context.status(422);
        }else{
            context.contentType("application/json");
            context.result(ClientController.convertToJson(account));
        }
    }

    public static void updatePartialAccount(Context context) {
        Integer clientId = 0;
        Integer accountId = 0;
        String name = "";
        Double balance = 0.0;
        Category category = Category.NONE;
        Double withdrawAmount = 0.0;
        Double depositAmount;
        Account account = null;
        Account updatedAccount = null;

        try{
            clientId = Integer.parseInt(context.pathParam("id"));
            accountId = Integer.parseInt(context.pathParam("accountId"));
        }catch (Exception e){
            clientId = 0;
            accountId = 0;
        }

        try{
            withdrawAmount = Double.parseDouble(context.formParam("withdraw"));
            account = accountService.withdrawFromBalance(accountId, withdrawAmount);
        } catch (Exception e)
        {
            withdrawAmount = -1.0;
        }
        try{
            depositAmount = Double.parseDouble(context.formParam("deposit"));
            account = accountService.depositToBalance(accountId, depositAmount);
        } catch(Exception e)
        {
            depositAmount = -1.0;
        }

        /*if(withdrawAmount < 0.00){
            context.status(422);
            context.result("No bueno muchacho.");
            return;
        }*/

        if(account == null){
            context.status(404);
        }else if(account.getId() == 0){
            context.status(422);
        }else{
            context.contentType("application/json");
            context.result(ClientController.convertToJson(account));
        }


        try{
            name = context.formParam("name");

        }catch (Exception e){
            name = "";
        }
        try{
            balance = Double.parseDouble(context.formParam("balance"));

        }catch (Exception e){
            balance = -1.0;
        }
        try{
            category = Category.valueOf(context.formParam("category").toUpperCase());

        }catch (Exception e){
            category = Category.NONE;
        }

        Account accountToUpdate = new Account(accountId, name, balance, category, clientId);
        //if any fields need updated, update them.
        if(accountToUpdate.getName() != null || accountToUpdate.getBalance() != -1.00 ||
                !accountToUpdate.getCategory().equals(Category.NONE)){
            updatedAccount = accountService.updateAccountPartial(accountId, name, balance, category, clientId);


            if(updatedAccount == null){
                context.status(404);
            }else {
                context.contentType("application/json");
                context.result(ClientController.convertToJson(updatedAccount));
            }
        }

    }
}

