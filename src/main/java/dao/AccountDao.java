package dao;

import models.Account;
import models.Category;
import org.apache.log4j.Logger;

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
    Logger log = Logger.getLogger(AccountDao.class);

    public AccountDao() {
        Properties prop = new Properties();
        String fileName = "config.txt";

        try (FileInputStream fis = new FileInputStream(fileName)) {

            prop.load(fis);
            url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            userName = prop.getProperty("jdbcUserName");
            password = prop.getProperty("jdbcPassword");

        } catch (Exception e) {
            log.error(e);
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
            log.error(e);
        }
        return false;
    }

    @Override
    public List<Account> getAccounts(int clientId) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql;
            PreparedStatement ps;
            sql = "SELECT * FROM accounts WHERE clientid = ? ORDER BY id;";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, clientId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3) ;
                Category category = Category.valueOf(rs.getString(4));
                int cId = rs.getInt(5);
                Account account = new Account(id, name, balance, category, cId);
                accounts.add(account);
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        return accounts;
    }

    @Override
    public List<Account> getAccounts(int clientId, double upperLimit, double lowerLimit){
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql;
            PreparedStatement ps;
            sql = "SELECT * FROM accounts WHERE clientid = ? AND balance > ? AND balance < ?";
            ps = conn.prepareStatement(sql);
            //ps.setInt(1, clientId);
            ps.setDouble(2, lowerLimit);
            ps.setDouble(3, upperLimit);


            ps.setInt(1, clientId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3) ;
                Category category = Category.valueOf(rs.getString(4));
                int cId = rs.getInt(5);
                Account account = new Account(id, name, balance, category, cId);
                accounts.add(account);
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        return accounts;
    }


    @Override
    public Account getAccount(int accountId) {
        Account newAccount = null;
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM accounts WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3) ;
                Category category = Category.valueOf(rs.getString(4));
                int cId = rs.getInt(5);
                newAccount = new Account(id, name, balance, category, cId);

            }
        } catch (Exception e)
        {
            log.error(e);
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
                newAccount = new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
            }
        } catch (Exception e)
        {
            log.error(e);
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
            log.error(e);
        }

        return false;
    }

    public Account withdrawFromAccount(Account account, double amount) {
        //Account account = new Account();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM accounts WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                account = new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
            }

            account.withdraw(amount);

            sql = "UPDATE accounts SET balance = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getId());

            ps.executeUpdate();

        } catch (Exception e)
        {
            log.error(e);
        }


        return account;
    }

    public Account depositToAccount(Account account, double amount) {
        //Account account = new Account();
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM accounts WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                account = new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                        Category.valueOf(rs.getString(4)), rs.getInt(5));
            }

            account.deposit(amount);

            sql = "UPDATE accounts SET balance = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getId());

            ps.executeUpdate();

        } catch (Exception e)
        {
            log.error(e);
        }


        return account;
    }
}
