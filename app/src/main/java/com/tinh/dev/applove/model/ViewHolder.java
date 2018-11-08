package com.tinh.dev.applove.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tinh.dev.applove.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView date;
    public TextView ngaykiniem;



    public ViewHolder(View itemView) {
        super(itemView);
        date =  itemView.findViewById(R.id.date);
        ngaykiniem = itemView.findViewById(R.id.ngaykiniem);
    }
}
