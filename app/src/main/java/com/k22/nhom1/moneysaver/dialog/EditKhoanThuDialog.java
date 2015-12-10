package com.k22.nhom1.moneysaver.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import com.k22.nhom1.moneysaver.database.domain.HangMucThu;
import com.k22.nhom1.moneysaver.database.domain.KhoanThu;
import com.k22.nhom1.moneysaver.database.domain.TaiKhoan;
import com.k22.nhom1.moneysaver.fragment.IncomeTabFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by thanh on 10/12/2015.
 */
public class EditKhoanThuDialog extends DialogFragment {
    public static final String TAG = "EDIT_BALANCE_FRAGMENT_TAG";

    EditText txt_transaction_name;
    EditText txt_transaction_amount;
    EditText txt_transaction_note;
    LabelledSpinner spn_transaction_category;
    LabelledSpinner spn_transaction_balance;
    EditText txt_transaction_date;

    DB4OProvider db;
    Context mContext;
    KhoanThu giaodich;
    static Fragment _caller;
    Calendar newCalendar;
    ImageButton btnSelectDate;

    SimpleDateFormat dateFormat;
    boolean isEdit = false;

    public EditKhoanThuDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String transactionName = getArguments().getString("transactionName");
        giaodich = new KhoanThu();
        mContext = getActivity().getApplicationContext();
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_transaction, null);
        String title = getActivity().getString(R.string.new_transaction_income);

        spn_transaction_category = (LabelledSpinner) view.findViewById(R.id.spn_transaction_category);
        spn_transaction_balance = (LabelledSpinner) view.findViewById(R.id.spn_transaction_balance);
        txt_transaction_name = (EditText) view.findViewById(R.id.txt_transaction_name);
        txt_transaction_amount = (EditText) view.findViewById(R.id.txt_transaction_amount);
        txt_transaction_note = (EditText) view.findViewById(R.id.txt_transaction_note);
        txt_transaction_date = (EditText) view.findViewById(R.id.txt_transaction_date);
        btnSelectDate = (ImageButton) view.findViewById(R.id.btnSelectDate);
        newCalendar = Calendar.getInstance();
        List<String> balances = loadBalance();
        ArrayAdapter<String> balanceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, balances);
        balanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_transaction_balance.setItemsArray((ArrayList<String>) balances);

        List<String> categories = loadCategory();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_transaction_category.setItemsArray((ArrayList<String>) categories);

        if (!TextUtils.isEmpty(transactionName)) {
            title = getActivity().getString(R.string.edit_transaction_income);//"Edit Account Balance";
            giaodich = db.getKhoanThu(transactionName);

            txt_transaction_name.setText(giaodich.getTenGiaoDich());
            txt_transaction_amount.setText(String.valueOf(giaodich.getSoTien()));
            txt_transaction_date.setText(dateFormat.format(giaodich.getNgayGiaoDich()));
            txt_transaction_note.setText(giaodich.getGhiChu());
            spn_transaction_category.setSelection(categories.indexOf(giaodich.getHangMucThu()));
            spn_transaction_balance.setSelection(balances.indexOf(giaodich.getTaiKhoan()));

            isEdit = true;
        }
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_transaction_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        b.setView(view).setTitle(title).setIcon(R.drawable.ic_account_balance_black_24dp);
        b.setPositiveButton(getActivity().getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        giaodich.setTenGiaoDich(txt_transaction_name.getText().toString());
                        giaodich.setSoTien(Integer.parseInt(txt_transaction_amount.getText().toString()));
                        giaodich.setGhiChu(txt_transaction_note.getText().toString());
                        try {
                            giaodich.setNgayGiaoDich(dateFormat.parse(txt_transaction_date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        giaodich.setTaiKhoan(db.getTaiKhoan(String.valueOf(spn_transaction_balance.getSpinner().getSelectedItem())));
                        giaodich.setHangMucThu(db.getHangMucThu(String.valueOf(spn_transaction_category.getSpinner().getSelectedItem())));

                        ((IncomeTabFragment) _caller).onFinishAdd(giaodich);
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

    private List<String> loadCategory() {
        List<String> result = new ArrayList<>();
        List<HangMucThu> data = db.findAllHangMucThu();
        for (HangMucThu o : data) {
            result.add(o.getTenHangMuc());
        }
        return result;
    }

    private List<String> loadBalance() {
        List<String> result = new ArrayList<>();
        List<TaiKhoan> data = db.findAllTaiKhoan();
        for (TaiKhoan o : data) {
            result.add(o.getTenTaiKhoan());
        }
        return result;
    }

    public static EditKhoanThuDialog newInstance(String name, Fragment caller) {
        _caller = caller;
        EditKhoanThuDialog f = new EditKhoanThuDialog();

        Bundle args = new Bundle();
        args.putString("transactionName", name);
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
