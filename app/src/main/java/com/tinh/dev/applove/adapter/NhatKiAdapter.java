package com.tinh.dev.applove.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tinh.dev.applove.Diary_Fragment;
import com.tinh.dev.applove.R;
import com.tinh.dev.applove.model.NhatKiHolder;
import com.tinh.dev.applove.model.NhatKiMolder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NhatKiAdapter extends RecyclerView.Adapter<NhatKiHolder> {
    private ArrayList<NhatKiMolder> arrayList;
    private Diary_Fragment diary_fragment;

    public NhatKiAdapter(ArrayList<NhatKiMolder> arrayList, Diary_Fragment diary_fragment) {
        this.arrayList = arrayList;
        this.diary_fragment = diary_fragment;
    }

    @NonNull
    @Override
    public NhatKiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_nhatkhi,parent,false);
        Animation animation= AnimationUtils.loadAnimation(diary_fragment.getActivity(),R.anim.anim4);
        view.setAnimation(animation);
        return new NhatKiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhatKiHolder holder, final int position) {
        Typeface typeface=Typeface.createFromAsset(diary_fragment.getActivity().getAssets(),"love_girl.ttf");
        Typeface typeface1=Typeface.createFromAsset(diary_fragment.getActivity().getAssets(),"font_1.ttf");
             final NhatKiMolder nhatKiMolder=arrayList.get(position);
             holder.nhatki.setTypeface(typeface);
             holder.ngay.setTypeface(typeface1);
             SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
             holder.img.setImageURI(Uri.parse(nhatKiMolder.getLinks()));

        holder.ngay.setText(""+simpleDateFormat.format(nhatKiMolder.getNgay()));
             holder.nhatki.setText(nhatKiMolder.getNhatki());
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     diary_fragment.update(nhatKiMolder.getId(),position,nhatKiMolder);
                 }
             });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
