package controller;
import model.IOperations;
import model.Operation;
import org.mariadb.jdbc.ClientPreparedStatement;
import org.mariadb.jdbc.Statement;
import org.mariadb.jdbc.client.result.Result;
import view.Login;
import model.User;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.InetAddress;

public class AppController implements IOperations {
    ArrayList<User> users = new ArrayList<>();
    ConnectDB connectDB = new ConnectDB();

    Operations operations = new Operations();
    public AppController(){
        setUsers();
        int userPos = getUserByHostname();
        connect(userPos);
        for(;;) {
            menu(userPos);
        }
    }

    public void setUsers(){
        User nodoA = new User("nodoA", "12345", "emmc316", "3309", User.NodeA);
        User nodoB = new User("nodoB","1234","irving","3306", User.NodeB);
        User nodoC = new User("nodoC","1234","parra","3306", User.NodeC);
        //only for debug  User nodoX = new User("NodoX", "12345", "emmcdev", "3306");
        this.users.add(nodoA);
        this.users.add(nodoB);
    }

    public int getUserByHostname(){
        int pos = 0, i = 0;
        for (User u:users) {
            try {
                if (u.getHostname().equals(InetAddress.getLocalHost().getHostName())) {
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
    public void connect(int id){
        connectDB.setUser(users.get(id));
        connectDB.connect();
    }

    public void disconnect(){
        this.connectDB.disconnect();
    }

    /*Only on debug actions*/
    public void debugConnection(){
    this.connectDB.debugConnection();
    }

    @Override
    public void selectInversors(int pos){
        if(operations.getOperations().get(Operations.SELECT_INVERSORS).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                Statement stmt = connectDB.connection.createStatement();
                Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_INVERSORS).getQuery());
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getBoolean(6));
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void selectInversorByRFC(int pos,String rfc) {
        if (operations.getOperations().get(Operations.SELECT_INVERSORS_BY_RFC).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_INVERSORS_BY_RFC).getQuery());
                preparedStatement.setString(1, rfc);
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void selectContracts(int pos) {
        if (operations.getOperations().get(Operations.SELECT_CONTRACTS).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                Statement stmt = connectDB.connection.createStatement();
                Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_CONTRACTS).getQuery());
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + " " + rs.getString(4) + " " + rs.getString(5));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void SelectContractsByClv(int pos,String clv) {
        if (operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).getQuery());
                preparedStatement.setString(1, clv);
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void selectPromissorys(int pos) {
        if (operations.getOperations().get(Operations.SELECT_PROMISORYS).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS).getQuery());
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void selectPromissoryByDate(int pos,String date) {
        if(operations.getOperations().get(Operations.SELECT_PROMISORY_BY_DATE).validateQuerieByNode(users.get(pos).getNode())) {
        try {
            ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORY_BY_DATE).getQuery());
            preparedStatement.setString(1, date);
            Result rs = (Result) preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
      }
    }

    @Override
    public void selectByPromissoryDates(int pos,String date1, String date2) {
        if (operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_DATES).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_DATES).getQuery());
                preparedStatement.setString(1, date1);
                preparedStatement.setString(2, date2);
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void menu(int pos){

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
                            selectInversors(pos);
                            break;
                        }

                        case 2: {
                            System.out.println("Type an RFC to Search:");
                            selectInversorByRFC(pos,sc.next());
                            break;
                        }

                        case 3: {
                            System.out.println("Type a date, example 2012-12-21:");
                            selectPromissoryByDate(pos,sc.next());
                            break;
                        }

                        case 4: {
                            System.out.println("Type two dates a date example 2012-12-21 2013-01-04: ");
                            selectByPromissoryDates(pos,sc.next(),sc.next());
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
