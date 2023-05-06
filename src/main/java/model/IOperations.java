package model;

public interface IOperations {
    public void selectInversors(int pos);
    public Inversor selectInversorByRFC(int pos,String rfc);
    public void selectContracts(int pos);
    public Contract selectContractsByClv(int pos,String rfc);
    public void selectPromissorys(int pos);
    public  void selectPromissorysByRFC(int pos, String rfc);
    public void selectByPromissoryDates(int pos,String date1, String date2);
}
