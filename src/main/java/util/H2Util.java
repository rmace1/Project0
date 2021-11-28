package util;

import dao.AccountDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2Util {
    public static String url = "jdbc:h2:./h2/db";
    public static String userName = "sa";
    public static String password = "sa";
    static Logger log = Logger.getLogger(AccountDao.class);


    public static void createClientTable(){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            String sql = "CREATE TABLE clients(id serial PRIMARY KEY, firstName varchar(50), lastName varchar(50))";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            log.error(e);
        }

    }

    public static void createAccountTable(){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            String sql = "CREATE TABLE accounts(\n" +
                    "id serial PRIMARY KEY, \n" +
                    "name varchar(50), \n" +
                    "balance DOUBLE PRECISION DEFAULT 0.00, \n" +
                    "category varchar(50) DEFAULT 'PERSONAL', \n" +
                    "clientid int NOT NULL,\n" +
                    "FOREIGN KEY (clientid) REFERENCES clients(id)\n" +
                    ");";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            log.error(e);
        }

    }

    public static void dropClientTable(){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            String sql = "DROP TABLE clients;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            log.error(e);
        }

    }

    public static void dropAccountTable(){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            String sql = "DROP TABLE accounts;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            log.error(e);
        }

    }
}
