package model;

public interface IOperations {
    public void selectInversors(String key);
    public Inversor selectInversorByRFC(String key,String rfc);
    public void selectContracts(String key);
    public Contract selectContractsByClv(String key,String rfc);
    public void selectPromissorys(String key);
    public  void selectPromissorysByRFC(String key, String rfc);
    public void selectByPromissoryDates(String key,String date1, String date2);
}
