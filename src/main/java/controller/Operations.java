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
    private HashMap<String,Operation> operations = new HashMap<String,Operation>();
    public Operations(){

        operations.put(Operations.SELECT_INVERSORS, new Operation(
                "SELECT * FROM inversionista",
                        new String[]{Operation.NODE_A}));

        operations.put(Operations.SELECT_INVERSORS_BY_RFC,new Operation(
                "SELECT * FROM inversionista WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A}));

        operations.put(Operations.SELECT_CONTRACTS,new Operation(
                "SELECT * FROM contrato",
                new String[]{Operation.NODE_A,Operation.NODE_B,Operation.NODE_C}));

        operations.put(Operations.SELECT_CONTRACTS_BY_CLV,new Operation(
                "SELECT * FROM contrato WHERE rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_B,Operation.NODE_C}));

        operations.put(Operations.SELECT_PROMISORYS,new Operation(
                "SELECT * FROM pagare",
                new String[]{Operation.NODE_A,Operation.NODE_B,Operation.NODE_C}));

        operations.put(Operations.SELECT_PROMISORYS_BY_RFC, new Operation(
                "SELECT DISTINCT C.* FROM contrato A JOIN inversionista B ON A.rfcinversionista = B.rfcinversionista JOIN pagare C ON A.clvcontrato = C.clvcontrato WHERE A.rfcinversionista = ?",
                new String[]{Operation.NODE_A,Operation.NODE_B,Operation.NODE_C}));

        operations.put(Operations.SELECT_PROMISORY_BY_DATE,new Operation(
                "SELECT * FROM pagare WHERE fechaemision >= ?",
                new String[]{Operation.NODE_A,Operation.NODE_B,Operation.NODE_C}));

        operations.put(Operations.SELECT_PROMISORYS_BY_DATES,new Operation(
                "SELECT * FROM pagare WHERE fechaemision >= ? AND fechavencimiento <= ?",
                new String[]{Operation.NODE_A,Operation.NODE_B,Operation.NODE_C}));
    }
    public Map<String, Operation> getOperations() {
        return operations;
    }
}
