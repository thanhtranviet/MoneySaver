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
    Set<KhoanThu> cacKhoanThu;
    Set<KhoanChi> cacKhoanChi;
    Set<KhoanVay> cacKhoanVay;
    Set<KhoanChoVay> cacKhoanChoVay;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }
    public TaiKhoan(String tenTaiKhoan, String loaiTaiKhoan, Integer soKhoiTao) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.soKhoiTao = soKhoiTao;
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

        return soKhoiTao + getTotalIncome() + getTotalBorrow() - getTotalExpense() - getTotalLoan();
    }

    public Integer getTotalIncome() {
        Integer result = 0;
        if (cacKhoanThu != null) {
            for (GiaoDich gd : cacKhoanThu) {
                result += gd.getSoTien();
            }
        }
        return result;
    }

    public Integer getTotalExpense() {
        Integer result = 0;
        if (cacKhoanChi != null) {
            for (GiaoDich gd : cacKhoanChi) {
                result += gd.getSoTien();
            }
        }
        return result;
    }

    public Integer getTotalBorrow() {
        Integer result = 0;
        if (cacKhoanVay != null) {
            for (GiaoDich gd : cacKhoanVay) {
                result += gd.getSoTien();
            }
        }
        return result;
    }

    public Integer getTotalLoan() {
        Integer result = 0;
        if (cacKhoanChoVay != null) {
            for (GiaoDich gd : cacKhoanChoVay) {
                result += gd.getSoTien();
            }
        }
        return result;
    }
    public Set<KhoanThu> getCacKhoanThu() {
        return cacKhoanThu;
    }

    public Set<KhoanChi> getCacKhoanChi() {
        return cacKhoanChi;
    }

    public Set<KhoanVay> getCacKhoanVay() {
        return cacKhoanVay;
    }

    public Set<KhoanChoVay> getCacKhoanChoVay() {
        return cacKhoanChoVay;
    }

    public boolean addKhoanThu(KhoanThu khoanThu) {
        if (cacKhoanThu == null) cacKhoanThu = new HashSet<>();
        boolean success = this.cacKhoanThu.add(khoanThu);
        return success;
    }

    public boolean deleteKhoanThu(KhoanThu khoanThu) {
        if (cacKhoanThu == null) cacKhoanThu = new HashSet<>();
        boolean success = this.cacKhoanThu.remove(khoanThu);
        return success;
    }
    public boolean addKhoanChi(KhoanChi khoanChi) {
        if (cacKhoanChi == null) cacKhoanChi = new HashSet<>();
        boolean success = this.cacKhoanChi.add(khoanChi);
        return success;
    }

    public boolean deleteKhoanChi(KhoanChi khoanChi) {
        if (cacKhoanChi == null) cacKhoanChi = new HashSet<>();
        boolean success = this.cacKhoanChi.remove(khoanChi);
        return success;
    }

    public boolean addKhoanVay(KhoanVay khoanVay) {
        if (cacKhoanVay == null) cacKhoanVay = new HashSet<>();
        boolean success = this.cacKhoanVay.add(khoanVay);
        return success;
    }

    public boolean deleteKhoanVay(KhoanVay khoanVay) {
        if (cacKhoanVay == null) cacKhoanVay = new HashSet<>();
        boolean success = this.cacKhoanVay.remove(khoanVay);
        return success;
    }
    public boolean addKhoanChoVay(KhoanChoVay khoanChoVay) {
        if (cacKhoanChoVay == null) cacKhoanChoVay = new HashSet<>();
        boolean success = this.cacKhoanChoVay.add(khoanChoVay);
        return success;
    }

    public boolean deleteKhoanChoVay(KhoanChoVay khoanChoVay) {
        if (cacKhoanChoVay == null) cacKhoanChoVay = new HashSet<>();
        boolean success = this.cacKhoanChoVay.remove(khoanChoVay);
        return success;
    }

}
