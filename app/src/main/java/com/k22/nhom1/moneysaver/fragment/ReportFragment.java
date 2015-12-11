package com.k22.nhom1.moneysaver.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.database.DB4OProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 07/12/2015.
 */
public class ReportFragment extends Fragment {
    public static final String TAG = "REPORT_FRAGMENT_TAG";
    DB4OProvider db;
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        // create a new chart object
        BarChart chart = (BarChart) v.findViewById(R.id.chart);
        ArrayList<String> xAxis = getXAxisValues();
        BarData data = new BarData(xAxis, getDataSet(xAxis));
        chart.setData(data);
        chart.setDescription(mContext.getString(R.string.week_transaction));
        chart.animateXY(2000, 2000);
        chart.invalidate();

        return v;
    }

    private ArrayList<BarDataSet> getDataSet(ArrayList<String> xAxis) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> khoanthu = new ArrayList<>();
        ArrayList<BarEntry> khoanchi = new ArrayList<>();
        ArrayList<BarEntry> khoanvay = new ArrayList<>();
        ArrayList<BarEntry> khoanchovay = new ArrayList<>();

        int idx = 0;

        for (String dateString : xAxis) {
            try {
                Date date = format.parse(dateString);
                khoanthu.add(new BarEntry(db.calculateKhoanThu(date), idx));
                khoanchi.add(new BarEntry(db.calculateKhoanChi(date), idx));
                khoanvay.add(new BarEntry(db.calculateKhoanVay(date), idx));
                khoanchovay.add(new BarEntry(db.calculateKhoanChoVay(date), idx));
                idx++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        BarDataSet barDataSet1 = new BarDataSet(khoanthu, getString(R.string.income));
        barDataSet1.setColor(Color.GREEN);
        BarDataSet barDataSet2 = new BarDataSet(khoanchi, getString(R.string.expense));
        barDataSet2.setColor(Color.YELLOW);
        BarDataSet barDataSet3 = new BarDataSet(khoanvay, getString(R.string.borrow));
        barDataSet3.setColor(Color.RED);
        BarDataSet barDataSet4 = new BarDataSet(khoanchovay, getString(R.string.loan));
        barDataSet4.setColor(Color.BLUE);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        dataSets.add(barDataSet4);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -6);
        ArrayList<String> xAxis = new ArrayList<>();
        for (int x = 1; x <= 7; x++) {
            xAxis.add(format.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        /*
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        */
        return xAxis;
    }

    public class OpenDBTask extends AsyncTask<Void, Void, DB4OProvider> {
        public OpenDBTask(Context mContext) {
        }

        @Override
        protected DB4OProvider doInBackground(Void... voids) {
            DB4OProvider db = DB4OProvider.getInstance(mContext);
            db.db();
            return db;
        }
    }
}
