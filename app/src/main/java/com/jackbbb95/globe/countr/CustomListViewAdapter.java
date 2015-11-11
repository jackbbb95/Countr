package com.jackbbb95.globe.countr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Countr> {
    Context context;
    public CustomListViewAdapter(
            Context context,
            int resourceID,
            List<Countr> items){
        super(context,resourceID,items);
        this.context = context;
    }
    private class ViewHolder {
        TextView mainText;
        TextView subText;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        Countr countr = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_countr,null);
            holder = new ViewHolder();
            holder.subText = (TextView) convertView.findViewById(R.id.sub_list_item_countr_textview);
            holder.mainText = (TextView) convertView.findViewById(R.id.list_item_countr_textview);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.subText.setText(String.valueOf(countr.getCurrentNumber()));
        holder.mainText.setText(String.valueOf(countr.getName()));
        return convertView;
    }

}
