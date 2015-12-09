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
import android.widget.EditText;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.HangMucThu;
import com.k22.nhom1.moneysaver.fragment.CategoryIncomeFragment;

import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 09/12/2015.
 */
public class EditCategoryIncomeDialog extends DialogFragment {
    public static final String TAG = "EDIT_CATEGORY_INCOME_FRAGMENT_TAG";

    EditText txt_name;
    EditText txt_desc;
    DB4OProvider db;
    Context mContext;
    HangMucThu hangmuc;
    boolean isEdit = false;

    public EditCategoryIncomeDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        String categoryName = getArguments().getString("categoryName");
        hangmuc = new HangMucThu();
        mContext = getActivity().getApplicationContext();
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_category_income_edit, null);
        String title = getActivity().getString(R.string.new_category_income);

        txt_name = (EditText) view.findViewById(R.id.txt_name);
        txt_desc = (EditText) view.findViewById(R.id.txt_desc);

        if (!TextUtils.isEmpty(categoryName)) {
            title = getActivity().getString(R.string.edit_category_income);//"Edit Account Balance";
            hangmuc = db.getHangMucThu(categoryName);

            txt_name.setText(hangmuc.getTenHangMuc());
            txt_desc.setText(hangmuc.getMoTa());

        }


        b.setView(view).setTitle(title).setIcon(R.drawable.ic_folder_special_black_24dp);
        b.setPositiveButton(getActivity().getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        hangmuc.setTenHangMuc(txt_name.getText().toString());
                        hangmuc.setMoTa(txt_desc.getText().toString());
                        if (!isEdit) {

                        }
                        ((CategoryIncomeFragment) getParentFragment()).onFinishAdd(hangmuc);
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

    public static EditCategoryIncomeDialog newInstance(String categoryName) {
        EditCategoryIncomeDialog f = new EditCategoryIncomeDialog();

        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
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
