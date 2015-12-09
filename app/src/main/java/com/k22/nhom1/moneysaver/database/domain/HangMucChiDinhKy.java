package com.k22.nhom1.moneysaver.database.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thanh on 08/12/2015.
 */
public class HangMucChiDinhKy extends HangMucChi {
    Date ngayBatDau;
    Date ngayKetThuc;
    String kyHan;
    Integer soTien;
    Set<KhoanChi> cacKhoanChi;

    public HangMucChiDinhKy() {
        super();
    }

    public HangMucChiDinhKy(String tenHangMuc) {
        super(tenHangMuc);
    }

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

    public Set<KhoanChi> getCacKhoanChi() {
        return cacKhoanChi;
    }

    public void setCacKhoanChi(Set<KhoanChi> cacKhoanChi) {
        this.cacKhoanChi = cacKhoanChi;
    }

    public boolean addKhoanChi(KhoanChi khoanChi) {
        if (cacKhoanChi == null) cacKhoanChi = new HashSet<>();
        return this.cacKhoanChi.add(khoanChi);
    }
}
