package com.jackbbb95.globe.countr;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Countr> {
    Context context;
    /*
    Constructor for the adapter, extends ArrayAdapter. Sets context to this class
     */
    public CustomListViewAdapter(Context context, int resourceID, List<Countr> items){
        super(context,resourceID,items);
        this.context = context;
    }

    /*
    Holds the two TextViews to be displayed in each item of the ListView
     */
    private class ViewHolder {
        TextView mainText;
        TextView subText;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null; //create holder
        Countr countr = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){ //used to recycle views to save resources
            convertView = mInflater.inflate(R.layout.list_item_countr,null); //inflates the item
            holder = new ViewHolder(); //initializes holder
            holder.subText = (TextView) convertView.findViewById(R.id.sub_list_item_countr_textview); //sets the recycled view to current items layout
            holder.mainText = (TextView) convertView.findViewById(R.id.list_item_countr_textview);
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.subText.setText(String.valueOf(countr.getCurrentNumber())); //subText holds the current count
        holder.mainText.setText(String.valueOf(countr.getName())); //mainText holds the name of the countr

        return convertView; //returns the recycled view item
    }

}
