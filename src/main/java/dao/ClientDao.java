package dao;

import models.Client;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//Connects to service
public class ClientDao implements ClientDaoInterface {

    @Override
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        Properties prop = new Properties();
        String fileName = "config.txt";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            String url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            Connection conn = DriverManager.getConnection(url, prop.get("jdbcUserName").toString(), prop.getProperty("jdbcPassword").toString());
            String sql = "SELECT * FROM clients;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                clients.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client getClient(int id) {
        Client client = null;
        Properties prop = new Properties();
        String fileName = "config.txt";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            String url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            Connection conn = DriverManager.getConnection(url, prop.get("jdbcUserName").toString(), prop.getProperty("jdbcPassword").toString());
            String sql = "SELECT * FROM clients WHERE id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean createClient(Client newClient) {

        Properties prop = new Properties();
        String fileName = "config.txt";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            String url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            Connection conn = DriverManager.getConnection(url, prop.get("jdbcUserName").toString(), prop.getProperty("jdbcPassword").toString());
            String sql = "INSERT INTO clients VALUES (DEFAULT, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newClient.getFirstName());
            ps.setString(2, newClient.getLastName());

            return (ps.executeUpdate() > 0);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteClient(int id) {

        Properties prop = new Properties();
        String fileName = "config.txt";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            String url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            Connection conn = DriverManager.getConnection(url, prop.get("jdbcUserName").toString(), prop.getProperty("jdbcPassword").toString());
            String sql = "DELETE FROM clients WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return (ps.executeUpdate() > 0);


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Client updateClient(Client updateClient) {
        Client client = null;
        Properties prop = new Properties();
        String fileName = "config.txt";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            String url = "jdbc:postgresql://" + prop.getProperty("jdbcConnection") + "/" + prop.getProperty("jdbcDbName");
            Connection conn = DriverManager.getConnection(url, prop.get("jdbcUserName").toString(), prop.getProperty("jdbcPassword").toString());
            String sql = "UPDATE clients SET firstname = ?, lastname = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, updateClient.getFirstName());
            ps.setString(2, updateClient.getLastName());
            ps.setInt(3, updateClient.getId());

            boolean updated = (ps.executeUpdate() > 0);

            sql = "SELECT * FROM clients WHERE id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, updateClient.getId());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return client;
    }
}
