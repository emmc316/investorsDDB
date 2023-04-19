package app;
import Model.Connect;
import Model.User;
import Controller.API;
import java.util.ArrayList;

public class app {
    public static void main(String args[]) {

        User nodoA = new User("nodoA", "12345", "emmc316", "3309");
        User nodoX = new User("NodoX", "12345", "emmcdev", "3306");

        //User nodoB = new User("nodoB", "12345", "emmc316", "3306");
        ArrayList<User> users = new ArrayList<User>();
        users.add(nodoA);
        users.add(nodoX);
        Connect connect = new Connect(users.get(0));
        API api = new API(users, connect);
        api.connect(users.get(0));



        for(;;) {
            System.out.println("reload");
            while (api.checkConnectiontoNode(users.get(0), connect.getConnection())) {

                if(!api.checkConnectiontoNode(users.get(0),connect.getConnection())){
                    api.changeHost(users.get(1));
                    api.connect(users.get(1));
                    api.debugConnection();
                }
            }

            while (api.checkConnectiontoNode(users.get(0), connect.getConnection())) {
                if(!api.checkConnectiontoNode(users.get(0),connect.getConnection())) {
                    api.changeHost(users.get(0));
                    api.connect(users.get(0));
                    api.debugConnection();
                }
            }
        }
    }
    }

