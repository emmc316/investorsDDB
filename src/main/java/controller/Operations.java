package controller;
import model.Operation;

import java.util.HashMap;
import java.util.Map;

public class Operations {
    public final static String SELECT_INVERSORS = "selectInversors";
    public final static String SELECT_INVERSORS_BY_RFC = "selectInversorsByRFC";
    public final static String SELECT_CONTRACTS = "selectContracts";
    public final static String SELECT_CONTRACTS_BY_CLV = "selectContractsByCLV";
    public final static String SELECT_PROMISORYS = "selectPromisorys";
    public final static String SELECT_PROMISORYS_BY_RFC = "selectPromisorysByRFC";
    public final static String SELECT_PROMISORY_BY_DATE = "selectPromisoryByDate";
    public final static String SELECT_PROMISORYS_BY_DATES = "selectPromisorysByDates";
    public final static String SELECT_INFORMATION_REFERENCE = "selectInformationReference";
    public final static String INSERT_TO_INVERSORS = "insertToInversors";
    public final static String INSERT_TO_CONTRACTS = "insertToContracts";
    public final static String INSERT_TO_PROMISORYS = "insertToPromisorys";
    public final static String UPDATE_TO_INVERSORS = "updateToInversors";
    public final static String UPDATE_TO_CONTRACTS = "updateToContracts";
    public final static String UPDATE_TO_PROMISORYS = "updateToPromisorys";
    public final static String DELETE_TO_INVERSORS = "deleteToInversors";
    public final static String DELETE_TO_CONTRACTS = "deleteToContracts";
    public final static String DELETE_TO_PROMISORYS = "deleteToPromisorys";

    //select informacionsucursal from inversionista where rfcinversionista = 'NAPI990201N11';

    private HashMap<String,Operation> operations = new HashMap<String,Operation>();
    public Operations(){
        //SELECT OPERATIONS
        operations.put(Operations.SELECT_INVERSORS, new Operation(
                "SELECT * FROM inversionista",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_INVERSORS_BY_RFC,new Operation(
                "SELECT * FROM inversionista WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_CONTRACTS,new Operation(
                "SELECT * FROM contrato",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_CONTRACTS_BY_CLV,new Operation(
                "SELECT * FROM contrato WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_PROMISORYS,new Operation(
                "SELECT * FROM pagare",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_PROMISORYS_BY_RFC, new Operation(
                "SELECT DISTINCT C.* FROM contrato A JOIN inversionista B ON A.rfcinversionista = B.rfcinversionista JOIN pagare C ON A.clvcontrato = C.clvcontrato WHERE A.rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_PROMISORY_BY_DATE,new Operation(
                "SELECT * FROM pagare WHERE fechaemision >= ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_PROMISORYS_BY_DATES,new Operation(
                "SELECT * FROM pagare WHERE fechaemision >= ? AND fechaemision <= ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.SELECT_INFORMATION_REFERENCE,new Operation(
                "SELECT informacionsucursal FROM inversionista WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        //INSERT OPERATIONS
        operations.put(Operations.INSERT_TO_INVERSORS,new Operation(
                "INSERT INTO inversionista (rfcinversionista,nombreinversionista,telefonoinversionista,direccioninversionista,emailinverionista,tipoPersona,informacionsucursal) VALUES (?,?,?,?,?,?,?)",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.INSERT_TO_CONTRACTS,new Operation(
                "INSERT INTO contrato (clvcontrato,clvsucursal,rfcinversionista,montototal,status) VALUES (?,?,?,?,?)",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.INSERT_TO_PROMISORYS,new Operation(
                "INSERT INTO pagare (clvpagare,clvcontrato,tipotasa,fechaemision,fechavencimiento) VALUES (?,?,?,?,?)",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        //UPDATE OPERATIONS
        operations.put(Operations.UPDATE_TO_INVERSORS,new Operation(
                "UPDATE inversionista SET telefonoinversionista = ?, direccioninversionista = ?, emailinverionista = ? WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.UPDATE_TO_CONTRACTS,new Operation(
                "UPDATE contrato SET montototal = ?, status = ? WHERE clvcontrato = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.UPDATE_TO_PROMISORYS,new Operation(
                "UPDATE pagare SET tipotasa = ? WHERE clvpagare = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        //DELETE OPERATIONS
        operations.put(Operations.DELETE_TO_INVERSORS,new Operation(
                "DELETE FROM inversionista WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.DELETE_TO_CONTRACTS,new Operation(
                "DELETE FROM contrato WHERE clvcontrato = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));

        operations.put(Operations.DELETE_TO_PROMISORYS,new Operation(
                "DELETE FROM pagare WHERE clvpagare = ?",
                new String[]{Operation.NODE_A,Operation.NODE_RA,Operation.NODE_B,Operation.NODE_RB,Operation.NODE_C,Operation.NODE_RC}));
    }
    public Map<String, Operation> getOperations() {
        return operations;
    }
}
