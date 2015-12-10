package com.k22.nhom1.moneysaver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.component.LabelledSpinner;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.LoaiTaiKhoan;
import com.k22.nhom1.moneysaver.database.domain.TaiKhoan;
import com.k22.nhom1.moneysaver.fragment.BalanceFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 07/12/2015.
 */
public class EditBalanceDialog extends DialogFragment {
    public static final String TAG = "EDIT_BALANCE_FRAGMENT_TAG";

    EditText txt_balance_name;
    LabelledSpinner spn_category;
    EditText txt_balance_amount;
    DB4OProvider db;
    Context mContext;
    TaiKhoan taiKhoan;
    boolean isEdit = false;

    public EditBalanceDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        String mBalanceName = getArguments().getString("balanceName");
        taiKhoan = new TaiKhoan();
        mContext = getActivity().getApplicationContext();
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_balance, null);
        String title = getActivity().getString(R.string.new_account_balance);

        spn_category = (LabelledSpinner) view.findViewById(R.id.spn_category);
        txt_balance_name = (EditText) view.findViewById(R.id.txt_balance_name);
        txt_balance_amount = (EditText) view.findViewById(R.id.txt_balance_amount);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(LoaiTaiKhoan.ATM);
        categories.add(LoaiTaiKhoan.BANK);
        categories.add(LoaiTaiKhoan.CASH);
        categories.add(LoaiTaiKhoan.OTHER);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_category.setItemsArray((ArrayList<String>) categories);

        if (!TextUtils.isEmpty(mBalanceName)) {
            title = getActivity().getString(R.string.edit_account_balance);//"Edit Account Balance";
            taiKhoan = db.getTaiKhoan(mBalanceName);

            txt_balance_name.setText(taiKhoan.getTenTaiKhoan());
            spn_category.setSelection(categories.indexOf(taiKhoan.getLoaiTaiKhoan()));

            TextInputLayout txtBalanceAmount = (TextInputLayout) view.findViewById(R.id.layout_txt_balance_amount);
            txtBalanceAmount.setVisibility(View.INVISIBLE);
            isEdit = true;
        }


        b.setView(view).setTitle(title).setIcon(R.drawable.ic_account_balance_black_24dp);
        b.setPositiveButton(getActivity().getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        taiKhoan.setTenTaiKhoan(txt_balance_name.getText().toString());
                        taiKhoan.setLoaiTaiKhoan(spn_category.getSpinner().getSelectedItem().toString());
                        if (!isEdit) {
                            taiKhoan.setSoKhoiTao(Integer.parseInt(txt_balance_amount.getText().toString()));
                        }
                        ((BalanceFragment) getParentFragment()).onFinishAddBalance(taiKhoan);
                    }
                }
        )
                .setNegativeButton(getActivity().getString(R.string.Cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        return b.create();
    }

    public static EditBalanceDialog newInstance(String balanceName) {
        EditBalanceDialog f = new EditBalanceDialog();

        Bundle args = new Bundle();
        args.putString("balanceName", balanceName);
        f.setArguments(args);

        return f;
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
