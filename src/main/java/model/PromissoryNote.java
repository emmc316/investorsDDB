package model;

import java.time.LocalDate;

public class PromissoryNote {

    private String promissoryNoteCode;
    private String contractCode;
    private char typeOfRate;
    private LocalDate issuanceDate;
    private LocalDate maturityDate;

    public PromissoryNote(String promissoryNoteCode, String contractCode, char typeOfRate, LocalDate issuanceDate, LocalDate maturityDate) {
        this.promissoryNoteCode = promissoryNoteCode;
        this.contractCode = contractCode;
        this.typeOfRate = typeOfRate;
        this.issuanceDate = issuanceDate;
        this.maturityDate = maturityDate;
    }

    public String getPromissoryNoteCode() {
        return promissoryNoteCode;
    }

    public void setPromissoryNoteCode(String promissoryNoteCode) {
        this.promissoryNoteCode = promissoryNoteCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public char getTypeOfRate() {
        return typeOfRate;
    }

    public void setTypeOfRate(char typeOfRate) {
        this.typeOfRate = typeOfRate;
    }

    public LocalDate getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(LocalDate issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    @Override
    public String toString() {
        return "PromissoryNote{" +
                "promissoryNoteCode='" + promissoryNoteCode + '\'' +
                ", contractCode='" + contractCode + '\'' +
                ", typeOfRate=" + typeOfRate +
                ", issuanceDate=" + issuanceDate +
                ", maturityDate=" + maturityDate +
                '}';
    }
}
