package controller;
import model.*;
import view.ConnectToServers;
import view.ControlPane;
import view.Login;
import view.Appearance;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.InetAddress;
import java.util.HashMap;

public class AppController {

    Login login;
    LoginController lgc;
    ControlPane controlPane;
    Nodes nodes = new Nodes();
    ArrayList<User> users = new ArrayList<>();
    public AppController(){
            Appearance appearance = new Appearance();
            nodes.initNodes();
            ConnectToServersController cc = new ConnectToServersController();
            for(ConnectDB connections: nodes.getNodes().values()) {
                cc.recieveConnections("https://"+connections.user.getHostname()+":"+connections.user.getPort());
            }
            cc.generateModal();
            cc.init(nodes.statusReport());

            String id = getUserByHostname();
            login = new Login();
            lgc = new LoginController(nodes.getNodes().get(id).user.getUser(),nodes.getNodes().get(id).user.getPasswd(),login);
            login.addButtonEvent(lgc);
           for(;;) {
               System.out.println();
               if(login.isLogged()){
                   System.out.println(login.isLogged());
                   login.dispose();
                   controlPane = new ControlPane(nodes.getNodes().get(id).user);
                   break;
               }
           }
        ControlPanelController controlPanelController = new ControlPanelController(controlPane);
        controlPanelController.setNodes(nodes);
        controlPanelController.setNodoId(id);
        controlPane.addEventsButtons(controlPanelController);
        controlPane.addEventsItems(controlPanelController);


    }

    public String getUserByHostname(){
        String id = "";
        for (ConnectDB conexions:nodes.getNodes().values()) {
            try {
                if (conexions.user.getHostname().equals(InetAddress.getLocalHost().getHostName().toLowerCase())) {
                    id = conexions.user.getNode();
                    break;
                }
            }catch (UnknownHostException error){
                System.out.println(" error 404 not Host Aviable");
            }
        }

        return id;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    }

