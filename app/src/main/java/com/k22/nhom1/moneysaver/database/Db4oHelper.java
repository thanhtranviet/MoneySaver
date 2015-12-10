package com.k22.nhom1.moneysaver.database;

/**
 * Created by thanh on 08/12/2015.
 */

import android.content.Context;
import android.util.Log;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.constraints.UniqueFieldValueConstraint;
import com.k22.nhom1.moneysaver.database.domain.GiaoDich;
import com.k22.nhom1.moneysaver.database.domain.KhoanChi;
import com.k22.nhom1.moneysaver.database.domain.KhoanChoVay;
import com.k22.nhom1.moneysaver.database.domain.KhoanThu;
import com.k22.nhom1.moneysaver.database.domain.KhoanVay;

import java.io.IOException;

public class Db4oHelper {

    private static ObjectContainer oc = null;
    private Context context;

    /**
     * @param ctx
     */
    public Db4oHelper(Context ctx) {
        context = ctx;
    }

    /**
     * Create, open and close the database
     */
    public ObjectContainer db() {

        try {
            if (oc == null || oc.ext().isClosed()) {
                oc = Db4oEmbedded.openFile(dbConfig(), db4oDBFullPath(context));
            }
            return oc;
        } catch (Exception ie) {
            Log.e(Db4oHelper.class.getName(), ie.toString());
            return null;
        }
    }

    /**
     * Configure the behavior of the database
     */

    private EmbeddedConfiguration dbConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        /*
        configuration.common().objectClass(NguoiDung.class).objectField("userName")
                .indexed(true);
        configuration.common().objectClass(NguoiDung.class).cascadeOnUpdate(true);
        configuration.common().objectClass(NguoiDung.class)
                .cascadeOnActivate(true);
        */
        configuration.common().objectClass(GiaoDich.class).objectField("tenGiaoDich")
                .indexed(true);
        configuration.common().add(new UniqueFieldValueConstraint(GiaoDich.class, "tenGiaoDich"));
        configuration.common().objectClass(KhoanThu.class).objectField("tenGiaoDich")
                .indexed(true);
        configuration.common().add(new UniqueFieldValueConstraint(KhoanThu.class, "tenGiaoDich"));
        configuration.common().objectClass(KhoanChi.class).objectField("tenGiaoDich")
                .indexed(true);
        configuration.common().add(new UniqueFieldValueConstraint(KhoanChi.class, "tenGiaoDich"));
        configuration.common().objectClass(KhoanVay.class).objectField("tenGiaoDich")
                .indexed(true);
        configuration.common().add(new UniqueFieldValueConstraint(KhoanVay.class, "tenGiaoDich"));
        configuration.common().objectClass(KhoanChoVay.class).objectField("tenGiaoDich")
                .indexed(true);
        configuration.common().add(new UniqueFieldValueConstraint(KhoanChoVay.class, "tenGiaoDich"));
        return configuration;
    }

    /**
     * Returns the path for the database location
     */

    private String db4oDBFullPath(Context ctx) {
        return ctx.getDir("data", 0) + "/" + "moneysaver.db4o";
    }

    /**
     * Closes the database
     */

    public void close() {
        if (oc != null)
            oc.close();
    }
}
