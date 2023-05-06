package controller;
import model.*;
import view.ControlPane;
import view.Login;
import view.Appearance;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.InetAddress;

public class AppController {

    Login login;
    LoginController lgc;
    ControlPane controlPane;
    ArrayList<User> users = new ArrayList<>();
    public AppController(){
            setUsers();
            Appearance appearance = new Appearance();
            int pos = getUserByHostname();
            login = new Login();
            lgc = new LoginController(users.get(pos).getUser(),users.get(pos).getPasswd(),login);
            login.addButtonEvent(lgc);
           for(;;) {
               System.out.println(login.isLogged());
               if(login.isLogged()){
                   System.out.println(login.isLogged());
                   login.dispose();
                   controlPane = new ControlPane(users.get(pos));
                   break;
               }
           }
           ControlPanelController controlPanelController = new ControlPanelController(controlPane);

    }

    public void setUsers(){
        User nodoA = new User("nodoA", "12345", "emmc316", "3309", User.NodeA);
        User nodoB = new User("nodoB","1234","irving","3306", User.NodeB);
        User nodoC = new User("nodoC","1234","parra","3306", User.NodeC);
        //only for debug  User nodoX = new User("NodoX", "12345", "emmcdev", "3306");
        this.users.add((nodoA));
        this.users.add((nodoB));
        this.users.add((nodoC));
    }
    public int getUserByHostname(){
        int pos = 0, i = 0;
        for (User u:users) {
            try {
                if (u.getHostname().equals(InetAddress.getLocalHost().getHostName().toLowerCase())) {
                    pos = i;
                    break;
                }
            }catch (UnknownHostException error){
                System.out.println(" error 404 not Host Aviable");
            }
            i++;
        }
        return pos;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    }

