package com.k22.nhom1.moneysaver.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.component.CustomSwipeLayout;
import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.database.domain.KhoanChi;
import com.k22.nhom1.moneysaver.database.domain.KhoanChoVay;
import com.k22.nhom1.moneysaver.database.domain.KhoanThu;
import com.k22.nhom1.moneysaver.database.domain.KhoanVay;
import com.k22.nhom1.moneysaver.dialog.EditBalanceDialog;
import com.k22.nhom1.moneysaver.dialog.EditKhoanChiDialog;
import com.k22.nhom1.moneysaver.dialog.EditKhoanChoVayDialog;
import com.k22.nhom1.moneysaver.dialog.EditKhoanThuDialog;
import com.k22.nhom1.moneysaver.dialog.EditKhoanVayDialog;
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
    //String transactionType;
    NumberFormat numberFormatter;
    SimpleDateFormat dateFormatter;
    Fragment caller;

    public TransactionAdapter(ArrayList<TransactionItem> mData, DB4OProvider db, FragmentManager fm, Fragment caller) {
        this.mData = mData;
        this.db = db;
        this.fm = fm;
        //this.transactionType = transactionType;
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


        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                editTransactionItem(viewHolder);
            }
        });

        swipeLayout.setOnClickItemListener(new CustomSwipeLayout.OnClickItemListener() {

            public void onClick(View view) {
                editTransactionItem(viewHolder);

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

    private void editTransactionItem(ViewHolder viewHolder) {
        Object item = mData.get(viewHolder.getPosition()).getDbObject();
        if (item instanceof KhoanThu) {
            EditKhoanThuDialog dialog = EditKhoanThuDialog.newInstance(((KhoanThu) item).getTenGiaoDich(), caller);
            dialog.show(fm, EditBalanceDialog.TAG);
        } else if (item instanceof KhoanChi) {
            EditKhoanChiDialog dialog = EditKhoanChiDialog.newInstance(((KhoanChi) item).getTenGiaoDich(), caller);
            dialog.show(fm, EditBalanceDialog.TAG);
        } else if (item instanceof KhoanVay) {
            EditKhoanVayDialog dialog = EditKhoanVayDialog.newInstance(((KhoanVay) item).getTenGiaoDich(), caller);
            dialog.show(fm, EditBalanceDialog.TAG);
        } else if (item instanceof KhoanChoVay) {
            EditKhoanChoVayDialog dialog = EditKhoanChoVayDialog.newInstance(((KhoanChoVay) item).getTenGiaoDich(), caller);
            dialog.show(fm, EditBalanceDialog.TAG);
        }
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
        Object obj = mData.get(position).getDbObject();
        if (obj instanceof KhoanChi) {
            db.deleteKhoanChi((KhoanChi) obj);
        } else if (obj instanceof KhoanThu) {
            db.deleteKhoanThu((KhoanThu) obj);
        } else if (obj instanceof KhoanVay) {
            db.deleteKhoanVay((KhoanVay) obj);
        } else if (obj instanceof KhoanChoVay) {
            db.deleteKhoanChoVay((KhoanChoVay) obj);
        }
        /*
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
        */
        mData.remove(position);
        closeItem(position);
        notifyItemRemoved(position);
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
