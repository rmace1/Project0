package controllers;

import dao.AccountDao;
import io.javalin.http.Context;
import service.AccountService;

public class AccountController {
    public static AccountService accountService = new AccountService(new AccountDao());

    public static void getAccounts(Context context) {
    }

    public static void addAccount(Context context) {
    }

    public static void getAccount(Context context) {
    }

    public static void updateAccount(Context context) {
    }

    public static void deleteAccount(Context context) {
    }

    public static void adjustBalance(Context context) {
    }

    public static void transferFunds(Context context) {
    }
}
