package controller;

import jdk.nashorn.internal.scripts.JO;
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


public class ControlPanelController implements ItemListener, IOperations, ActionListener {
    private ControlPane pane;
    private boolean isConnected = false;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Inversor> inversors = new ArrayList<Inversor>();
    private ArrayList<Branch> branches = new ArrayList<Branch>();
    private ArrayList<Rate> rates = new ArrayList<Rate>();
    private ArrayList<Contract> contracts = new ArrayList<Contract>();
    private ArrayList<PromissoryNote> promisossorysNotes = new ArrayList<PromissoryNote>();
    private String informationReference = "";
    private Operations operations = new Operations();
    private Nodes nodes = null;
    private String nodoId = "";
    public ControlPanelController(ControlPane pane) {
        this.pane = pane;
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
                System.out.println(this.nodoId);
                pane.getModelInversionista().setRowCount(0);
                if(!pane.getCampoRFCInversionistas().isEnabled()) {
                    selectInversors(this.nodoId);
                    for (Inversor inv : inversors) {
                        pane.getModelInversionista().addRow(new Object[]{inv.getRfc(), inv.getFull_name(), inv.getPhoneNumber(), inv.getAddress(), inv.getEmail(), inv.getType()});

                    }
                }
                else{
                    Inversor inv = selectInversorByRFC(nodoId,pane.getCampoRFCInversionistas().getText());
                    pane.getModelInversionista().addRow(new Object[]{inv.getRfc(),inv.getFull_name(),inv.getPhoneNumber(),inv.getAddress(),inv.getEmail(),inv.getType()});
                }
                inversors.clear();
                break;
            }

            case "contracts":{
                pane.getModelContrato().setRowCount(0);
                if(!pane.getCampoRFCContratos().isEnabled()) {
                    //selectContracts("A");
                    selectContracts("B");
                    selectContracts("C");
                    for (Contract ctc : contracts) {
                        pane.getModelContrato().addRow(new Object[]{ctc.getCodeContract(), ctc.getBranchCode(), ctc.getRfcInversor(), ctc.getTotalMount()});
                    }
                }
                else{
                    selectInformationReference(this.nodoId, pane.getCampoRFCContratos().getText());
                    Contract ctc = selectContractsByClv(informationReference,pane.getCampoRFCContratos().getText());
                    pane.getModelContrato().addRow(new Object[]{ctc.getCodeContract(),ctc.getBranchCode(),ctc.getRfcInversor(),ctc.getTotalMount()});
                }
                contracts.clear();
                break;
            }

