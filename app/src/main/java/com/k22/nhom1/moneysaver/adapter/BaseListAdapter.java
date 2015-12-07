package com.k22.nhom1.moneysaver.adapter;

/**
 * Created by thanh on 07/12/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.model.BaseListItem;

import java.util.List;

public class BaseListAdapter extends ArrayAdapter {

    private Context context;

    public BaseListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder {
        TextView titleText;
        TextView valueText;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        BaseListItem item = (BaseListItem) getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            viewToUse = mInflater.inflate(R.layout.base_list_item, null);


            holder = new ViewHolder();
            holder.titleText = (TextView) viewToUse.findViewById(R.id.titleTextView);
            holder.valueText = (TextView) viewToUse.findViewById(R.id.valueTextView);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.titleText.setText(item.getItemTitle());
        holder.valueText.setText(item.getItemValue());
        return viewToUse;
    }
}
