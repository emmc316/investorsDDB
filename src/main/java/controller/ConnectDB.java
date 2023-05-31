package controller;

import model.User;
import org.mariadb.jdbc.*;
import java.sql.SQLException;
public class ConnectDB {
    String driver;
    User user;
    Connection connection;
    MariaDbDataSource mariaDbDataSource;

    private boolean status = false;

    public ConnectDB() {
        this.mariaDbDataSource = new MariaDbDataSource();
    }

    public void connect() {
        try {
            this.driver = "jdbc:mariadb://" + this.user.getHostname() + ":" + this.user.getPort() + "/" + this.user.getDB() + "?user=" + this.user.getUser() + "&password=" + this.user.getPasswd();
            mariaDbDataSource.setUrl(driver);
            this.connection = (Connection) mariaDbDataSource.getConnection();
            status = true;
        } catch (SQLException err) {
            System.out.println("Connection isn't aviable...");
            status = false;
        }
    }

    public boolean checkConnectiontoNode() {
        boolean flag = false;
        try {
            if (this.connection.isValid(1)) {
                flag = true;
            }
        } catch (SQLException ex) {
            status = false;
        }
        return flag;
    }

    public void disconnect() {
        try {
            connection.close();
            status = false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void debugConnection() {
        System.out.println(this.connection.__test_host());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        if (this.status) {
            return "ConnectDB{" +
                    ", hostname=" + user.getHostname() + ", user, " + user.getUser() + ", " + user.getNode() + "," +
                    ", status=" + "online" +
                    '}';
        } else {
            return "ConnectDB{" +
                    ", hostname=" + user.getHostname() + ", user, " + user.getUser() + "," + user.getNode() + "," +
                    ", status=" + "down" +
                    '}';
        }
    }
}
