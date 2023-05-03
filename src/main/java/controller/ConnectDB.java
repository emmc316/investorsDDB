package controller;

import model.User;
import org.mariadb.jdbc.*;
import java.sql.SQLException;
public class ConnectDB {
    String driver;
    User user;
    Connection connection;
    MariaDbDataSource mariaDbDataSource;

    public ConnectDB(){
        this.mariaDbDataSource = new MariaDbDataSource();
    }

    public void connect(){
        try {
        this.driver = "jdbc:mariadb://" + this.user.getHostname() + ":" + this.user.getPort() + "/inversiones" + "?user=" + this.user.getUser() + "&password=" + this.user.getPasswd();
        mariaDbDataSource.setUrl(driver);
        this.connection = (Connection) mariaDbDataSource.getConnection();
            }
            catch (SQLException err){
                System.out.println("Connection isn't aviable...");
            }
        }
    public boolean checkConnectiontoNode(){
        boolean flag = false;
        try {
                if (this.connection.isValid(1)) {
                 flag = true;
                }
            }
            catch(SQLException ex){
                System.out.println("finding connection...");
            }
        return flag;
    }

    public void disconnect(){
        try {
            connection.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }
    public void debugConnection(){
        System.out.println(this.connection.__test_host());
    }

    public void setUser(User user) {
        this.user = user;
    }
}
