package com.k22.nhom1.moneysaver.database.domain;

import java.util.Date;

/**
 * Created by thanh on 08/12/2015.
 */
public class GiaoDich {
    public static final String KHOAN_THU = "Income";
    public static final String KHOAN_CHI = "Expense";
    public static final String KHOAN_VAY = "Borrow";
    public static final String KHOAN_CHOVAY = "Loan";
    String tenGiaoDich;
    Date ngayGiaoDich;
    Integer soTien;
    String ghiChu;

    public GiaoDich() {
    }

    public GiaoDich(String name) {
        this.tenGiaoDich = name;
    }

    public String getTenGiaoDich() {
        return tenGiaoDich;
    }

    public void setTenGiaoDich(String tenGiaoDich) {
        this.tenGiaoDich = tenGiaoDich;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
