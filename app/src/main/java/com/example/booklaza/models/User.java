package com.example.booklaza.models;

public class User {

    private String fName;
    private String lName;
    private String pNumber;
    private String email;
    private String pass;

    public User (){}

    public User(String fName, String lName, String pNumber, String email, String pass) {
        this.fName = fName;
        this.lName = lName;
        this.pNumber = pNumber;
        this.email = email;
        this.pass = pass;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
