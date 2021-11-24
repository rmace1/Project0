package controllers;

import dao.AccountDao;
import io.javalin.http.Context;
import models.Account;
import models.Category;
import service.AccountService;

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
            upperLimit = 0.0;
            lowerLimit = 0.0;
        }
        System.out.println(lowerLimit);
        System.out.println(upperLimit);
        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.getAccounts(clientId, upperLimit, lowerLimit)));
    }

    public static void addAccount(Context context) {
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        String name = context.formParam("name");
        Double balance = Double.parseDouble(context.formParam("balance"));
        Category category = Category.valueOf(context.formParam("category"));
        Account newAccount = new Account(name, balance, category, clientId);

        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.createAccount(newAccount)));

    }

    //get one account from a client by id
    public static void getAccount(Context context) {
        Account newAccount = new Account();
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Integer id = Integer.parseInt(context.pathParam("accountId"));
        newAccount.setId(id);
        newAccount.setClientId(clientId);

        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.getAccount(newAccount)));
    }

    public static void updateAccount(Context context) {
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));
        String name = context.formParam("name");
        Double balance = Double.parseDouble(context.formParam("balance"));
        Category category = Category.valueOf(context.formParam("category"));
        Account account = new Account(name, balance, category, clientId);
        account.setId(accountId);

        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.updateAccount(account)));
    }

    public static void deleteAccount(Context context) {
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));

        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.deleteAccount(accountId)));
    }

    public static void adjustBalance(Context context) {
        Integer accountId = Integer.parseInt(context.pathParam("accountId"));
        Double withdrawAmount;
        Double depositAmount;

        try{
            withdrawAmount = Double.parseDouble(context.formParam("withdraw"));
        } catch (Exception e)
        {
            withdrawAmount = 0.0;
        }
        try{
            depositAmount = Double.parseDouble(context.formParam("deposit"));
        } catch(Exception e)
        {
            depositAmount = 0.0;
        }
        Double amount = depositAmount - withdrawAmount;


        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.adjustBalance(accountId, amount)));
    }

    public static void transferFunds(Context context) {
        Integer fromAccountId = Integer.parseInt(context.pathParam("accountId"));
        Integer toAccountId = Integer.parseInt(context.pathParam("transferId"));
        Double transferAmount = Double.parseDouble(context.formParam("transfer"));

        context.contentType("application/json");
        context.result(ClientController.convertToJson(accountService.transferFunds(fromAccountId, toAccountId, transferAmount)));

    }
}
