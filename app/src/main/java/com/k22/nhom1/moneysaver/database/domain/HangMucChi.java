package com.k22.nhom1.moneysaver.database.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by thanh on 08/12/2015.
 */
public class HangMucChi extends HangMuc {
    Integer dinhMucChi;
    Set<KhoanChi> cacKhoanChi;

    public Integer getDinhMucChi() {
        return dinhMucChi;
    }

    public void setDinhMucChi(Integer dinhMucChi) {
        this.dinhMucChi = dinhMucChi;
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
