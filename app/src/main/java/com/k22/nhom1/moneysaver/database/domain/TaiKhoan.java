package com.k22.nhom1.moneysaver.database.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by thanh on 08/12/2015.
 */
public class TaiKhoan {
    String tenTaiKhoan;
    String loaiTaiKhoan;
    Integer soKhoiTao;
    Integer soDuHienTai;
    Set<KhoanThu> cacKhoanThu;
    Set<KhoanChi> cacKhoanChi;
    Set<KhoanVay> cacKhoanVay;
    Set<KhoanChoVay> cacKhoanChoVay;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String loaiTaiKhoan, Integer soKhoiTao) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.soKhoiTao = soKhoiTao;
        this.soDuHienTai = soKhoiTao;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public Integer getSoKhoiTao() {
        return soKhoiTao;
    }

    public void setSoKhoiTao(Integer soKhoiTao) {
        this.soKhoiTao = soKhoiTao;
    }

    public Integer getSoDuHienTai() {
        return soDuHienTai;
    }

    public void setSoDuHienTai(Integer soDuHienTai) {
        this.soDuHienTai = soDuHienTai;
    }

    public Set<KhoanThu> getCacKhoanThu() {
        return cacKhoanThu;
    }

    public void setCacKhoanThu(Set<KhoanThu> cacKhoanThu) {
        this.cacKhoanThu = cacKhoanThu;
    }

    public Set<KhoanChi> getCacKhoanChi() {
        return cacKhoanChi;
    }

    public void setCacKhoanChi(Set<KhoanChi> cacKhoanChi) {
        this.cacKhoanChi = cacKhoanChi;
    }

    public Set<KhoanVay> getCacKhoanVay() {
        return cacKhoanVay;
    }

    public void setCacKhoanVay(Set<KhoanVay> cacKhoanVay) {
        this.cacKhoanVay = cacKhoanVay;
    }

    public Set<KhoanChoVay> getCacKhoanChoVay() {
        return cacKhoanChoVay;
    }

    public void setCacKhoanChoVay(Set<KhoanChoVay> cacKhoanChoVay) {
        this.cacKhoanChoVay = cacKhoanChoVay;
    }

    public boolean addKhoanThu(KhoanThu khoanThu) {
        if (cacKhoanThu == null) cacKhoanThu = new HashSet<>();
        boolean success = this.cacKhoanThu.add(khoanThu);
        if (success) {
            soDuHienTai += khoanThu.getSoTien();
        }
        return success;
    }

    public boolean addKhoanChi(KhoanChi khoanChi) {
        if (cacKhoanChi == null) cacKhoanChi = new HashSet<>();
        boolean success = this.cacKhoanChi.add(khoanChi);
        if (success) {
            soDuHienTai -= khoanChi.getSoTien();
        }
        return success;
    }

    public boolean addKhoanVay(KhoanVay khoanVay) {
        if (cacKhoanVay == null) cacKhoanVay = new HashSet<>();
        boolean success = this.cacKhoanVay.add(khoanVay);
        if (success) {
            soDuHienTai += khoanVay.getSoTien();
        }
        return success;
    }

    public boolean addKhoanChoVay(KhoanChoVay khoanChoVay) {
        if (cacKhoanChoVay == null) cacKhoanChoVay = new HashSet<>();
        boolean success = this.cacKhoanChoVay.add(khoanChoVay);
        if (success) {
            soDuHienTai -= khoanChoVay.getSoTien();
        }
        return success;
    }
}
