package dao;

import models.Client;
import org.apache.log4j.Logger;

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
    private String url;
    private String userName;
    private String password;
    Logger log = Logger.getLogger(ClientDao.class);

    public ClientDao(){
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

    public ClientDao(String url, String userName, String password){
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM clients;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                clients.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e)
        {
            log.error(e);
        }

        return clients;
    }

    @Override
    public Client getClient(int id) {
        Client client = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "SELECT * FROM clients WHERE id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        return client;
    }

    @Override
    public boolean createClient(Client newClient) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "INSERT INTO clients VALUES (DEFAULT, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newClient.getFirstName());
            ps.setString(2, newClient.getLastName());

            return (ps.executeUpdate() > 0);


        } catch (Exception e)
        {
            log.error(e);
        }

        return false;
    }

    @Override
    public boolean deleteClient(int id) {

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            String sql = "DELETE FROM clients WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return (ps.executeUpdate() > 0);


        } catch (Exception e)
        {
            log.error(e);
        }
        return false;
    }

    @Override
    public Client updateClient(Client updateClient) {
        Client client = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
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
            log.error(e);
        }
        return client;
    }
}
