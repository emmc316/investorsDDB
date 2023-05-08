package controller;
import view.ConnectToServers;

import javax.swing.*;
import java.util.ArrayList;

public class ConnectToServersController{
    Thread sendingInformation;
    Thread loadBar;
    ArrayList<String> connections = new ArrayList<>();
    ConnectToServers ctc;

    public void generateModal(){
        this.ctc = new ConnectToServers();
    }
    public String copyInformation(String value){
        return value;
    }

    public void recieveConnections(String data){
      //  System.out.println(data);
       connections.add(data);
    }


    public void sentInfo() {
        sendingInformation = new Thread(() -> {
            for (String connection : connections) {

                String copiedInfo = copyInformation(connection);
                ctc.setInformation(copiedInfo);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void loadingBar(String status) {
        loadBar = new Thread(() -> {
            for (int i = 0; i <= 100; i+=20) {
                ctc.LoadingProgress(i);
                if(ctc.getProgress()==100){
                    close(status);
                    break;
                }
            //    System.out.println(i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void init(String status){
        sentInfo();
        loadingBar(status);
        startThreads();
        ctc.refresh();
    }
    public void close(String statusReport){
        if(ctc.getProgress()==100){
            JOptionPane.showConfirmDialog(null,statusReport,"Servers Connected:",JOptionPane.INFORMATION_MESSAGE);
        }
        ctc.dispose();
    }
    public void startThreads() {
        sendingInformation.start();
        loadBar.start();
    }
}
