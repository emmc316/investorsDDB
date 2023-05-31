package controller;

import model.User;

import java.util.HashMap;

public class Nodes {

    private HashMap<String,ConnectDB> nodes = new HashMap<>();

    public void initNodes(){
        nodes.put("A",new ConnectDB());
        nodes.get("A").setUser(new User("nodoA","12345","emmc316","3309",User.NodeA, "inversiones"));

        nodes.put("RA", new ConnectDB());
        nodes.get("RA").setUser(new User("nodoRA","1234","parra","3306",User.NodeRA, "inversionesr"));

        nodes.put("B",new ConnectDB());
        nodes.get("B").setUser(new User("nodoB","1234","irving","3306",User.NodeB, "inversiones"));

        nodes.put("RB", new ConnectDB());
        nodes.get("RB").setUser(new User("nodoRB","1234","emmc316","3309",User.NodeRB, "inversionesr"));

        nodes.put("C",new ConnectDB());
        nodes.get("C").setUser(new User("nodoC","1234","parra","3306",User.NodeC, "inversiones"));

        nodes.put("RC", new ConnectDB());
        nodes.get("RC").setUser(new User("nodoRC","1234","irving","3306",User.NodeRC, "inversionesr"));

        //nodes.put("X",new ConnectDB());
        //nodes.get("X").setUser(new User("NodoX","12345","emmcdev","3306","X", "inversiones"));

        //nodes.put("Y",new ConnectDB());
        //nodes.get("Y").setUser(new User("nodoY","1234","irvingdev","3306","Y", "inversiones"));
        for (ConnectDB connexions: nodes.values()) {
            connexions.connect();
        }
    }

    public int nodesAviable(){
        int nodesOnline = 0;
        for (ConnectDB conexions:
             nodes.values()) {
            if(conexions.isStatus()){
                nodesOnline++;
            }
        }
        return nodesOnline;
    }

    public String statusReport(){
        String builder = "";
        for (ConnectDB conexions:
                nodes.values()) {
            builder += conexions.toString() + "\n";
        }
       builder += "The total of conexions online are: " + nodesAviable() + "\n";
        return builder;
    }

    public HashMap<String, ConnectDB> getNodes() {
        return nodes;
    }


}
