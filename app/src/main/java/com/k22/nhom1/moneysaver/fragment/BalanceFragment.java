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
import com.k22.nhom1.moneysaver.database.domain.TaiKhoan;
import com.k22.nhom1.moneysaver.dialog.EditBalanceDialog;
import com.k22.nhom1.moneysaver.model.BaseListItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 07/12/2015.
 */
public class BalanceFragment extends ListFragment implements View.OnClickListener {
    public static final String TAG = "BALANCE_FRAGMENT_TAG";
    List balanceItemList = new ArrayList();
    BaseListAdapter mAdapter;
    ListView mListView;
    FloatingActionButton fabAddBalance;
    NumberFormat formatter;
    View rootView;
    DB4OProvider db;
    Context mContext;

    public BalanceFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_balance, container, false);
        formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        loadBalanceList();
        mAdapter = new BaseListAdapter(getActivity(), balanceItemList);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        fabAddBalance = (FloatingActionButton) rootView.findViewById(R.id.fabAddBalance);
        fabAddBalance.setOnClickListener(this);
        return rootView;
    }

    private List loadBalanceList() {
        balanceItemList.clear();
        List<TaiKhoan> taiKhoans = db.findAllTaiKhoan();

        for (TaiKhoan tk : taiKhoans) {
            balanceItemList.add(new BaseListItem(tk.getTenTaiKhoan(), formatter.format(tk.getSoDuHienTai())));
        }
        return balanceItemList;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        BaseListItem item = (BaseListItem) this.balanceItemList.get(position);

        FragmentManager fm = getChildFragmentManager();
        EditBalanceDialog editNameDialog = EditBalanceDialog.newInstance(item.getItemTitle());
        editNameDialog.show(fm, EditBalanceDialog.TAG);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabAddBalance:
                addBalance();
                break;
        }
    }

    private void addBalance() {
        FragmentManager fm = getChildFragmentManager();
        EditBalanceDialog editNameDialog = EditBalanceDialog.newInstance("");
        editNameDialog.show(fm, EditBalanceDialog.TAG);
    }

    public void onFinishAddBalance(TaiKhoan tk) {
        db.store(tk);
        loadBalanceList();
        mAdapter.notifyDataSetChanged();
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.relativeLayout), tk.getTenTaiKhoan() + " added!", Snackbar.LENGTH_SHORT);

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