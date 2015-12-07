package com.k22.nhom1.moneysaver.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.component.LabelledSpinner;
import com.k22.nhom1.moneysaver.fragment.BalanceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thanh on 07/12/2015.
 */
public class EditBalanceDialog extends DialogFragment {
    public static final String TAG = "EDIT_BALANCE_FRAGMENT_TAG";

    private EditText mEditText;

    public EditBalanceDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        String mBalanceName = getArguments().getString("balanceName");

        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_balance, null);
        String title = getActivity().getString(R.string.new_account_balance);
        if (!TextUtils.isEmpty(mBalanceName)) {
            title = getActivity().getString(R.string.edit_account_balance);//"Edit Account Balance";
            mEditText = (EditText) view.findViewById(R.id.txt_balance_name);
            mEditText.setText(mBalanceName);
        }
        // Spinner element
        LabelledSpinner spn_category = (LabelledSpinner) view.findViewById(R.id.spn_category);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(getActivity().getString(R.string.cash));
        categories.add("Bank Account");
        categories.add("ATM");
        categories.add("Other");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_category.setItemsArray((ArrayList<String>) categories);

        b.setView(view).setTitle(title).setIcon(R.drawable.ic_account_balance_black_24dp);
        b.setPositiveButton(getActivity().getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mEditText = (EditText) view.findViewById(R.id.txt_balance_name);
                        ((BalanceFragment) getParentFragment()).onFinishAddBalance(mEditText.getText().toString());
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
}
