package com.k22.nhom1.moneysaver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.component.LabelledSpinner;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.HangMucChi;
import com.k22.nhom1.moneysaver.fragment.LimitFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 09/12/2015.
 */
public class EditLimitDialog extends DialogFragment {
    public static final String TAG = "EDIT_BALANCE_FRAGMENT_TAG";

    LabelledSpinner spn_limit;
    EditText txt_limit_amount;
    DB4OProvider db;
    Context mContext;
    HangMucChi hangMucChi;
    String hangMucChiName;
    boolean isEdit = false;

    public EditLimitDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        hangMucChiName = getArguments().getString("mHangMucChiName");
        hangMucChi = new HangMucChi();
        mContext = getActivity().getApplicationContext();
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_limit, null);
        String title = getActivity().getString(R.string.new_limit_balance);

        spn_limit = (LabelledSpinner) view.findViewById(R.id.spn_limit);
        txt_limit_amount = (EditText) view.findViewById(R.id.txt_limit_amount);
        List<String> list = loadDinhMucChi();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_limit.setItemsArray((ArrayList<String>) list);

        if (!TextUtils.isEmpty(hangMucChiName)) {
            title = getActivity().getString(R.string.edit_limit_balance);//"Edit Account Balance";
            hangMucChi = db.getHangMucChi(hangMucChiName);

            spn_limit.setSelection(list.indexOf(hangMucChi.getTenHangMuc()));
            spn_limit.setEnabled(false);
            txt_limit_amount.setText(hangMucChi.getDinhMucChi().toString());

            isEdit = true;
        }


        b.setView(view).setTitle(title).setIcon(R.drawable.ic_account_balance_black_24dp);
        b.setPositiveButton(getActivity().getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (!isEdit) {
                            hangMucChi = db.getHangMucChi(spn_limit.getSpinner().getSelectedItem().toString());
                        }
                        hangMucChi.setDinhMucChi(Integer.parseInt(txt_limit_amount.getText().toString()));

                        ((LimitFragment) getParentFragment()).onFinishAddLimit(hangMucChi);
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

    private List<String> loadDinhMucChi() {
        List<String> result = new ArrayList<>();
        List<HangMucChi> list = db.findAllHangMucChi();
        for (HangMucChi hmc : list) {
            result.add(hmc.getTenHangMuc());
        }
        return result;
    }

    public static EditLimitDialog newInstance(String limitName) {
        EditLimitDialog f = new EditLimitDialog();

        Bundle args = new Bundle();
        args.putString("mHangMucChiName", limitName);
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
