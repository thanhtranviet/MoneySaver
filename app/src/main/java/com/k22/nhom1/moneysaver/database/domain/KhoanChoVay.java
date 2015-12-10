package com.k22.nhom1.moneysaver.database.domain;

import java.util.Date;

/**
 * Created by thanh on 08/12/2015.
 */
public class KhoanChoVay extends GiaoDich {
    Date ngayDuKienThu;
    Date ngayThucTeThu;
    String nguoiVay;
    String trangThai;
    TaiKhoan taiKhoan;
    HangMucChi hangMucChi;

    public KhoanChoVay() {
        super();
    }

    public KhoanChoVay(String name) {
        super(name);
    }
    public Date getNgayDuKienThu() {
        return ngayDuKienThu;
    }

    public void setNgayDuKienThu(Date ngayDuKienThu) {
        this.ngayDuKienThu = ngayDuKienThu;
    }

    public Date getNgayThucTeThu() {
        return ngayThucTeThu;
    }

    public void setNgayThucTeThu(Date ngayThucTeThu) {
        this.ngayThucTeThu = ngayThucTeThu;
    }

    public String getNguoiVay() {
        return nguoiVay;
    }

    public void setNguoiVay(String nguoiVay) {
        this.nguoiVay = nguoiVay;
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

    public HangMucChi getHangMucChi() {
        return hangMucChi;
    }

    public void setHangMucChi(HangMucChi hangMucChi) {
        this.hangMucChi = hangMucChi;
    }
}
