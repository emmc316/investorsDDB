package model;
import java.util.Arrays;

public class Operation {

    public static final String NODE_A = "A";
    public static final String NODE_RA = "RA";
    public static final String NODE_B = "B";
    public static final String NODE_RB = "RB";
    public static final String NODE_C = "C";
    public static final String NODE_RC = "RC";

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
                System.out.println("You are allowed use this query in:" + node);
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
