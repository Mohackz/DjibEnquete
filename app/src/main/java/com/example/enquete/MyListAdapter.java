package com.example.enquete;
import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;

    public MyListAdapter(Activity context, String[] maintitle,String[] subtitle) {
        super(context, R.layout.list_view_enquete, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view_enquete, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.list_titre);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.list_description);

        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    };
}