package model;

import org.mariadb.jdbc.ClientPreparedStatement;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;
import org.mariadb.jdbc.client.result.Result;

import java.sql.SQLException;
import java.util.Arrays;

public class Operation {

    public static final String NODE_A = "A";
    public static final String NODE_B = "B";
    public static final String NODE_C = "C";

    private String name;
    private String query;
    private String[] nodes;
    public Operation(String query, String[] nodes){
        this.query = query;
        this.nodes = nodes;
    }
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] getNodes() {
        return nodes;
    }

    public void setNodes(String[] nodes) {
        this.nodes = nodes;
    }

    public boolean validateQuerieByNode(String node){
        boolean validated = false;
        for(String nodes: this.getNodes()){
            if(nodes.equals(node)){
                System.out.println("You are allowed use this queri in:" + node);
                validated = true;
            }
        }
        return validated;
    }

    @Override
    public String toString() {
        return "Operation{" +
                ", query='" + query + '\'' +
                ", nodes=" + Arrays.toString(nodes) +
                '}';
    }
}
