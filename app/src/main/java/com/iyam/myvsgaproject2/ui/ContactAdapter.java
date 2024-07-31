/* *    Hi, Code Enthusiast! *    https://github.com/yudiatmoko *//* *    Hi, Code Enthusiast! *    https://github.com/yudiatmoko */package com.iyam.myvsgaproject2.ui;import android.content.Context;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ArrayAdapter;import android.widget.TextView;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import com.iyam.myvsgaproject2.model.Contact;import java.util.ArrayList;public class ContactAdapter extends ArrayAdapter<Contact> {    public ContactAdapter(@NonNull Context context) {        super(context, 0, new ArrayList<>());    }    @NonNull    @Override    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {        View itemView = convertView;        if (itemView == null){            itemView = LayoutInflater.from(getContext()).inflate(                    android.R.layout.simple_list_item_2, parent, false            );        }        Contact contactItem = getItem(position);        TextView tvName = itemView.findViewById(android.R.id.text1);        tvName.setText(contactItem.getName());        TextView tvAddress = itemView.findViewById(android.R.id.text2);        tvAddress.setText(contactItem.getAddress());        return itemView;    }}