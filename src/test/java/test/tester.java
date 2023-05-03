package test;
import app.App;
import model.User;
import controller.ConnectDB;
import controller.AppController;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class tester {

    public static void main(String args[]) throws UnknownHostException {

        ConnectDB connectDB = new ConnectDB();
        AppController api = new AppController();
        api.setUsers();

        System.out.println(api.getUsers().get(0).getHostname());
        System.out.println(InetAddress.getLocalHost().getHostName());

        if(api.getUsers().get(0).getHostname().equals(InetAddress.getLocalHost().getHostName())){
            System.out.println("coincide");
        }

    }
}


