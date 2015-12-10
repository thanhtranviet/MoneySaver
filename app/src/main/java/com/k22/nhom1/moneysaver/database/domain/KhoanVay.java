package com.k22.nhom1.moneysaver.database.domain;

import java.util.Date;

/**
 * Created by thanh on 08/12/2015.
 */
public class KhoanVay extends GiaoDich {
    Date ngayDuKienTra;
    Date ngayThucTeTra;
    String chuNo;
    String trangThai;

    TaiKhoan taiKhoan;

    public KhoanVay() {
        super();
    }

    public KhoanVay(String name) {
        super(name);
    }
    public Date getNgayDuKienTra() {
        return ngayDuKienTra;
    }

    public void setNgayDuKienTra(Date ngayDuKienTra) {
        this.ngayDuKienTra = ngayDuKienTra;
    }

    public Date getNgayThucTeTra() {
        return ngayThucTeTra;
    }

    public void setNgayThucTeTra(Date ngayThucTeTra) {
        this.ngayThucTeTra = ngayThucTeTra;
    }

    public String getChuNo() {
        return chuNo;
    }

    public void setChuNo(String chuNo) {
        this.chuNo = chuNo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
}
