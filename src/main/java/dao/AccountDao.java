package dao;

import models.Account;
import models.Category;
import models.Client;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//connects to service
public class AccountDao implements AccountDaoInterface {
    private String url;
    private String userName;
    private String password;

    public AccountDao() {
        Properties prop = new Properties();
        String fileName = "config.txt";

        try (FileInputStream fis = new FileInputStream(fileName)) {

            prop.load(fis);
            url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            userName = prop.getProperty("jdbcUserName");
            password = prop.getProperty("jdbcPassword");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AccountDao(String url, String userName, String password){
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public boolean createAccount(Account newAccount) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "INSERT INTO accounts VALUES (DEFAULT, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newAccount.getName());
            ps.setDouble(2, newAccount.getBalance());
            ps.setString(3, newAccount.getCategory().toString());
            ps.setInt(4, newAccount.getClientId());

            return (ps.executeUpdate() > 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Account> getAccounts(int clientId, double upperLimit, double lowerLimit) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql;
            PreparedStatement ps;
            if(upperLimit == 0 && lowerLimit == 0) {
                sql = "SELECT * FROM accounts WHERE clientid = ?;";
                ps = conn.prepareStatement(sql);
                //ps.setInt(1, clientId);
            }else
            {
                sql = "SELECT * FROM accounts WHERE clientid = ? AND balance > ? AND balance < ?";
                ps = conn.prepareStatement(sql);
                //ps.setInt(1, clientId);
                ps.setDouble(2, lowerLimit);
                ps.setDouble(3, upperLimit);
            }

            ps.setInt(1, clientId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3) ;
                Category category = Category.valueOf(rs.getString(4));
                int cId = rs.getInt(5);
                Account account = new Account(name, balance, category, cId);
                account.setId(id);
                accounts.add(account);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getAccount(Account account) {
        Account newAccount = null;
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM accounts WHERE id = ? AND clientid = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getId());
            ps.setInt(2, account.getClientId());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3) ;
                Category category = Category.valueOf(rs.getString(4));
                int cId = rs.getInt(5);
                newAccount = new Account(name, balance, category, cId);
                newAccount.setId(id);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return newAccount;
    }

    @Override
    public Account updateAccount(Account account) {
        Account newAccount = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "UPDATE accounts SET name = ?, balance = ?, category = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setDouble(2, account.getBalance());
            ps.setString(3, account.getCategory().toString());
            ps.setInt(4, account.getId());

            boolean updated = (ps.executeUpdate() > 0);

            sql = "SELECT * FROM accounts WHERE id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getId());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                newAccount = new Account(rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return newAccount;
    }

    @Override
    public boolean deleteAccount(int accountid) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "DELETE FROM accounts WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountid);

            return (ps.executeUpdate() > 0);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Account adjustBalance(int accountId, double amount) {
        Account account = new Account();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM accounts WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                account = new Account(rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
                account.setId(rs.getInt(1));
            }

            account.adjustBalance(amount);

            sql = "UPDATE accounts SET balance = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, accountId);

            ps.executeUpdate();

        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return account;
    }

    @Override
    public Account transferFunds(int sendingAccountId, int receivingAccountId, double amount) {
        Account sendingAccount = new Account();
        Account receivingAccount = new Account();

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM accounts WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sendingAccountId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sendingAccount = new Account(rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
                sendingAccount.setId(rs.getInt(1));
            }

            sql = "SELECT * FROM accounts WHERE id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, receivingAccountId);

            rs = ps.executeQuery();

            while (rs.next()) {
                receivingAccount = new Account(rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
                receivingAccount.setId(rs.getInt(1));
            }

            sendingAccount.adjustBalance(-amount);
            receivingAccount.adjustBalance(amount);

            sql = "UPDATE accounts SET balance = ? WHERE id = ?;";
            ps = conn.prepareStatement(sql);

            ps.setDouble(1, receivingAccount.getBalance());
            ps.setInt(2, receivingAccountId);

            ps.executeUpdate();

            sql = "UPDATE accounts SET balance = ? WHERE id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, sendingAccount.getBalance());
            ps.setInt(2, sendingAccountId);

            ps.executeUpdate();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sendingAccount;
    }
}
