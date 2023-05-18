package com.example.enquete;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import models.Preuve;

public class GridPreuveAdapter extends BaseAdapter {
    private Activity context;
    private List<Preuve> itemList;

    public GridPreuveAdapter(Activity context, List<Preuve> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Preuve getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate the grid item layout if it's not already available
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_grid_item_preuve, null,true);

            // Create a ViewHolder to optimize performance by reusing views
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            // Reuse the existing views
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the data for the current position
        Preuve item = itemList.get(i);

        // Set the data to the views
        holder.imageView.setImageBitmap(item.getImage());
        holder.textView.setText(item.getLieu());

        return convertView;
    }
}
