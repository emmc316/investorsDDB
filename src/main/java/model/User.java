package model;

public class User {

    public final static String NodeA = "A";
    public final static String NodeRA = "RA";
    public final static String NodeB = "B";
    public final static String NodeRB = "RA";
    public final static String NodeC = "C";
    public final static String NodeRC = "RC";
    private String user;
    private String passwd;
    private String hostname;
    private String port;
    private String node;
    private String db;

    public User(String user, String passwd, String hostname, String port, String node, String db){
        this.user = user;
        this.passwd = passwd;
        this.hostname = hostname.toLowerCase();
        this.port = port;
        this.node = node;
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname.toLowerCase();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNode() {
        return node;
    }

    public void setDB(String db){this.db = db;}

    public String getDB(){
        return db;
    }

    public void setNode(String node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User otherUser = (User) obj;
        return this.user.equals(otherUser.getUser()) &&
                this.passwd.equals(otherUser.getPasswd()) &&
                this.hostname.equals(otherUser.getHostname()) &&
                this.port.equals(otherUser.getPort()) &&
                this.node.equals(otherUser.getNode());
    }
}
