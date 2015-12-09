package com.k22.nhom1.moneysaver.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.adapter.BaseListAdapter;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.HangMucChi;
import com.k22.nhom1.moneysaver.dialog.EditBalanceDialog;
import com.k22.nhom1.moneysaver.dialog.EditLimitDialog;
import com.k22.nhom1.moneysaver.model.BaseListItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 07/12/2015.
 */
public class LimitFragment extends ListFragment implements View.OnClickListener {
    public static final String TAG = "LIMIT_FRAGMENT_TAG";
    List limitItemList = new ArrayList();
    BaseListAdapter mAdapter;
    ListView mListView;
    FloatingActionButton fabAddLimit;
    NumberFormat formatter;
    View rootView;
    DB4OProvider db;
    Context mContext;
    public LimitFragment() {
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

        rootView = inflater.inflate(R.layout.fragment_limit, container, false);
        formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        loadLimitList();
        mAdapter = new BaseListAdapter(getActivity(), limitItemList);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        fabAddLimit = (FloatingActionButton) rootView.findViewById(R.id.fabAddLimit);
        fabAddLimit.setOnClickListener(this);
        return rootView;
    }

    private List loadLimitList() {
        limitItemList.clear();
        List<HangMucChi> list = db.findAllHangMucChi(true);

        for (HangMucChi hmc : list) {
            limitItemList.add(new BaseListItem(hmc.getTenHangMuc(), formatter.format(hmc.getDinhMucChi())));
        }
        return limitItemList;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        BaseListItem item = (BaseListItem) this.limitItemList.get(position);

        FragmentManager fm = getChildFragmentManager();
        EditLimitDialog editNameDialog = EditLimitDialog.newInstance(item.getItemTitle());
        editNameDialog.show(fm, EditBalanceDialog.TAG);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabAddLimit:
                addLimit();
                break;
        }
    }

    private void addLimit() {
        FragmentManager fm = getChildFragmentManager();
        EditLimitDialog editNameDialog = EditLimitDialog.newInstance("");
        editNameDialog.show(fm, EditLimitDialog.TAG);
    }

    public void onFinishAddLimit(HangMucChi hangMucChi) {
        db.store(hangMucChi);
        loadLimitList();
        mAdapter.notifyDataSetChanged();
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.relativeLayout), hangMucChi.getTenHangMuc() + " updated!", Snackbar.LENGTH_SHORT);

        snackbar.show();
        //Toast.makeText(getActivity(), "Hi, " + inputText, Toast.LENGTH_SHORT).show();
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
