package controller;

import model.*;
import org.mariadb.jdbc.ClientPreparedStatement;
import org.mariadb.jdbc.Statement;
import org.mariadb.jdbc.client.result.Result;
import view.ControlPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class ControlPanelController implements ItemListener, IOperations, ActionListener {

    private ControlPane pane;

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Inversor> inversors = new ArrayList<Inversor>();
    ArrayList<Branch> branches = new ArrayList<Branch>();
    ArrayList<Rate> rates = new ArrayList<Rate>();
    ArrayList<Contract> contracts = new ArrayList<Contract>();
    ArrayList<PromissoryNote> promisossorysNotes = new ArrayList<PromissoryNote>();
    ConnectDB connectDB = new ConnectDB();
    Operations operations = new Operations();

    int userPos;
    public ControlPanelController(ControlPane pane) {
        setUsers();
        this.pane = pane;
        userPos = findUser();
        connect(userPos);

        }



    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox origen = (JComboBox) e.getSource();
        switch (origen.getName()){
            case "comboInversionista":
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selected = (String)e.getItem();
                    switch (selected){
                        case "Todos los clientes":
                            pane.enableComponents(1,false);

                            break;
                        case "RFC de un cliente":
                            pane.enableComponents(1,true);
                            break;
                    }
                }
                break;

            case "comboContrato":
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selected = (String)e.getItem();
                    switch (selected){
                        case "Todos los contratos":
                            pane.enableComponents(2,false);
                            break;
                        case "RFC de un cliente":
                            pane.enableComponents(2,true);
                            break;
                    }
                }
                break;

            case "comboPagare":
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selected = (String)e.getItem();
                    switch (selected){
                        case "Todos los pagares":
                            pane.enableComponents(3,false);
                            pane.enableComponents(4,false);
                            break;
                        case "RFC de un cliente":
                            pane.enableComponents(3,true);
                            pane.enableComponents(4,false);
                            break;
                        case "En un intervalo de fechas":
                            pane.enableComponents(3, false);
                            pane.enableComponents(4, true);
                            break;
                    }
                }
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getName()){
            case "inversors":{
                pane.getModelInversionista().setRowCount(0);
                if(!pane.getCampoRFCInversionistas().isEnabled()) {
                    selectInversors(this.userPos);
                    for (Inversor inv : inversors) {
                        pane.getModelInversionista().addRow(new Object[]{inv.getRfc(), inv.getFull_name(), inv.getPhoneNumber(), inv.getAddress(), inv.getEmail(), inv.getType()});

                    }
                }
                else{
                    Inversor inv = selectInversorByRFC(userPos,pane.getCampoRFCInversionistas().getText());
                    pane.getModelInversionista().addRow(new Object[]{inv.getRfc(),inv.getFull_name(),inv.getPhoneNumber(),inv.getAddress(),inv.getEmail(),inv.getType()});
                }
                inversors.clear();
                break;
            }

            case "contracts":{
                pane.getModelContrato().setRowCount(0);
                if(!pane.getCampoRFCContratos().isEnabled()) {

                    selectContracts(this.userPos);
                    for (Contract ctc : contracts) {
                        pane.getModelContrato().addRow(new Object[]{ctc.getCodeContract(), ctc.getBranchCode(), ctc.getRfcInversor(), ctc.getTotalMount()});
                    }
                }
                else{
                    Contract ctc = selectContractsByClv(userPos,pane.getCampoRFCContratos().getText());
                    pane.getModelContrato().addRow(new Object[]{ctc.getCodeContract(),ctc.getBranchCode(),ctc.getRfcInversor(),ctc.getTotalMount()});
                }
                contracts.clear();
                break;
            }

            case "promissoryNotes":{
                pane.getModelPagare().setRowCount(0);
                if(!pane.getFechaFinal().isEnabled() && !pane.getFechaFinal().isEnabled() && !pane.getCampoRFCPagares().isEnabled()) {
                    selectPromissorys(userPos);
                    for (PromissoryNote pro : promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getBranchCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
                    }
                }
                else if(pane.getCampoRFCPagares().isEnabled() && !pane.getFechaInicial().isEnabled() && !pane.getFechaFinal().isEnabled()) {
                  selectPromissorysByRFC(userPos, this.pane.getCampoRFCPagares().getText());
                    for (PromissoryNote pro : promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getBranchCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
                    }
                }

                else{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    selectByPromissoryDates(this.userPos,dateFormat.format(pane.getFechaInicial().getDate()),dateFormat.format(pane.getFechaFinal().getDate()));
                    for(PromissoryNote pro: promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getBranchCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
     }
                }

                promisossorysNotes.clear();
                break;
            }
        }
    }

    public int findUser(){
        int i = 0, pos = 0;
        for(User u:users){
            if(this.pane.getNode().equals(u)){
              pos = i;
              break;
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

                if(!connectDB.checkConnectiontoNode()){
                    connectDB.disconnect();
                    connectDB.setUser(users.get(2));
                    connectDB.connect();
                    connectDB.debugConnection();
                }
            }
        }
        return flag;
    }
    @Override
    public void selectInversors(int pos){
        String tipoPersona;
        if(operations.getOperations().get(Operations.SELECT_INVERSORS).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                Statement stmt = connectDB.connection.createStatement();
                Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_INVERSORS).getQuery());
                while (rs.next()) {
                    System.out.println();
                    if(rs.getInt(6)==0){
                        tipoPersona = "fisica";
                    }
                    else{
                        tipoPersona = "moral";
                    }
                    inversors.add(new Inversor(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),tipoPersona));
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public Inversor selectInversorByRFC(int pos,String rfc) {
        Inversor inversorLocal = null;
        String typeInversor = "";
        if (operations.getOperations().get(Operations.SELECT_INVERSORS_BY_RFC).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_INVERSORS_BY_RFC).getQuery());
                preparedStatement.setString(1, rfc);

                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    if(rs.getInt(6)==0){
                        typeInversor = "fisica";
                    }
                    else{
                        typeInversor = "moral";
                    }
                    inversorLocal = new Inversor(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),typeInversor);
                              }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inversorLocal;
    }

    @Override
    public void selectContracts(int pos) {
        if (operations.getOperations().get(Operations.SELECT_CONTRACTS).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                Statement stmt = connectDB.connection.createStatement();
                Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_CONTRACTS).getQuery());
                while (rs.next()) {
                    contracts.add(new Contract(rs.getString(1) ,rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getBoolean(5)));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public Contract selectContractsByClv(int pos,String clv) {
        Contract contractLocal = null;
        if (operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).getQuery());
                preparedStatement.setString(1, clv);
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                   contractLocal = new Contract(rs.getString(1) ,rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getBoolean(5));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contractLocal;
    }

    @Override
    public void selectPromissorys(int pos) {
        if (operations.getOperations().get(Operations.SELECT_PROMISORYS).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS).getQuery());
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    promisossorysNotes.add(new PromissoryNote(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4).charAt(0), LocalDate.parse(rs.getString(5)),LocalDate.parse(rs.getString(6))));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void selectPromissorysByRFC(int pos, String rfc) {
        if (operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_RFC).validateQuerieByNode(users.get(pos).getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) connectDB.connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_RFC).getQuery());
                preparedStatement.setString(1,rfc);
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    promisossorysNotes.add(new PromissoryNote(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4).charAt(0), LocalDate.parse(rs.getString(5)),LocalDate.parse(rs.getString(6))));
                }
            } catch (SQLException e) {
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
                    promisossorysNotes.add(new PromissoryNote(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4).charAt(0), LocalDate.parse(rs.getString(5)),LocalDate.parse(rs.getString(6))));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void setUsers(){
        User nodoA = new User("nodoA", "12345", "emmc316", "3309", User.NodeA);
        User nodoB = new User("nodoB","1234","irving","3306", User.NodeB);
        User nodoC = new User("nodoC","1234","parra","3306", User.NodeC);
        //only for debug  User nodoX = new User("NodoX", "12345", "emmcdev", "3306");
        this.users.add((nodoA));
        this.users.add((nodoB));
        this.users.add((nodoC));
    }
}
