package com.jackbbb95.globe.countr.Handlers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;

import java.util.List;

public class CountrAdapter extends ArrayAdapter<Countr> {
    Context context;

    //Constructor for the adapter, extends ArrayAdapter. Sets context to this class
    public CountrAdapter(Context context, int resourceID, List<Countr> items){
        super(context,resourceID,items);
        this.context = context;
    }

    //Holds the two TextViews to be displayed in each item of the ListView
    private class ViewHolder {
        TextView mainText;
        TextView intervalText;
        TextView subText;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder; //create holder
        Countr countr = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){ //used to recycle views to save resources
            convertView = mInflater.inflate(R.layout.list_item_countr,null); //inflates the item
            Typeface robotoLight = Typeface.createFromAsset(getContext().getAssets(),"fonts/roboto.light.ttf");
            holder = new ViewHolder(); //initializes holder
            holder.mainText = (TextView) convertView.findViewById(R.id.tv_name);
            //holder.mainText.setTypeface(robotoLight);
            holder.subText = (TextView) convertView.findViewById(R.id.tv_count); //sets the recycled view to current items layout
            //holder.subText.setTypeface(robotoLight);
            holder.intervalText = (TextView) convertView.findViewById(R.id.tv_interval);
            //holder.intervalText.setTypeface(robotoLight);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.mainText.setText(String.valueOf(countr.getName())); //mainText holds the name of the countr
        holder.subText.setText(String.valueOf(countr.getCurrentNumber())); //subText holds the current count
        holder.intervalText.setText(String.format("±%s", String.valueOf(countr.getCountBy()))); //intervalText holds the current interval


        return convertView; //returns the recycled view item
    }

}
