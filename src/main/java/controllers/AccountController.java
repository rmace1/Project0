package controllers;

import dao.AccountDao;
import io.javalin.http.Context;
import models.Account;
import models.Category;
import models.Client;
import service.AccountService;

import java.util.List;
import java.util.Locale;

//connects to dispatcher
public class AccountController {
    public static AccountService accountService = new AccountService(new AccountDao());

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
        if(accountList.size() == 0)
        {
            context.status(404);
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
        }
    }

    //get one account from a client by id
    public static void getAccount(Context context) {
        Account newAccount = new Account();
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Integer id = Integer.parseInt(context.pathParam("accountId"));
        newAccount.setId(id);
        newAccount.setClientId(clientId);

        Account account = accountService.getAccount(id);
        if(account == null){
            context.status(404);
        }else {
            context.contentType("application/json");
            context.result(ClientController.convertToJson(account));
        }
    }

    public static void updateAccount(Context context) {
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));
        String name = context.formParam("name");
        Double balance = Double.parseDouble(context.formParam("balance"));
        Category category = Category.valueOf(context.formParam("category").toUpperCase());
        Account account = new Account(name, balance, category, clientId);
        account.setId(accountId);

        Account updatedAccount = accountService.updateAccount(account);
        if(updatedAccount == null){
            context.status(404);
        }else {
            context.contentType("application/json");
            context.result(ClientController.convertToJson(updatedAccount));
        }
    }

    public static void deleteAccount(Context context) {
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));

        boolean successful = accountService.deleteAccount(accountId);
        if(successful) {
            context.contentType("application/json");
            context.result(ClientController.convertToJson(successful));
        }else {
            context.status(404);
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
            withdrawAmount = -1.0;
        }
        try{
            depositAmount = Double.parseDouble(context.formParam("deposit"));
            account = accountService.depositToBalance(accountId, depositAmount);
        } catch(Exception e)
        {
            depositAmount = -1.0;
        }

        if(account == null){
            context.status(404);
        }else if(account.getId() == 0){
            context.status(422);
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
}

