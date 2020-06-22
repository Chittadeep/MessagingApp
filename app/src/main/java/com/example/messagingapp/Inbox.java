/**package com.example.messagingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Inbox extends ArrayAdapter<Message> {

    private Context mContext;
    int mResource;

    public Inbox(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        long number = getItem(position).getNum();
        String text = getItem(position).getMessage();

        try {
            Message message = new Message(number, text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView num = (TextView) convertView.findViewById(R.id.number);
        TextView message =(TextView) convertView.findViewById(R.id.message);

        num.setText(Long.toString(number));
        message.setText(text);
        return convertView;
    }
}*/
