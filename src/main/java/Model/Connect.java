package Model;
import java.sql.*;
import org.mariadb.jdbc.*;
import org.mariadb.jdbc.Configuration;

public class Connect {
    private User user;
    private org.mariadb.jdbc.Connection connection;

    public Connect(User user) {
        this.user = user;

    }
    public void setUser(User user){
        this.user = user;
    }

    public org.mariadb.jdbc.Connection getConnection(){
        return this.connection;
    }

    public void setConnection(org.mariadb.jdbc.Connection connection) {
        this.connection = connection;
    }
}

