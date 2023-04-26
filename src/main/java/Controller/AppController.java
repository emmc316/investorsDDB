package Controller;

import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class AppController {
    ArrayList<User> users = new ArrayList<>();
    ConnectDB connectDB = new ConnectDB();
    public AppController() {
        this.setUsers();
        connectDB.setUser(users.get(0));
        connectDB.connect();
        connectDB.debugConnection();
        for (;; ) {
            
            if(!connectDB.checkConnectiontoNode()){
                connectDB.disconnect();
                connectDB.setUser(users.get(1));
                connectDB.connect();
                connectDB.debugConnection();
                if(!connectDB.checkConnectiontoNode()){
                    connectDB.disconnect();
                    connectDB.setUser(users.get(0));
                    connectDB.connect();
                    connectDB.debugConnection();
                }
            }
        }
    }
    public void setUsers(){
        User nodoA = new User("nodoA", "12345", "emmc316", "3309");
        User nodoX = new User("NodoX", "12345", "emmcdev", "3306");
        //User nodoB = new User("nodoB","1234","irving","3306");
        this.users.add(nodoA);
        this.users.add(nodoX);
    }
}

