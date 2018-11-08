package com.tinh.dev.applove.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tinh.dev.applove.Base_Fragment;
import com.tinh.dev.applove.R;
import com.tinh.dev.applove.model.DateMolder;
import com.tinh.dev.applove.model.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DateAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<DateMolder> dateMolders;
    private Context context;

    public DateAdapter(ArrayList<DateMolder> dateMolders, Context context) {
        this.dateMolders = dateMolders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_date,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"font_1.ttf");
         DateMolder dateMolder=dateMolders.get(position);
         holder.date.setTypeface(typeface);
         holder.ngaykiniem.setTypeface(typeface);
         holder.date.setText("❤"+dateMolder.getDay()+"-ngày yêu");
         holder.ngaykiniem.setText(simpleDateFormat.format(dateMolder.getNgaykiniem())+"");
    }

    @Override
    public int getItemCount() {
        return dateMolders.size();
    }
}
