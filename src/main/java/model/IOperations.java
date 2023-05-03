package model;

public interface IOperations {
    public void selectInversors(int pos);
    public void selectInversorByRFC(int pos,String rfc);
    public void selectContracts(int pos);
    public void selectContractsByClv(int pos,String clv);
    public void selectPromissorys(int pos);
    public void selectPromissoryByDate(int pos,String date);
    public void selectByPromissoryDates(int pos,String date1, String date2);
}
