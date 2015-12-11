package com.k22.nhom1.moneysaver.database;

/**
 * Created by thanh on 08/12/2015.
 */

import android.content.Context;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.k22.nhom1.moneysaver.database.domain.GiaoDich;
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
import java.util.Date;
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
            chianuong.setNgayGiaoDich(formatter.parse("5/12/2015"));
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
            chianuong1.setNgayGiaoDich(formatter.parse("6/12/2015"));
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
            chiShopping.setNgayGiaoDich(formatter.parse("7/12/2015"));
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
        chiHousebill.setTenGiaoDich("Pay monthly bill for December");
        try {
            chiHousebill.setNgayGiaoDich(formatter.parse("8/12/2015"));
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
            thuExtra.setNgayGiaoDich(formatter.parse("7/12/2015"));
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
            thuOvertime.setNgayGiaoDich(formatter.parse("8/12/2015"));
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
            thuSalary.setNgayGiaoDich(formatter.parse("8/12/2015"));
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
            khoanVay.setNgayGiaoDich(formatter.parse("6/12/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        khoanVay.setTrangThai(TrangThai.PENDING);
        khoanVay.setGhiChu("Many thank");
        khoanVay.setSoTien(2300000);
        //this.store(khoanVay);


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
        db().store(khoanVay);
    }

    public void store(NguoiDung nguoiDung) {
        db().store(nguoiDung);
    }

    public void delete(NguoiDung nguoiDung) {
        db().delete(nguoiDung);
    }

    public List<NguoiDung> findAllNguoiDung() {
        return db().queryByExample(NguoiDung.class);
    }

    public List<TaiKhoan> findAllTaiKhoan() {
        return db().queryByExample(TaiKhoan.class);
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

    public HangMucChi getHangMucChi(String mHangMucChiName) {
        HangMucChi tk = new HangMucChi(mHangMucChiName);
        ObjectSet<HangMucChi> query = db().queryByExample(tk);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public void store(HangMucChi hm) {
        db().store(hm);
    }

    public void store(HangMucChiDinhKy hm) {
        db().store(hm);
    }

    public List<HangMucChi> findAllHangMucChi(Boolean hasDinhMuc) {
        if (!hasDinhMuc) {
            return db().query(new Predicate<HangMucChi>() {
                public boolean match(HangMucChi item) {
                    return item.getDinhMucChi() == 0;
                }
            });
        } else {
            return db().query(new Predicate<HangMucChi>() {
                public boolean match(HangMucChi item) {
                    return item.getDinhMucChi() > 0;
                }
            });
        }
    }

    public List<HangMucThu> findAllHangMucThu() {
        return db().query(new Predicate<HangMucThu>() {
            public boolean match(HangMucThu candidate) {
                return !(candidate instanceof HangMucThuDinhKy);
            }
        });
    }

    public List<HangMucThuDinhKy> findAllHangMucThuDinhKy() {
        return db().queryByExample(HangMucThuDinhKy.class);
    }

    public List<HangMucChi> findAllHangMucChi() {
        return db().query(new Predicate<HangMucChi>() {
            public boolean match(HangMucChi candidate) {
                return !(candidate instanceof HangMucChiDinhKy);
            }
        });
    }

    public List<HangMucChiDinhKy> findAllHangMucChiDinhKy() {
        return db().queryByExample(HangMucChiDinhKy.class);
    }

    public List<NguoiDung> getRecord(NguoiDung s) {
        return db().queryByExample(s);
    }

    public HangMucThu getHangMucThu(String categoryName) {
        HangMucThu tk = new HangMucThu(categoryName);
        ObjectSet<HangMucThu> query = db().queryByExample(tk);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public void store(HangMucThu hm) {
        db().store(hm);
    }

    public void store(HangMucThuDinhKy hm) {
        db().store(hm);
    }

    public HangMucChiDinhKy getHangMucChiDinhKy(String categoryName) {
        HangMucChiDinhKy tk = new HangMucChiDinhKy(categoryName);
        ObjectSet<HangMucChiDinhKy> query = db().queryByExample(tk);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public HangMucThuDinhKy getHangMucThuDinhKy(String categoryName) {
        HangMucThuDinhKy tk = new HangMucThuDinhKy(categoryName);
        ObjectSet<HangMucThuDinhKy> query = db().queryByExample(tk);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public List<GiaoDich> findAllGiaoDich() {
        return db().query(GiaoDich.class);
    }

    public void deleteKhoanThu(KhoanThu gd) {
        gd.getTaiKhoan().deleteKhoanThu(gd);
        db().delete(gd);
        //db().commit();
    }

    public void deleteKhoanChi(KhoanChi gd) {
        gd.getTaiKhoan().deleteKhoanChi(gd);
        db().delete(gd);
        //db().commit();
    }

    public void deleteKhoanVay(KhoanVay gd) {
        gd.getTaiKhoan().deleteKhoanVay(gd);
        db().delete(gd);
    }

    public void deleteKhoanChoVay(KhoanChoVay gd) {
        gd.getTaiKhoan().deleteKhoanChoVay(gd);
        db().delete(gd);
        //db().commit();
    }

    public List<KhoanThu> findAllKhoanThu() {
        return db().query(KhoanThu.class);
    }

    public KhoanThu getKhoanThu(String transactionName) {
        KhoanThu o = new KhoanThu(transactionName);
        ObjectSet<KhoanThu> query = db().queryByExample(o);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public KhoanChi getKhoanChi(String transactionName) {
        KhoanChi o = new KhoanChi(transactionName);
        ObjectSet<KhoanChi> query = db().queryByExample(o);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public KhoanVay getKhoanVay(String transactionName) {
        KhoanVay o = new KhoanVay(transactionName);
        ObjectSet<KhoanVay> query = db().queryByExample(o);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public KhoanChoVay getKhoanChoVay(String transactionName) {
        KhoanChoVay o = new KhoanChoVay(transactionName);
        ObjectSet<KhoanChoVay> query = db().queryByExample(o);
        if (query.hasNext()) {
            return query.next();
        }
        return null;
    }

    public Integer calculateKhoanThu(final Date date) {
        Integer tong = 0;
        ObjectSet<KhoanThu> query = db().query(new Predicate<KhoanThu>() {
            public boolean match(KhoanThu candidate) {
                return candidate.getNgayGiaoDich().equals(date);
            }
        });
        if (query.hasNext()) {
            tong += query.next().getSoTien();
        }
        return tong;
    }

    public Integer calculateKhoanChi(final Date date) {
        Integer tong = 0;
        ObjectSet<KhoanChi> query = db().query(new Predicate<KhoanChi>() {
            public boolean match(KhoanChi candidate) {
                return candidate.getNgayGiaoDich().equals(date);
            }
        });
        if (query.hasNext()) {
            tong += query.next().getSoTien();
        }
        return tong;
    }

    public Integer calculateKhoanVay(final Date date) {
        Integer tong = 0;
        ObjectSet<KhoanVay> query = db().query(new Predicate<KhoanVay>() {
            public boolean match(KhoanVay candidate) {
                return candidate.getNgayGiaoDich().equals(date);
            }
        });
        if (query.hasNext()) {
            tong += query.next().getSoTien();
        }
        return tong;
    }

    public Integer calculateKhoanChoVay(final Date date) {
        Integer tong = 0;
        ObjectSet<KhoanChoVay> query = db().query(new Predicate<KhoanChoVay>() {
            public boolean match(KhoanChoVay candidate) {
                return candidate.getNgayGiaoDich().equals(date);
            }
        });
        if (query.hasNext()) {
            tong += query.next().getSoTien();
        }
        return tong;
    }

    public void addKhoanThu(KhoanThu giaodich) {
        giaodich.getTaiKhoan().addKhoanThu(giaodich);
        db().store(giaodich);
        db().commit();
    }

    public void addKhoanChi(KhoanChi giaodich) {
        giaodich.getTaiKhoan().addKhoanChi(giaodich);
        db().store(giaodich);
    }

    public void addKhoanVay(KhoanVay giaodich) {
        giaodich.getTaiKhoan().addKhoanVay(giaodich);
        db().store(giaodich);
    }

    public void addKhoanChoVay(KhoanChoVay giaodich) {
        giaodich.getTaiKhoan().addKhoanChoVay(giaodich);
        db().store(giaodich);
    }

    public List<KhoanChi> findAllKhoanChi() {
        return db().query(KhoanChi.class);
    }

    public List<KhoanVay> findAllKhoanVay() {

        return db().query(KhoanVay.class);
    }

    public List<KhoanChoVay> findAllKhoanChoVay() {

        return db().query(KhoanChoVay.class);
    }
}

