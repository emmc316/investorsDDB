package model;

public class Inversor {

    private String rfc;
    private String full_name;
    private String phoneNumber;
    private String address;
    private String email;
    private String type;

    public Inversor() {
    }

    public Inversor(String rfc, String full_name, String phoneNumber, String address, String email, String type) {
        this.rfc = rfc;
        this.full_name = full_name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.type = type;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Inversor{" +
                "rfc='" + rfc + '\'' +
                ", full_name='" + full_name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
