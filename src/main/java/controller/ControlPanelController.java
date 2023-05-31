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
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


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
                pane.getModelInversionista().setRowCount(0);
                if(!pane.getCampoRFCInversionistas().isEnabled()) {
                    selectInversors("A");
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
                    selectContracts("A");
                    selectContracts("B");
                    selectContracts("C");
                    for (Contract ctc : contracts) {
                        String status = "Activo";
                        if(!ctc.isStatus()){
                            status = "Concluido";
                        }
                        pane.getModelContrato().addRow(new Object[]{ctc.getCodeContract(), ctc.getBranchCode(), ctc.getRfcInversor(), ctc.getTotalMount(), status});
                    }
                }
                else{
                    selectInformationReference("A", pane.getCampoRFCContratos().getText());
                    Contract ctc = selectContractsByClv(informationReference,pane.getCampoRFCContratos().getText());
                    pane.getModelContrato().addRow(new Object[]{ctc.getCodeContract(),ctc.getBranchCode(),ctc.getRfcInversor(),ctc.getTotalMount()});
                }
                contracts.clear();
                break;
            }

            case "promissoryNotes":{
                pane.getModelPagare().setRowCount(0);
                if(!pane.getFechaFinal().isEnabled() && !pane.getFechaFinal().isEnabled() && !pane.getCampoRFCPagares().isEnabled()) {
                    selectPromissorys("A");
                    selectPromissorys("B");
                    selectPromissorys("C");
                    for (PromissoryNote pro : promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
                    }
                }
                else if(pane.getCampoRFCPagares().isEnabled() && !pane.getFechaInicial().isEnabled() && !pane.getFechaFinal().isEnabled()) {
                    selectInformationReference("A", pane.getCampoRFCContratos().getText());
                    selectPromissorysByRFC(informationReference, this.pane.getCampoRFCPagares().getText());
                    for (PromissoryNote pro : promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(), pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
                    }
                }

                else{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    selectByPromissoryDates("A",dateFormat.format(pane.getFechaInicial().getDate()).toString(),dateFormat.format(pane.getFechaFinal().getDate()).toString());
                    selectByPromissoryDates("B",dateFormat.format(pane.getFechaInicial().getDate()).toString(),dateFormat.format(pane.getFechaFinal().getDate()).toString());
                    selectByPromissoryDates("C",dateFormat.format(pane.getFechaInicial().getDate()).toString(),dateFormat.format(pane.getFechaFinal().getDate()).toString());
                    for(PromissoryNote pro: promisossorysNotes) {
                        pane.getModelPagare().addRow(new Object[]{pro.getPromissoryNoteCode(), pro.getContractCode(),  pro.getTypeOfRate(), pro.getIssuanceDate(), pro.getMaturityDate()});
     }
                }
                promisossorysNotes.clear();
                break;
            }
            case "agregarII":{
                String rfc = pane.getCampoRFCII().getText();
                String nombre = pane.getCampoNombreII().getText();
                String telefono = pane.getCampoTelefonoII().getText();
                String direccion = pane.getCampoDireccionII().getText();
                String correo = pane.getCampoCorreoII().getText();
                String tipoSeleccionado = (String)(pane.getComboTipoPersonaII().getSelectedItem());
                boolean tipoPersona = true;
                if(tipoSeleccionado.equals("Moral")){
                    tipoPersona = false;
                }
                try {
                    comprobarOperacion(insertToInversors("A",rfc,nombre,telefono,direccion,correo,tipoPersona));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "agregarIC":{
                String rfcinversionista = pane.getCampoRCFIC().getText();
                selectInformationReference("A", rfcinversionista);
                int clvcontrato = Integer.parseInt(pane.getCampoContratoIC().getText());
                String clvsucursal = informationReference;
                double montototal = Double.parseDouble(pane.getCampoMontoIC().getText());
                try{
                    comprobarOperacion(insertToContracts(informationReference,clvcontrato,clvsucursal,rfcinversionista,montototal,true));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "agregarIP":{
                String rfc = pane.getCampoRFCIP().getText();
                selectInformationReference("A", rfc);
                int clvpagare = Integer.parseInt(pane.getCampoPagareIP().getText());
                int clvcontrato = Integer.parseInt(pane.getCampoContratoIP().getText());
                String tipotasa = (String)(pane.getComboTasaIP().getSelectedItem());
                Date date = new Date();
                String fechaemision = new SimpleDateFormat("yyyy-MM-dd").format(date);
                String fechavencimiento = new SimpleDateFormat("yyyy-MM-dd").format(pane.getFechaVencimiento().getDate());
                try{
                    comprobarOperacion(insertToPromisorys(informationReference,clvpagare,clvcontrato,tipotasa,fechaemision,fechavencimiento));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }

                break;
            }
            case "actualizarAI":{
                String rfc = pane.getCampoRFCAI().getText();
                String telefono = pane.getCampoTelefonoAI().getText();
                String direccion = pane.getCampoDireccionAI().getText();
                String correo = pane.getCampoCorreoAI().getText();
                try{
                    comprobarOperacion(updateToInversors("A",rfc,telefono,direccion,correo));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "actualizarAC":{
                String rfc = pane.getCampoRFCAC().getText();
                selectInformationReference("A",rfc);
                int clvcontrato = Integer.parseInt(pane.getCampoContratoAC().getText());
                double monto = Double.parseDouble(pane.getCampoMontoAC().getText());
                boolean status = true;
                String seleccion = (String)(pane.getComboStatusAC().getSelectedItem());
                if(seleccion.equals("Concluido")){
                    status = false;
                }
                try{
                    comprobarOperacion(updateToContracts(informationReference,clvcontrato,monto,status));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "actualizarAP":{
                String rfc = pane.getCampoRFCAP().getText();
                selectInformationReference("A",rfc);
                int clvpagare = Integer.parseInt(pane.getCampoPagareAP().getText());
                String tipoTasa = (String)(pane.getComboTasa().getSelectedItem());
                try{
                    comprobarOperacion(updateToPromisorys(informationReference,clvpagare,tipoTasa));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "eliminarEI":{
                String rfc = pane.getCampoRFCEI().getText();
                try{
                    comprobarOperacion(deleteToInversors("A",rfc));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "eliminarEC":{
                String rfc = pane.getCampoRFCEC().getText();
                selectInformationReference("A",rfc);
                int contrato = Integer.parseInt(pane.getCampoContratoEC().getText());
                try{
                    comprobarOperacion(deleteToContracts(informationReference,contrato));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
                break;
            }
            case "eliminarEP":{
                String rfc = pane.getCampoRFCEP().getText();
                selectInformationReference("A",rfc);
                int pagare = Integer.parseInt(pane.getCampoPagareEP().getText());
                try{
                    comprobarOperacion(deleteToPromisorys(informationReference,pagare));
                } catch(SQLIntegrityConstraintViolationException ex){
                    ex.printStackTrace();
                }
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
                    if(rs.getInt(6)==1){
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
                    contracts.add(new Contract(key + rs.getString(1) ,rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getBoolean(5)));
                }
                System.out.println("Consulta desde el nodo principal");
            } catch (SQLException | NullPointerException e) {
                String keyR = "R" + key;
                try {
                    Statement stmt = nodes.getNodes().get(keyR).connection.createStatement();
                    Result rs = (Result) stmt.executeQuery(operations.getOperations().get(Operations.SELECT_CONTRACTS).getQuery());
                    while (rs.next()) {
                        contracts.add(new Contract(key + rs.getString(1) ,rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getBoolean(5)));
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
                        contractLocal = new Contract(key + rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getBoolean(5));
                    }
                    System.out.println("Consulta desde el nodo principal");
                } catch (SQLException | NullPointerException e) {
                    String keyR = "R" + key;
                    try {
                        ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_CONTRACTS_BY_CLV).getQuery());
                        preparedStatement.setString(1, clv);
                        Result rs = (Result) preparedStatement.executeQuery();
                        while (rs.next()) {
                            contractLocal = new Contract(key + rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getBoolean(5));
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
                    promisossorysNotes.add(new PromissoryNote(key + rs.getString(1),rs.getString(2),rs.getString(3).charAt(0),LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5))));
                }
                System.out.println("Consulta desde el nodo principal");
            } catch (SQLException | NullPointerException e) {
                String keyR = "R" + key;
                try {
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS).getQuery());
                    Result rs = (Result) preparedStatement.executeQuery();
                    while (rs.next()) {
                        promisossorysNotes.add(new PromissoryNote(key + rs.getString(1),rs.getString(2),rs.getString(3).charAt(0),LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5))));
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
                        promisossorysNotes.add(new PromissoryNote(key + rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
                    }
                    System.out.println("Consulta desde el nodo principal");
                } catch (SQLException | NullPointerException e) {
                    String keyR = "R" + key;
                    try {
                        ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(keyR).connection.prepareStatement(operations.getOperations().get(Operations.SELECT_PROMISORYS_BY_RFC).getQuery());
                        preparedStatement.setString(1, rfc);
                        Result rs = (Result) preparedStatement.executeQuery();
                        while (rs.next()) {
                            promisossorysNotes.add(new PromissoryNote(key + rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
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
                        promisossorysNotes.add(new PromissoryNote(key + rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
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
                            promisossorysNotes.add(new PromissoryNote(key + rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), LocalDate.parse(rs.getString(4)), LocalDate.parse(rs.getString(5))));
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

    public boolean insertToInversors(String key, String rfc, String nombre, String telefono, String direccion, String correo, boolean tipoPersona) throws SQLIntegrityConstraintViolationException {
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.INSERT_TO_INVERSORS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.INSERT_TO_INVERSORS).getQuery());
                    preparedStatement.setString(1, rfc);
                    preparedStatement.setString(2, nombre);
                    preparedStatement.setString(3, telefono);
                    preparedStatement.setString(4, direccion);
                    preparedStatement.setString(5, correo);
                    preparedStatement.setBoolean(6, tipoPersona);
                    preparedStatement.setString(7, key);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.INSERT_TO_INVERSORS).getQuery());
                            preparedStatement.setString(1, rfc);
                            preparedStatement.setString(2, nombre);
                            preparedStatement.setString(3, telefono);
                            preparedStatement.setString(4, direccion);
                            preparedStatement.setString(5, correo);
                            preparedStatement.setBoolean(6, tipoPersona);
                            preparedStatement.setString(7, key);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        } else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean insertToContracts(String key, int clvcontrato, String clvsucursal, String rfcinversionista, double montototal, boolean status) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.INSERT_TO_CONTRACTS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.INSERT_TO_CONTRACTS).getQuery());
                    preparedStatement.setInt(1, clvcontrato);
                    preparedStatement.setString(2, clvsucursal);
                    preparedStatement.setString(3, rfcinversionista);
                    preparedStatement.setDouble(4, montototal);
                    preparedStatement.setBoolean(5, status);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.INSERT_TO_CONTRACTS).getQuery());
                            preparedStatement.setInt(1, clvcontrato);
                            preparedStatement.setString(2, clvsucursal);
                            preparedStatement.setString(3, rfcinversionista);
                            preparedStatement.setDouble(4, montototal);
                            preparedStatement.setBoolean(5, status);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        } else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean insertToPromisorys(String key, int clvpagare, int clvcontrato, String tipotasa, String fechaemision, String fechavencimiento) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.INSERT_TO_PROMISORYS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.INSERT_TO_PROMISORYS).getQuery());
                    preparedStatement.setInt(1, clvpagare);
                    preparedStatement.setInt(2, clvcontrato);
                    preparedStatement.setString(3, tipotasa);
                    preparedStatement.setString(4, fechaemision);
                    preparedStatement.setString(5, fechavencimiento);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.INSERT_TO_PROMISORYS).getQuery());
                            preparedStatement.setInt(1, clvpagare);
                            preparedStatement.setInt(2, clvcontrato);
                            preparedStatement.setString(3, tipotasa);
                            preparedStatement.setString(4, fechaemision);
                            preparedStatement.setString(5, fechavencimiento);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        } else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean updateToInversors(String key, String rfc, String telefono, String direccion, String correo) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.UPDATE_TO_INVERSORS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.UPDATE_TO_INVERSORS).getQuery());
                    preparedStatement.setString(1, telefono);
                    preparedStatement.setString(2, direccion);
                    preparedStatement.setString(3, correo);
                    preparedStatement.setString(4, rfc);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.UPDATE_TO_INVERSORS).getQuery());
                            preparedStatement.setString(1, telefono);
                            preparedStatement.setString(2, direccion);
                            preparedStatement.setString(3, correo);
                            preparedStatement.setString(4, rfc);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        } else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean updateToContracts(String key, int contrato, double monto, boolean status) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.UPDATE_TO_CONTRACTS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.UPDATE_TO_CONTRACTS).getQuery());
                    preparedStatement.setDouble(1, monto);
                    preparedStatement.setBoolean(2, status);
                    preparedStatement.setInt(3, contrato);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.UPDATE_TO_CONTRACTS).getQuery());
                            preparedStatement.setDouble(1, monto);
                            preparedStatement.setBoolean(2, status);
                            preparedStatement.setInt(3, contrato);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        } else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean updateToPromisorys(String key, int pagare, String tasa) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.UPDATE_TO_PROMISORYS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.UPDATE_TO_PROMISORYS).getQuery());
                    preparedStatement.setString(1, tasa);
                    preparedStatement.setInt(2, pagare);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.UPDATE_TO_PROMISORYS).getQuery());
                            preparedStatement.setString(1, tasa);
                            preparedStatement.setInt(2, pagare);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        } else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean deleteToInversors(String key, String rfc) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.DELETE_TO_INVERSORS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.DELETE_TO_INVERSORS).getQuery());
                    preparedStatement.setString(1, rfc);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.DELETE_TO_INVERSORS).getQuery());
                            preparedStatement.setString(1, rfc);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        }
                        else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean deleteToContracts(String key, int contrato) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.DELETE_TO_CONTRACTS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.DELETE_TO_CONTRACTS).getQuery());
                    preparedStatement.setInt(1, contrato);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.DELETE_TO_CONTRACTS).getQuery());
                            preparedStatement.setInt(1, contrato);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        }
                        else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean deleteToPromisorys(String key, int pagare) throws SQLIntegrityConstraintViolationException{
        nodes.getNodes().get(key).connect();
        if (operations.getOperations().get(Operations.DELETE_TO_PROMISORYS).validateQuerieByNode(nodes.getNodes().get(key).user.getNode())) {
            if(nodes.getNodes().get(key).isStatus()){
                try {
                    nodes.getNodes().get(key).connection.setAutoCommit(false);
                    ClientPreparedStatement preparedStatement = (ClientPreparedStatement) nodes.getNodes().get(key).connection.prepareStatement(operations.getOperations().get(Operations.DELETE_TO_PROMISORYS).getQuery());
                    preparedStatement.setInt(1, pagare);
                    int resultado = preparedStatement.executeUpdate();
                    if(resultado > 0){
                        nodes.getNodes().get("R" + key).connect();
                        if(nodes.getNodes().get("R" + key).isStatus()){
                            nodes.getNodes().get("R" + key).connection.setAutoCommit(false);
                            preparedStatement = (ClientPreparedStatement) nodes.getNodes().get("R" + key).connection.prepareStatement(operations.getOperations().get(Operations.DELETE_TO_PROMISORYS).getQuery());
                            preparedStatement.setInt(1, pagare);
                            resultado = preparedStatement.executeUpdate();
                            if(resultado > 0){
                                nodes.getNodes().get("R" + key).connection.commit();
                                nodes.getNodes().get(key).connection.commit();
                                return true;
                            } else {
                                nodes.getNodes().get("R" + key).connection.rollback();
                                nodes.getNodes().get(key).connection.rollback();
                                return false;
                            }
                        }
                        else {
                            nodes.getNodes().get(key).connection.rollback();
                            return false;
                        }
                    } else {
                        nodes.getNodes().get(key).connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }
        return false;
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
    public void comprobarOperacion(boolean completado){
        if(completado){
            JOptionPane.showMessageDialog(pane,"Operacion completada","Exito",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(pane,"No se pudo completar la transaccion","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}

