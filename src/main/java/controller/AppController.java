package controller;
import org.mariadb.jdbc.ClientPreparedStatement;
import org.mariadb.jdbc.Statement;
import org.mariadb.jdbc.client.result.Result;
import view.Login;
import model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppController {
    ArrayList<User> users = new ArrayList<>();
    ConnectDB connectDB = new ConnectDB();
    public AppController(){
        Login lg = new Login();
    }

    public void setUsers(){
        User nodoA = new User("nodoA", "12345", "emmc316", "3309");
        User nodoX = new User("NodoX", "12345", "emmcdev", "3306");
        //User nodoB = new User("nodoB","1234","irving","3306");
        this.users.add(nodoA);
        this.users.add(nodoX);
    }

    public void connect(){
        connectDB.setUser(users.get(0));
        connectDB.connect();
    }

    /*Only on debug actions*/
    public void debugConnection(){
        try {
            if (connectDB.connection.isValid(1)) {
                System.out.println(connectDB.connection.__test_host());
            }
        }catch (SQLException ex){
            System.out.println("There isnt connection");
        }
    }

    public void selectInversors(){
        String querysito = "SELECT * FROM inversionista";
        try {
            Statement stmt = connectDB.connection.createStatement();
            Result rs = (Result) stmt.executeQuery(querysito);
            while(rs.next()){
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getBoolean(6));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void selectInversorByRFC(String rfc){
        String querisito = "SELECT * FROM inversionista WHERE rfcinversionista = ?";
        try {
            ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(querisito);
            preparedStatement.setString(1,rfc);
            Result rs = (Result) preparedStatement.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void selectByDates(String date){
        String querisito = "SELECT * FROM pagare WHERE fechaemision >= ?";
        try {
            ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(querisito);
            preparedStatement.setString(1, date);
            Result rs = (Result) preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString((5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void selectByDates(String date1, String date2){
        String querisito = "SELECT * FROM pagare WHERE fechaemision >= ? AND fechavencimiento <= ?";
        try {
            ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(querisito);
            preparedStatement.setString(1, date1);
            preparedStatement.setString(2,date2);
            Result rs = (Result) preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean connectionIsDown(){
        boolean flag = false;
        if(!connectDB.checkConnectiontoNode()){
            connectDB.disconnect();
            connectDB.setUser(users.get(1));
            connectDB.connect();
            connectDB.debugConnection();
            flag = true;

            if(!connectDB.checkConnectiontoNode()){
                connectDB.disconnect();
                connectDB.setUser(users.get(0));
                connectDB.connect();
                connectDB.debugConnection();
                flag = true;
            }

        }
        return flag;
    }
    public void menu(){

        int op = 0;
        if(!connectionIsDown()) {
            do {
                if(!connectionIsDown()) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Type an option: 1. Select Inversors, 2.Select an specific Inverson 3. Select pagares by date, 4 Select Pagaras by a range of dates");
                    op = sc.nextInt();
                    switch (op) {
                        case 1: {
                            System.out.println("Cosnults: Inversos");
                            selectInversors();
                            break;
                        }

                        case 2: {
                            System.out.println("Type an RFC to Search:");
                            selectInversorByRFC(sc.next());
                            break;
                        }

                        case 3: {
                            System.out.println("Type a date, example 2012-12-21:");
                            selectByDates(sc.next());
                            break;
                        }

                        case 4: {
                            System.out.println("Type two dates a date example 2012-12-21 2013-01-04: ");
                            selectByDates(sc.next(), sc.next());
                            break;
                        }

                        case 5: {
                            System.exit(1);
                        }
                    }
                }
            } while (op == 5);
        }
    }

}
