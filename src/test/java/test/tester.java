package test;
import app.App;
import model.User;
import controller.ConnectDB;
import controller.AppController;
public class tester {

    public static void main (String args[]){

        ConnectDB connectDB = new ConnectDB();
        AppController api = new AppController();
        api.setUsers();
        api.connect();
        api.debugConnection();
        api.selectInversors();
    }
}
