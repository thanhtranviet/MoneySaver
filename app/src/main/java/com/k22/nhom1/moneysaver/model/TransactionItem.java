package com.k22.nhom1.moneysaver.model;

import com.k22.nhom1.moneysaver.database.domain.GiaoDich;
import com.k22.nhom1.moneysaver.database.domain.KhoanChi;
import com.k22.nhom1.moneysaver.database.domain.KhoanChoVay;
import com.k22.nhom1.moneysaver.database.domain.KhoanThu;
import com.k22.nhom1.moneysaver.database.domain.KhoanVay;

import java.util.Date;

/**
 * Created by thanh on 10/12/2015.
 */
public class TransactionItem {
    String transactionType;
    Object dbObject;
    String tenGiaoDich;
    Date ngayGiaoDich;
    Integer soTien;
    String ghiChu;

    public TransactionItem(GiaoDich gd) {
        this.dbObject = gd;
        this.tenGiaoDich = gd.getTenGiaoDich();
        this.ngayGiaoDich = gd.getNgayGiaoDich();
        this.soTien = gd.getSoTien();
        this.ghiChu = gd.getGhiChu();

        if (gd instanceof KhoanChi) {
            transactionType = GiaoDich.KHOAN_CHI;
        } else if (gd instanceof KhoanThu) {
            transactionType = GiaoDich.KHOAN_THU;
        } else if (gd instanceof KhoanVay) {
            transactionType = GiaoDich.KHOAN_VAY;
        } else if (gd instanceof KhoanChoVay) {
            transactionType = GiaoDich.KHOAN_CHOVAY;
        }
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Object getDbObject() {
        return dbObject;
    }

    public String getTenGiaoDich() {
        return tenGiaoDich;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }
}
