import com.fasterxml.jackson.databind.ObjectMapper;
import frontcontroller.FrontController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import models.Account;
import models.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * CRUD operations for Clients and Accounts
 * 1 models.Client can have many Accounts
 * Clients can deposit/withdraw money from an account
 * Funds can be transferred between accounts
 *
 * log4j allows logging to happen
 *
 * Log4J levels
 *  All < debug < info < warn < fatal < off
 */
public class Main {
    static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        log.setLevel(Level.OFF);
        log.debug("this is a debug log.");
        log.info("this is an info log.");
        log.warn("this is a warning log.");
        log.error("this is an error log.");
        log.fatal("this is a fatal log.");


        Javalin server = Javalin.create().start(9000);
        new FrontController(server);


    }
}
