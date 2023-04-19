package Controller;

import Model.User;
import Model.Connect;
import org.mariadb.jdbc.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class API {
    String driver;
    Connect connect;
    ArrayList<User> users;
    public API(ArrayList<User> users, Connect connect){
    this.connect = connect;
    this.users = users;

    }

    public void connect(User user){

            driver = driver = "jdbc:mariadb://" + user.getHostname() + ":" + user.getPort() + "/inversiones" + "?user=" + user.getUser() + "&password=" + user.getPasswd();
            try {
                this.connect.setConnection(Driver.connect(Configuration.parse(this.driver)));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    public boolean checkConnectiontoNode(User user, org.mariadb.jdbc.Connection connection ){
            try {
                this.connect.setUser(user);
                if (connection.isValid(1)) {
                    System.out.println("Estoy conectado");
                } else {
                    System.out.println("No estoy conectado");
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
                return true;
    }

    public void changeHost(User user){
        this.connect = new Connect(user);
    }

    public void debugConnection(){
        System.out.println(this.connect.getConnection().__test_host());
    }
}
