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
import models.Suspect;

public class GridSuspectAdapter extends BaseAdapter {
    private Activity context;
    private List<Suspect> itemList;

    public GridSuspectAdapter(Activity context, List<Suspect> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Suspect getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textViewNom, textViewPrenom, textViewAdresse,textViewDateNaissance,textViewCni;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate the grid item layout if it's not already available
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_grid_item_suspect, null,true);

            // Create a ViewHolder to optimize performance by reusing views
            holder = new ViewHolder();
            holder.textViewNom = convertView.findViewById(R.id.textViewNom);
            holder.textViewPrenom = convertView.findViewById(R.id.textViewPrenom);
            holder.textViewAdresse = convertView.findViewById(R.id.textViewAdresse);
            holder.textViewDateNaissance = convertView.findViewById(R.id.textViewDateNaissance);
            holder.textViewCni = convertView.findViewById(R.id.textViewCni);
            holder.imageView = convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            // Reuse the existing views
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the data for the current position
        Suspect item = itemList.get(i);

        // Set the data to the views
        if(item.getPhoto() != null)
            holder.imageView.setImageBitmap(item.getPhoto());
        holder.textViewNom.setText(item.getNom());
        holder.textViewPrenom.setText(item.getPrenom());
        holder.textViewAdresse.setText(item.getAdresse());
        holder.textViewDateNaissance.setText(item.getDate_naissance());
        holder.textViewCni.setText(item.getCni());

        return convertView;
    }
}
