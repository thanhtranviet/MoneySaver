package com.k22.nhom1.moneysaver.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.adapter.TransactionAdapter;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.GiaoDich;
import com.k22.nhom1.moneysaver.database.domain.KhoanChoVay;
import com.k22.nhom1.moneysaver.dialog.EditBalanceDialog;
import com.k22.nhom1.moneysaver.dialog.EditKhoanChoVayDialog;
import com.k22.nhom1.moneysaver.model.TransactionItem;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeItemManagerInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 10/12/2015.
 */
public class LoanTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, View.OnClickListener {
    private SuperRecyclerView mRecycler;
    private TransactionAdapter mAdapter;
    private Handler mHandler;
    FloatingActionButton fabAdd;
    DB4OProvider db;
    Context mContext;

    public LoanTabFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_overview_tab, container, false);

        mRecycler = (SuperRecyclerView) rootView.findViewById(R.id.list);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<TransactionItem> list = loadData();
        mAdapter = new TransactionAdapter(list, db, getFragmentManager(), this);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mHandler = new Handler(Looper.getMainLooper());
        mRecycler.setAdapter(mAdapter);

        mRecycler.setRefreshListener(this);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecycler.setupMoreListener(this, 1);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);
        return rootView;
    }

    private ArrayList<TransactionItem> loadData() {
        ArrayList<TransactionItem> result = new ArrayList<>();
        List<KhoanChoVay> data = db.findAllKhoanChoVay();
        for (GiaoDich o : data) {
            result.add(new TransactionItem(o));
        }
        Collections.sort(result, new Comparator<TransactionItem>() {
            public int compare(TransactionItem o1, TransactionItem o2) {
                if (o1.getNgayGiaoDich() == null || o2.getNgayGiaoDich() == null) {
                    return 0;
                }
                return o1.getNgayGiaoDich().compareTo(o2.getNgayGiaoDich());
            }
        });
        Collections.reverse(result);
        return result;
    }

    @Override
    public void onRefresh() {
        refreshdata();
    }

    private void refreshdata() {
        mAdapter.closeAllExcept(null);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mAdapter.reload(loadData());
            }
        }, 200);
    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        refreshdata();
        mRecycler.hideMoreProgress();
        mRecycler.setLoadingMore(false);
    }

    public void onFinishAdd(KhoanChoVay giaodich) {
        db.addKhoanChoVay(giaodich);

        refreshdata();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabAdd:
                EditKhoanChoVayDialog dialog = EditKhoanChoVayDialog.newInstance("", this);
                dialog.show(getFragmentManager(), EditBalanceDialog.TAG);
                break;
        }
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