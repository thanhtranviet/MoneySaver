package com.k22.nhom1.moneysaver.database.domain;

/**
 * Created by thanh on 08/12/2015.
 */
public class NguoiDung {
    String userName;
    String passWord;
    String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NguoiDung() {

    }

    public NguoiDung(String email, String passWord) {
        this.passWord = passWord;
        this.email = email;
    }

    public NguoiDung(String email, String passWord, String userName) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }
}
