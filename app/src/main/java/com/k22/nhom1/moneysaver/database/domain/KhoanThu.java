package com.k22.nhom1.moneysaver.database.domain;

/**
 * Created by thanh on 08/12/2015.
 */
public class KhoanThu extends GiaoDich {
    TaiKhoan taiKhoan;
    HangMucThu hangMucThu;
    HangMucThuDinhKy hangMucThuDinhKy;

    public KhoanThu() {
    }

    public KhoanThu(String name) {
        super(name);
    }
    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public HangMucThu getHangMucThu() {
        return hangMucThu;
    }

    public void setHangMucThu(HangMucThu hangMucThu) {
        this.hangMucThu = hangMucThu;
    }

    public HangMucThuDinhKy getHangMucThuDinhKy() {
        return hangMucThuDinhKy;
    }

    public void setHangMucThuDinhKy(HangMucThuDinhKy hangMucThuDinhKy) {
        this.hangMucThuDinhKy = hangMucThuDinhKy;
    }
}
