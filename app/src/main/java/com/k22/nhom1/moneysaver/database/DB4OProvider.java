package com.k22.nhom1.moneysaver.database;

/**
 * Created by thanh on 08/12/2015.
 */

import android.content.Context;

import com.db4o.ObjectSet;
import com.k22.nhom1.moneysaver.database.domain.HangMucChi;
import com.k22.nhom1.moneysaver.database.domain.HangMucChiDinhKy;
import com.k22.nhom1.moneysaver.database.domain.HangMucThu;
import com.k22.nhom1.moneysaver.database.domain.HangMucThuDinhKy;
import com.k22.nhom1.moneysaver.database.domain.KhoanChi;
import com.k22.nhom1.moneysaver.database.domain.KhoanChoVay;
import com.k22.nhom1.moneysaver.database.domain.KhoanThu;
import com.k22.nhom1.moneysaver.database.domain.KhoanVay;
import com.k22.nhom1.moneysaver.database.domain.KyHan;
import com.k22.nhom1.moneysaver.database.domain.LoaiTaiKhoan;
import com.k22.nhom1.moneysaver.database.domain.NguoiDung;
import com.k22.nhom1.moneysaver.database.domain.TaiKhoan;
import com.k22.nhom1.moneysaver.database.domain.TrangThai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DB4OProvider extends Db4oHelper {

    private static DB4OProvider provider = null;

    //configure Db4oHelper by Passing Context.
    public DB4OProvider(Context ctx) {
        super(ctx);
    }

    public static DB4OProvider getInstance(Context ctx) {
        if (provider == null) {
            provider = new DB4OProvider(ctx);
        }
        return provider;
    }

    public void createSampleData() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        HangMucThuDinhKy hangMucThuDinhKy = new HangMucThuDinhKy();
        hangMucThuDinhKy.setTenHangMuc("Salary");
        hangMucThuDinhKy.setKyHan(KyHan.MONTHLY);
        hangMucThuDinhKy.setMoTa("Monthly Salary");
        hangMucThuDinhKy.setSoTien(5000000);


        HangMucChiDinhKy hangMucChiDinhKy = new HangMucChiDinhKy();
        hangMucChiDinhKy.setTenHangMuc("House Bill");
        hangMucChiDinhKy.setKyHan(KyHan.MONTHLY);
        hangMucChiDinhKy.setMoTa("Monthly House Bill");
        hangMucChiDinhKy.setSoTien(1000000);

        HangMucThu extraPay = new HangMucThu();
        extraPay.setTenHangMuc("Extra payment");
        extraPay.setMoTa("Extra payment");

        HangMucThu overTime = new HangMucThu();
        overTime.setTenHangMuc("Overtime payment");
        overTime.setMoTa("Overtime payment");

        HangMucChi shopping = new HangMucChi();
        shopping.setTenHangMuc("Shopping");
        shopping.setMoTa("Shopping");
        shopping.setDinhMucChi(1000000);

        HangMucChi eating = new HangMucChi();
        eating.setTenHangMuc("Eating");
        eating.setMoTa("Eating");


        TaiKhoan atm = new TaiKhoan("ACB ATM", LoaiTaiKhoan.ATM, 20000000);
        TaiKhoan wallet = new TaiKhoan("My Wallet", LoaiTaiKhoan.CASH, 10000000);

        KhoanChi chianuong = new KhoanChi();
        chianuong.setHangMucChi(eating);
        chianuong.setTaiKhoan(wallet);
        chianuong.setSoTien(200000);
        chianuong.setTenGiaoDich("Dinner at restaurant");
        try {
            chianuong.setNgayGiaoDich(formatter.parse("1/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        chianuong.setGhiChu("Celebrate birthday for my self ");
        wallet.addKhoanChi(chianuong);
        eating.addKhoanChi(chianuong);

        KhoanChi chianuong1 = new KhoanChi();
        chianuong1.setHangMucChi(eating);
        chianuong1.setTaiKhoan(wallet);
        chianuong1.setSoTien(300000);
        chianuong1.setTenGiaoDich("Attend wedding party");
        try {
            chianuong1.setNgayGiaoDich(formatter.parse("3/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        chianuong1.setGhiChu("Attend wedding party");
        wallet.addKhoanChi(chianuong1);
        eating.addKhoanChi(chianuong1);

        KhoanChi chiShopping = new KhoanChi();
        chiShopping.setHangMucChi(shopping);
        chiShopping.setTaiKhoan(wallet);
        chiShopping.setSoTien(500000);
        chiShopping.setTenGiaoDich("Buy new T-Shirt");
        try {
            chiShopping.setNgayGiaoDich(formatter.parse("2/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        chiShopping.setGhiChu("Celebrate birthday for my self ");
        wallet.addKhoanChi(chiShopping);
        eating.addKhoanChi(chiShopping);

        KhoanChi chiHousebill = new KhoanChi();
        chiHousebill.setHangMucChiDinhKy(hangMucChiDinhKy);
        chiHousebill.setTaiKhoan(wallet);
        chiHousebill.setSoTien(hangMucChiDinhKy.getSoTien());
        chiHousebill.setTenGiaoDich("Buy new T-Shirt");
        try {
            chiHousebill.setNgayGiaoDich(formatter.parse("2/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        chiHousebill.setGhiChu("Celebrate birthday for my self ");
        wallet.addKhoanChi(chiHousebill);
        hangMucChiDinhKy.addKhoanChi(chiHousebill);

        KhoanThu thuExtra = new KhoanThu();
        thuExtra.setHangMucThu(extraPay);
        thuExtra.setTaiKhoan(wallet);
        thuExtra.setTenGiaoDich("Extra payment income");
        thuExtra.setSoTien(550000);
        try {
            thuExtra.setNgayGiaoDich(formatter.parse("1/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        thuExtra.setGhiChu("Extra payment income. So far so good");
        wallet.addKhoanThu(thuExtra);
        extraPay.addKhoanThu(thuExtra);

        KhoanThu thuOvertime = new KhoanThu();
        thuOvertime.setHangMucThu(extraPay);
        thuOvertime.setTaiKhoan(wallet);
        thuOvertime.setTenGiaoDich("Overtime payment income");
        thuOvertime.setSoTien(550000);
        try {
            thuOvertime.setNgayGiaoDich(formatter.parse("2/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        thuOvertime.setGhiChu("Overtime payment income. So far so good");
        wallet.addKhoanThu(thuOvertime);
        extraPay.addKhoanThu(thuOvertime);

        KhoanThu thuSalary = new KhoanThu();
        thuSalary.setHangMucThuDinhKy(hangMucThuDinhKy);
        thuSalary.setTaiKhoan(wallet);
        thuSalary.setTenGiaoDich("Monthly Salary");
        thuSalary.setSoTien(hangMucThuDinhKy.getSoTien());
        try {
            thuSalary.setNgayGiaoDich(formatter.parse("3/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        thuSalary.setGhiChu("Monthly Salary, automatic added");
        wallet.addKhoanThu(thuSalary);
        hangMucThuDinhKy.addKhoanThu(thuSalary);

        KhoanVay khoanVay = new KhoanVay();
        khoanVay.setTaiKhoan(wallet);
        khoanVay.setTenGiaoDich("Borrow some cash from Mr Bean");
        khoanVay.setChuNo("Mr Bean");
        try {
            khoanVay.setNgayDuKienTra(formatter.parse("1/3/2016"));
            khoanVay.setNgayGiaoDich(formatter.parse("1/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        khoanVay.setTrangThai(TrangThai.PENDING);
        khoanVay.setGhiChu("Many thank");
        khoanVay.setSoTien(2300000);
        this.store(khoanVay);


        db().store(hangMucThuDinhKy);
        db().store(hangMucChiDinhKy);
        db().store(extraPay);
        db().store(overTime);
        db().store(shopping);
        db().store(eating);
        db().store(atm);
        db().store(wallet);
        db().store(chianuong);
        db().store(chianuong1);
        db().store(chiHousebill);
        db().store(chiShopping);
        db().store(thuExtra);
        db().store(thuOvertime);
        db().store(thuSalary);
    }

    //This method is used to store the object into the database.
    public void store(NguoiDung nguoiDung) {
        db().store(nguoiDung);
    }

    //This method is used to delete the object into the database.
    public void delete(NguoiDung nguoiDung) {
        db().delete(nguoiDung);
    }

    //This method is used to retrive all object from database.
    public List<NguoiDung> findAllNguoiDung() {
        return db().query(NguoiDung.class);
    }

    //This method is used to retrive all object from database.
    public List<TaiKhoan> findAllTaiKhoan() {
        return db().query(TaiKhoan.class);
    }

    //This method is used to retrive matched object from database.
    public List<NguoiDung> getRecord(NguoiDung s) {
        return db().queryByExample(s);
    }

    public void store(KhoanVay khoanVay) {
        db().store(khoanVay);
        KhoanThu thu = new KhoanThu();
        thu.setTaiKhoan(khoanVay.getTaiKhoan());
        thu.setNgayGiaoDich(khoanVay.getNgayGiaoDich());
        thu.setTenGiaoDich(khoanVay.getTenGiaoDich());
        thu.setGhiChu(khoanVay.getGhiChu());
        thu.setSoTien(khoanVay.getSoTien());
        db().store(thu);

        ObjectSet query = db().queryByExample(khoanVay.getTaiKhoan());
        if (query.hasNext()) {
            TaiKhoan tk = (TaiKhoan) query.next();
            tk.addKhoanThu(thu);
            tk.addKhoanVay(khoanVay);
            db().store(tk);
        }
    }

    public void store(KhoanChoVay khoanChoVay) {
        db().store(khoanChoVay);
        KhoanChi chi = new KhoanChi();
        chi.setTaiKhoan(khoanChoVay.getTaiKhoan());
        chi.setNgayGiaoDich(khoanChoVay.getNgayGiaoDich());
        chi.setTenGiaoDich(khoanChoVay.getTenGiaoDich());
        chi.setGhiChu(khoanChoVay.getGhiChu());
        chi.setSoTien(khoanChoVay.getSoTien());
        db().store(chi);

        ObjectSet query = db().queryByExample(khoanChoVay.getTaiKhoan());
        if (query.hasNext()) {
            TaiKhoan tk = (TaiKhoan) query.next();
            tk.addKhoanChi(chi);
            tk.addKhoanChoVay(khoanChoVay);
            db().store(tk);
        }
    }

    public TaiKhoan getTaiKhoan(String mBalanceName) {
        TaiKhoan tk = new TaiKhoan(mBalanceName);
        ObjectSet<TaiKhoan> query = db().queryByExample(tk);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public void store(TaiKhoan taiKhoan) {
        db().store(taiKhoan);
    }
}

