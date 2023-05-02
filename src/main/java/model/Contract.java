package model;

public class Contract {

    private String codeContract;
    private String branchCode;
    private String rfcInversor;
    private double totalMount;
    private boolean status;

    public Contract(String codeContract, String branchCode, String rfcInversor, double totalMount, boolean status) {
        this.codeContract = codeContract;
        this.branchCode = branchCode;
        this.rfcInversor = rfcInversor;
        this.totalMount = totalMount;
        this.status = status;
    }

    public String getCodeContract() {
        return codeContract;
    }

    public void setCodeContract(String codeContract) {
        this.codeContract = codeContract;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getRfcInversor() {
        return rfcInversor;
    }

    public void setRfcInversor(String rfcInversor) {
        this.rfcInversor = rfcInversor;
    }

    public double getTotalMount() {
        return totalMount;
    }

    public void setTotalMount(double totalMount) {
        this.totalMount = totalMount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "codeContract='" + codeContract + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", rfcInversor='" + rfcInversor + '\'' +
                ", totalMount=" + totalMount +
                ", status=" + status +
                '}';
    }
}
