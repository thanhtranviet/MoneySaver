package com.k22.nhom1.moneysaver.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.component.CustomSwipeLayout;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.GiaoDich;
import com.k22.nhom1.moneysaver.database.domain.KhoanChi;
import com.k22.nhom1.moneysaver.database.domain.KhoanChoVay;
import com.k22.nhom1.moneysaver.database.domain.KhoanThu;
import com.k22.nhom1.moneysaver.database.domain.KhoanVay;
import com.k22.nhom1.moneysaver.dialog.EditBalanceDialog;
import com.k22.nhom1.moneysaver.dialog.EditKhoanThuDialog;
import com.k22.nhom1.moneysaver.model.TransactionItem;
import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.malinskiy.superrecyclerview.swipe.SwipeLayout;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransactionAdapter extends BaseSwipeAdapter<TransactionAdapter.ViewHolder> {

    private ArrayList<TransactionItem> mData;
    private DB4OProvider db;
    FragmentManager fm;
    String transactionType;
    NumberFormat numberFormatter;
    SimpleDateFormat dateFormatter;
    Fragment caller;

    public TransactionAdapter(ArrayList<TransactionItem> mData, DB4OProvider db, FragmentManager fm, String transactionType, Fragment caller) {
        this.mData = mData;
        this.db = db;
        this.fm = fm;
        this.transactionType = transactionType;
        this.caller = caller;
        numberFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.base_swipeable_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        CustomSwipeLayout swipeLayout = (CustomSwipeLayout) viewHolder.swipeLayout;
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        /*
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        */
        swipeLayout.setOnClickItemListener(new CustomSwipeLayout.OnClickItemListener() {

            public void onClick(View view) {
                TransactionItem item = mData.get(viewHolder.getPosition());
                if (!TextUtils.isEmpty(transactionType)) {
                    EditKhoanThuDialog dialog = EditKhoanThuDialog.newInstance(item.getTenGiaoDich(), caller);
                    dialog.show(fm, EditBalanceDialog.TAG);
                }

            }

        });
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionItem item = mData.get(viewHolder.getPosition());
                remove(viewHolder.getPosition());
                Toast.makeText(v.getContext(), "Deleted " + item.getTenGiaoDich(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        TransactionItem item = mData.get(position);
        holder.title.setText(item.getTenGiaoDich());
        String date = "";
        try {
            date = dateFormatter.format(item.getNgayGiaoDich());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.txtSummary.setText("[" + item.getTransactionType() + "] " + date);
        holder.txtAmount.setText(numberFormatter.format(item.getSoTien()));
        holder.txtNote.setText(item.getGhiChu());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(TransactionItem item) {
        insert(item, mData.size());
    }

    public void reload(ArrayList<TransactionItem> data) {
        closeAllExcept(null);
        mData.clear();
        for (TransactionItem item : data) {
            mData.add(item);
        }
        notifyDataSetChanged();
    }

    public void insert(TransactionItem item, int position) {
        closeAllExcept(null);

        mData.add(position, item);

        notifyItemInserted(position);
    }

    public void remove(int position) {
        TransactionItem item = mData.get(position);
        if (transactionType != null) {
            switch (transactionType) {
                case GiaoDich.KHOAN_CHI:
                    db.deleteKhoanChi((KhoanChi) item.getDbObject());
                    break;
                case GiaoDich.KHOAN_THU:
                    db.deleteKhoanThu((KhoanThu) item.getDbObject());
                    break;
                case GiaoDich.KHOAN_VAY:
                    db.deleteKhoanVay((KhoanVay) item.getDbObject());
                    break;
                case GiaoDich.KHOAN_CHOVAY:
                    db.deleteKhoanChoVay((KhoanChoVay) item.getDbObject());
                    break;
            }

            mData.remove(position);
            closeItem(position);
            notifyItemRemoved(position);
        }
    }

    public static class ViewHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {
        public TextView title;
        public TextView txtSummary;
        public TextView txtAmount;
        public TextView txtNote;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            txtSummary = (TextView) itemView.findViewById(R.id.txtSummary);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            txtNote = (TextView) itemView.findViewById(R.id.txtNote);
            deleteButton = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
