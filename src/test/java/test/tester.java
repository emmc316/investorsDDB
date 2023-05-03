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

        int pos = api.getUserByHostname();
        api.connect(pos);
        if(api.getUsers().get(pos).getHostname().equals(InetAddress.getLocalHost().getHostName())){
            for(;;){
                if(!api.connectionIsDown()){
                    api.menu(pos);
                }
            }
        }

    }
}


