package com.tinh.dev.applove.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tinh.dev.applove.R;

public class NhatKiHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView ngay;
    public TextView nhatki;



    public NhatKiHolder(View itemView) {
        super(itemView);
        img =  itemView.findViewById(R.id.img);
        ngay = itemView.findViewById(R.id.ngay);
        nhatki =  itemView.findViewById(R.id.nhatki);
    }
}
