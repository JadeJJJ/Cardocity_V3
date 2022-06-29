package com.varsitycollege.cardocity_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.varsitycollege.cardocity_app.Item;
import com.varsitycollege.cardocity_app.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Item> {

    public MyAdapter(Context context, ArrayList<Item> ItemArrayList) {
        super(context, 0, ItemArrayList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View myView = convertView;
        if (myView == null)
        {
            myView = LayoutInflater.from(getContext()).inflate(R.layout.activity_grid_view_items, parent, false);
        }
        Item myItem= getItem(position);
        TextView txtNameView = myView.findViewById(R.id.txtName);
        TextView txtTypeView = myView.findViewById(R.id.txtType);
        TextView txtNumView = myView.findViewById(R.id.txtNums);
        txtNameView.setText(myItem.getCardName());
        txtTypeView.setText(myItem.getCardType());
        txtNumView.setText(myItem.getNumberOfCards().toString());

        return myView;
    }
}
