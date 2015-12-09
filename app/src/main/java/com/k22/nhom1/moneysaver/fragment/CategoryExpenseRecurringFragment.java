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
import com.k22.nhom1.moneysaver.database.domain.HangMucChiDinhKy;
import com.k22.nhom1.moneysaver.dialog.EditCategoryExpenseRecurringDialog;
import com.k22.nhom1.moneysaver.model.BaseListItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 09/12/2015.
 */
public class CategoryExpenseRecurringFragment extends ListFragment implements View.OnClickListener {
    public static final String TAG = "CATEGORY_EXPENSE_RECURRING_FRAGMENT_TAG";
    List list = new ArrayList();
    BaseListAdapter mAdapter;
    ListView mListView;
    FloatingActionButton fabAdd;
    NumberFormat formatter;
    View rootView;
    DB4OProvider db;
    Context mContext;

    public CategoryExpenseRecurringFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_category_expense_recurring, container, false);
        formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        loadList();
        mAdapter = new BaseListAdapter(getActivity(), list);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);
        return rootView;
    }

    private void loadList() {
        list.clear();
        List<HangMucChiDinhKy> result = db.findAllHangMucChiDinhKy();

        for (HangMucChiDinhKy hm : result) {
            list.add(new BaseListItem(hm.getTenHangMuc(), hm.getMoTa()));
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        BaseListItem item = (BaseListItem) this.list.get(position);

        FragmentManager fm = getChildFragmentManager();
        EditCategoryExpenseRecurringDialog dialog = EditCategoryExpenseRecurringDialog.newInstance(item.getItemTitle());
        dialog.show(fm, EditCategoryExpenseRecurringDialog.TAG);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabAdd:
                addCategory();
                break;
        }
    }

    private void addCategory() {
        FragmentManager fm = getChildFragmentManager();
        EditCategoryExpenseRecurringDialog dialog = EditCategoryExpenseRecurringDialog.newInstance("");
        dialog.show(fm, EditCategoryExpenseRecurringDialog.TAG);
    }

    public void onFinishAdd(HangMucChiDinhKy hm) {
        db.store(hm);
        loadList();
        mAdapter.notifyDataSetChanged();
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.relativeLayout), hm.getTenHangMuc() + " updated!", Snackbar.LENGTH_SHORT);

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
