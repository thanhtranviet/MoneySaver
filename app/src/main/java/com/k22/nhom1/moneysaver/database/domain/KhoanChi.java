package com.k22.nhom1.moneysaver.database.domain;

/**
 * Created by thanh on 08/12/2015.
 */
public class KhoanChi extends GiaoDich {
    TaiKhoan taiKhoan;
    HangMucChi hangMucChi;
    HangMucChiDinhKy hangMucChiDinhKy;

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

    public HangMucChiDinhKy getHangMucChiDinhKy() {
        return hangMucChiDinhKy;
    }

    public void setHangMucChiDinhKy(HangMucChiDinhKy hangMucChiDinhKy) {
        this.hangMucChiDinhKy = hangMucChiDinhKy;
    }
}
