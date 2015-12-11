package com.k22.nhom1.moneysaver.dialog;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.component.LabelledSpinner;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.HangMucThuDinhKy;
import com.k22.nhom1.moneysaver.database.domain.KyHan;
import com.k22.nhom1.moneysaver.fragment.CategoryIncomeRecuringFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 09/12/2015.
 */
public class EditCategoryIncomeRecurringDialog extends DialogFragment {
    public static final String TAG = "EDIT_CATEGORY_INCOME_RECURRING_FRAGMENT_TAG";

    EditText txt_name;
    EditText txt_desc;
    EditText txtFromDate;
    EditText txtToDate;
    EditText txt_amount;
    ImageButton btnSelectFromDate;
    ImageButton btnSelectToDate;
    SimpleDateFormat dateFormatter;
    DB4OProvider db;
    Context mContext;
    HangMucThuDinhKy hangmuc;
    Calendar newCalendar;
    LabelledSpinner spn_recurring;
    boolean isEdit = false;

    public EditCategoryIncomeRecurringDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        String categoryName = getArguments().getString("categoryName");
        hangmuc = new HangMucThuDinhKy();
        mContext = getActivity().getApplicationContext();
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_category_expense_recurring_edit, null);
        String title = getActivity().getString(R.string.new_category_expense_recurring);
        spn_recurring = (LabelledSpinner) view.findViewById(R.id.spn_recurring);
        List<String> categories = new ArrayList<String>();
        categories.add(KyHan.MONTHLY);
        categories.add(KyHan.WEEKLY);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_recurring.setItemsArray((ArrayList<String>) categories);
        txt_name = (EditText) view.findViewById(R.id.txt_name);
        txt_desc = (EditText) view.findViewById(R.id.txt_desc);
        txt_amount = (EditText) view.findViewById(R.id.txt_amount);
        txtFromDate = (EditText) view.findViewById(R.id.txtFromDate);
        txtToDate = (EditText) view.findViewById(R.id.txtToDate);
        btnSelectFromDate = (ImageButton) view.findViewById(R.id.btnSelectFromDate);
        btnSelectToDate = (ImageButton) view.findViewById(R.id.btnSelectToDate);
        newCalendar = Calendar.getInstance();
        btnSelectFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtFromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        btnSelectToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtToDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        if (!TextUtils.isEmpty(categoryName)) {
            title = getActivity().getString(R.string.edit_category_expense_recurring);//"Edit Account Balance";
            hangmuc = db.getHangMucThuDinhKy(categoryName);
            spn_recurring.setSelection(categories.indexOf(hangmuc.getKyHan()));
            txt_name.setText(hangmuc.getTenHangMuc());
            txt_desc.setText(hangmuc.getMoTa());
            txt_amount.setText(String.valueOf(hangmuc.getSoTien()));
            try {
                txtFromDate.setText(dateFormatter.format(hangmuc.getNgayBatDau()));
                txtToDate.setText(dateFormatter.format(hangmuc.getNgayKetThuc()));
            } catch (Exception e) {
                e.printStackTrace();
            }


            isEdit = true;
        }


        b.setView(view).setTitle(title).setIcon(R.drawable.ic_folder_special_black_24dp);
        b.setPositiveButton(getActivity().getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        hangmuc.setTenHangMuc(txt_name.getText().toString());
                        hangmuc.setMoTa(txt_desc.getText().toString());
                        hangmuc.setSoTien(Integer.parseInt(String.valueOf(txt_amount.getText())));
                        try {
                            hangmuc.setNgayBatDau(dateFormatter.parse(String.valueOf(txtFromDate.getText())));
                            hangmuc.setNgayKetThuc(dateFormatter.parse(String.valueOf(txtToDate.getText())));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        hangmuc.setKyHan(String.valueOf(spn_recurring.getSpinner().getSelectedItem()));
                        if (!isEdit) {

                        }
                        ((CategoryIncomeRecuringFragment) getParentFragment()).onFinishAdd(hangmuc);
                    }
                });
        b.setNegativeButton(getActivity().getString(R.string.Cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }
        );

        return b.create();
    }

    public static EditCategoryIncomeRecurringDialog newInstance(String categoryName) {
        EditCategoryIncomeRecurringDialog f = new EditCategoryIncomeRecurringDialog();

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
