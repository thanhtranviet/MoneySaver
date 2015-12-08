package com.k22.nhom1.moneysaver.database.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thanh on 08/12/2015.
 */
public class HangMucThuDinhKy extends HangMucThu {
    Date ngayBatDau;
    Date ngayKetThuc;
    String kyHan;
    Integer soTien;
    Set<KhoanThu> cacKhoanThu;

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getKyHan() {
        return kyHan;
    }

    public void setKyHan(String kyHan) {
        this.kyHan = kyHan;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }

    public Set<KhoanThu> getCacKhoanThu() {
        return cacKhoanThu;
    }

    public void setCacKhoanThu(Set<KhoanThu> cacKhoanThu) {
        this.cacKhoanThu = cacKhoanThu;
    }

    public boolean addKhoanThu(KhoanThu khoanThu) {
        if (cacKhoanThu == null) cacKhoanThu = new HashSet<>();
        return this.cacKhoanThu.add(khoanThu);
    }
}
