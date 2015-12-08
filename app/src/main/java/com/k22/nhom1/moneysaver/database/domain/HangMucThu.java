package com.k22.nhom1.moneysaver.database.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by thanh on 08/12/2015.
 */
public class HangMucThu extends HangMuc {
    Set<KhoanThu> cacKhoanThu;

    public Set<KhoanThu> getCacKhoanThu() {
        return cacKhoanThu;
    }

    public void setCacKhoanThu(Set<KhoanThu> cacKhoanThu) {
        this.cacKhoanThu = cacKhoanThu;
    }

    public boolean addKhoanThu(KhoanThu khoanThu) {
        if (cacKhoanThu == null) cacKhoanThu = new HashSet<>();
        return this.cacKhoanThu.add(khoanThu);
    }
}
