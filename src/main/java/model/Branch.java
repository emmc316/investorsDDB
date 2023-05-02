package model;

public class Branch {

    private String branchCode;
    private String branchName;
    private String branchLocation;
    private String BranchManager;
    private String branchPhone;

    public Branch() {
    }

    public Branch(String branchCode, String branchName, String branchLocation, String branchManager, String branchPhone) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.branchLocation = branchLocation;
        BranchManager = branchManager;
        this.branchPhone = branchPhone;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public String getBranchManager() {
        return BranchManager;
    }

    public void setBranchManager(String branchManager) {
        BranchManager = branchManager;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchLocation='" + branchLocation + '\'' +
                ", BranchManager='" + BranchManager + '\'' +
                ", branchPhone='" + branchPhone + '\'' +
                '}';
    }
}