            case "promissoryNotes":{
                pane.getModelPagare().setRowCount(0);
                if(!pane.getFechaFinal().isEnabled() && !pane.getFechaFinal().isEnabled() && !pane.getCampoRFCPagares().isEnabled()) {
                    //selectPromissorys("A");
                    selectPromissorys("B");
                    selectPromissorys("C");
                    for (PromissoryNote pro : promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
                    }
                }
                else if(pane.getCampoRFCPagares().isEnabled() && !pane.getFechaInicial().isEnabled() && !pane.getFechaFinal().isEnabled()) {
                    selectInformationReference(this.nodoId, pane.getCampoRFCContratos().getText());
                    selectPromissorysByRFC(informationReference, this.pane.getCampoRFCPagares().getText());
                    for (PromissoryNote pro : promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
                    }
                }

                else{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //selectByPromissoryDates("A",dateFormat.format(pane.getFechaInicial().getDate()).toString(),dateFormat.format(pane.getFechaFinal().getDate()).toString());
                    selectByPromissoryDates("B",dateFormat.format(pane.getFechaInicial().getDate()).toString(),dateFormat.format(pane.getFechaFinal().getDate()).toString());
                    selectByPromissoryDates("C",dateFormat.format(pane.getFechaInicial().getDate()).toString(),dateFormat.format(pane.getFechaFinal().getDate()).toString());
                    for(PromissoryNote pro: promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(),  pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
     }
                }

                promisossorysNotes.clear();
                break;
            }
        }
    }

    @Override
    public void selectInversors(String key){
        String tipoPersona;
        if(operations.getOperations().get(Operations.SELECT_INVERSORS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            try {
                Statement stmt = nodes.getNodes().get(key).connection.createStatement();
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
    public Inversor selectInversorByRFC(String key,String rfc) {
        Inversor inversorLocal = null;
        if(!rfc.isEmpty()) {
            String typeInversor = "";
            if (operations.getOperations().get(Operations.SELECT_INVERSORS_BY_RFC).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_INVERSORS_BY_RFC).getQuery());
                    preparedStatement.setString(1, rfc);

                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        if (rs.getInt(6) == 0) {
                            typeInversor = "fisica";
                        } else {
                            typeInversor = "moral";
                        }
                        inversorLocal = new Inversor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), typeInversor);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(pane,"Error: RFC can not be empty!","Error: Empty Field",JOptionPane.ERROR_MESSAGE);
        }
        return inversorLocal;
    }

    @Override
    public void selectContracts(String key) {
        if (operations.getOperations().get(Operations.SELECT_CONTRACTS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            try {
                Statement stmt = nodes.getNodes().get(key).connection.createStatement();
                Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_CONTRACTS).getQuery());
                while (rs.next()) {
                    contracts.add(new Contract(rs.getString(1) ,rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getBoolean(5)));
                }
                System.out.println("Consulta desde el nodo principal");
            } catch (SQLException | NullPointerException e) {
                String keyR = "R" + key;
                try {
                    Statement stmt = nodes.getNodes().get(keyR).connection.createStatement();
                    Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_CONTRACTS).getQuery());
                    while (rs.next()) {
                        contracts.add(new Contract(rs.getString(1) ,rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getBoolean(5)));
                    }
                    System.out.println("Consulta desde el nodo respaldo");
                } catch (SQLException | NullPointerException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    @Override
    public Contract selectContractsByClv(String key,String clv) {
        Contract contractLocal = null;
        if(!clv.isEmpty()) {
            if (operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).getQuery());
                    preparedStatement.setString(1, clv);
                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        contractLocal = new Contract(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getBoolean(5));
                    }
                    System.out.println("Consulta desde el nodo principal");
                } catch (SQLException | NullPointerException e) {
                    String keyR = "R" + key;
                    try {
                        ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).getQuery());
                        preparedStatement.setString(1, clv);
                        Result rs = (Result) preparedStatement.executeQuery();
                        while (rs.next()) {
                            contractLocal = new Contract(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getBoolean(5));
                        }
                        System.out.println("Consulta desde el nodo secundario");
                    } catch (SQLException | NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(pane,"The RFC can no be empty!","Error: Field Empty",JOptionPane.ERROR_MESSAGE);
        }
        return contractLocal;
    }

    @Override
    public void selectPromissorys(String key) {
        if (operations.getOperations().get(Operations.SELECT_PROMISORYS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            try {
                ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS).getQuery());
                Result rs = (Result) preparedStatement.executeQuery();
                while (rs.next()) {
                    promisossorysNotes.add(new PromissoryNote(rs.getString(1),rs.getString(2),rs.getString(3).charAt(0),LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5))));
                }
                System.out.println("Consulta desde el nodo principal");
            } catch (SQLException | NullPointerException e) {
                String keyR = "R" + key;
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS).getQuery());
                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        promisossorysNotes.add(new PromissoryNote(rs.getString(1),rs.getString(2),rs.getString(3).charAt(0),LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5))));
                    }
                    System.out.println("Consulta desde el nodo secundario");
                } catch (SQLException | NullPointerException ex) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void selectPromissorysByRFC(String key, String rfc) {
        if(!rfc.isEmpty()) {
            if (operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_RFC).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_RFC).getQuery());
                    preparedStatement.setString(1, rfc);
                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        promisossorysNotes.add(new PromissoryNote(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
                    }
                    System.out.println("Consulta desde el nodo principal");
                } catch (SQLException | NullPointerException e) {
                    String keyR = "R" + key;
                    try {
                        ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_RFC).getQuery());
                        preparedStatement.setString(1, rfc);
                        Result rs = (Result) preparedStatement.executeQuery();
                        while (rs.next()) {
                            promisossorysNotes.add(new PromissoryNote(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
                        }
                        System.out.println("Consulta desde el nodo secundario");
                    } catch (SQLException | NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(pane,"The RFC can not be empty!","Error: Field Empty",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void selectByPromissoryDates(String key,String date1, String date2) {
        if((!date1.isEmpty() && !date2.isEmpty()) || (!date1.isEmpty() && date2.isEmpty()) || (date1.isEmpty() && !date2.isEmpty())) {
            if (operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_DATES).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_DATES).getQuery());
                    preparedStatement.setString(1, date1);
                    preparedStatement.setString(2, date2);
                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        promisossorysNotes.add(new PromissoryNote(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
                    }
                    System.out.println("Consulta desde el nodo principal");
                } catch (SQLException | NullPointerException e) {
                    String keyR = "R" + key;
                    try {
                        ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_DATES).getQuery());
                        preparedStatement.setString(1, date1);
                        preparedStatement.setString(2, date2);
                        Result rs = (Result) preparedStatement.executeQuery();
                        while (rs.next()) {
                            promisossorysNotes.add(new PromissoryNote(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
                        }
                        System.out.println("Consulta desde el nodo secundario");
                    } catch (SQLException | NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(pane,"The date fields can not be empty!","Error: Field Empty",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void selectInformationReference(String key, String rfc){
        if(!rfc.isEmpty()) {
            if (operations.getOperations().get(Operations.SELECT_INFORMATION_REFERENCE).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_INFORMATION_REFERENCE).getQuery());
                    preparedStatement.setString(1, rfc);
                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        informationReference = rs.getString(1);
                    }
                    System.out.println("Consulta desde el nodo principal");
                } catch (SQLException | NullPointerException e) {
                    String keyR = "R" + key;
                    try {
                        ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_INFORMATION_REFERENCE).getQuery());
                        preparedStatement.setString(1, rfc);
                        Result rs = (Result) preparedStatement.executeQuery();
                        while (rs.next()) {
                            informationReference = rs.getString(1);
                        }
                        System.out.println("Consulta desde el nodo secundario");
                    } catch (SQLException | NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(pane,"Error: RFC can not be empty!","Error: Empty Field",JOptionPane.ERROR_MESSAGE);
        }
    }

    public Nodes getNodes() {
        return nodes;
    }

    public void setNodes(Nodes nodes) {
        this.nodes = nodes;
    }

    public String getNodoId() {
        return nodoId;
    }

    public void setNodoId(String nodoId) {
        this.nodoId = nodoId;
    }
}

