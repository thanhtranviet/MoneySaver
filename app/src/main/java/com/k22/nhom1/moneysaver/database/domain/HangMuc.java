package com.k22.nhom1.moneysaver.database.domain;

/**
 * Created by thanh on 08/12/2015.
 */
public class HangMuc {
    String tenHangMuc;
    String moTa;

    public HangMuc() {

    }

    public HangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
