package model;

public class Rate {
    private char typeOfRate;
    private double grossInteres;
    private double withheldTax;
    private double netInterest;

    public Rate() {
    }

    public Rate(char typeOfRate, double grossInteres, double withheldTax, double netInterest) {
        this.typeOfRate = typeOfRate;
        this.grossInteres = grossInteres;
        this.withheldTax = withheldTax;
        this.netInterest = netInterest;
    }

    public char getTypeOfRate() {
        return typeOfRate;
    }

    public void setTypeOfRate(char typeOfRate) {
        this.typeOfRate = typeOfRate;
    }

    public double getGrossInteres() {
        return grossInteres;
    }

    public void setGrossInteres(double grossInteres) {
        this.grossInteres = grossInteres;
    }

    public double getWithheldTax() {
        return withheldTax;
    }

    public void setWithheldTax(double withheldTax) {
        this.withheldTax = withheldTax;
    }

    public double getNetInterest() {
        return netInterest;
    }

    public void setNetInterest(double netInterest) {
        this.netInterest = netInterest;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "typeOfRate=" + typeOfRate +
                ", grossInteres=" + grossInteres +
                ", withheldTax=" + withheldTax +
                ", netInterest=" + netInterest +
                '}';
    }
}
