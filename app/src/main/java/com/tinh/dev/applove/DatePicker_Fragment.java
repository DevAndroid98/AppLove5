package com.tinh.dev.applove;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tinh.dev.applove.DataBase.DataBase;
import com.tinh.dev.applove.adapter.DateAdapter;
import com.tinh.dev.applove.model.DateMolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatePicker_Fragment extends Base_Fragment {

    private RelativeLayout toolbar;
    private LinearLayout xxx;
    private ImageView imgLove;
    private TextView txtnam;
    private ImageView imgLove1;
    private TextView txtthang;
    private ImageView imgLove2;
    private TextView txtngay;
    private ImageView imgLove3;
    private TextView txtgio;
    private ImageView imgLove4;
    private TextView txtphut;
    private ImageView imgLove5;
    private TextView txtgiay;
    private TextView nam;
    private TextView thang;
    private TextView ngay;
    private TextView gio;
    private TextView phut;
    private TextView giay;
    private TextView txtNgayYeu;
    private RecyclerView recyclerview;

    private ArrayList<DateMolder> dateMolders;
    private DateAdapter dateAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datepicker_fragment, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        xxx = view.findViewById(R.id.xxx);
        imgLove = view.findViewById(R.id.img_love);
        txtnam = view.findViewById(R.id.txtnam);
        imgLove1 = view.findViewById(R.id.img_love1);
        txtthang = view.findViewById(R.id.txtthang);
        imgLove2 = view.findViewById(R.id.img_love2);
        txtngay = view.findViewById(R.id.txtngay);
        imgLove3 = view.findViewById(R.id.img_love3);
        txtgio = view.findViewById(R.id.txtgio);
        imgLove4 = view.findViewById(R.id.img_love4);
        txtphut = view.findViewById(R.id.txtphut);
        imgLove5 = view.findViewById(R.id.img_love5);
        txtgiay = view.findViewById(R.id.txtgiay);
        nam = view.findViewById(R.id.nam);
        thang = view.findViewById(R.id.thang);
        ngay = view.findViewById(R.id.ngay);
        gio = view.findViewById(R.id.gio);
        phut = view.findViewById(R.id.phut);
        giay = view.findViewById(R.id.giay);
        txtNgayYeu = view.findViewById(R.id.txtNgayYeu);
        recyclerview = view.findViewById(R.id.recyclerview);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font_1.ttf");
        nam.setTypeface(typeface);
        thang.setTypeface(typeface);
        ngay.setTypeface(typeface);
        gio.setTypeface(typeface);
        phut.setTypeface(typeface);
        giay.setTypeface(typeface);
        txtgiay.setTypeface(typeface);
        txtphut.setTypeface(typeface);
        txtgio.setTypeface(typeface);
        txtngay.setTypeface(typeface);
        txtthang.setTypeface(typeface);
        txtnam.setTypeface(typeface);
        txtNgayYeu.setTypeface(typeface);
        final Date date = new Date();
        String strDateFormat24 = "HH";
        String strDateFormat25 = "mm";
        String strDateFormat26 = "ss";
        SimpleDateFormat sdf = null;
        SimpleDateFormat sdf1 = null;
        SimpleDateFormat sdf2 = null;

        sdf = new SimpleDateFormat(strDateFormat24);
        sdf1 = new SimpleDateFormat(strDateFormat25);
        sdf2 = new SimpleDateFormat(strDateFormat26);
        txtgio.setText(sdf.format(date) + "");
        txtphut.setText(sdf1.format(date) + "");
        txtgiay.setText(sdf2.format(date) + "");
        RunAble runAble1 = new RunAble(1);
        new Thread(runAble1).start();
        dataBase = new DataBase(getActivity());
        getData();
        String ngayyeu = null;
        Cursor cursor = dataBase.getNgayyeu();
        if (cursor != null && cursor.moveToFirst()) {
            ngayyeu = cursor.getString(1);
        }

        txtNgayYeu.setText("❤" + ngayyeu + "❤");

        add1();
        return view;
    }

    class RunAble implements Runnable {
        int seconds;

        public RunAble(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 1; i++) {
                Handler handler = new Handler(Looper.getMainLooper());
                final int intI = i;
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {

                        if (intI == 1) {
                            final Date date = new Date();
                            String strDateFormat24 = "HH";
                            String strDateFormat25 = "mm";
                            String strDateFormat26 = "ss";
                            SimpleDateFormat sdf = null;
                            SimpleDateFormat sdf1 = null;
                            SimpleDateFormat sdf2 = null;

                            sdf = new SimpleDateFormat(strDateFormat24);
                            sdf1 = new SimpleDateFormat(strDateFormat25);
                            sdf2 = new SimpleDateFormat(strDateFormat26);
                            txtgio.setText(sdf.format(date) + "");
                            txtphut.setText(sdf1.format(date) + "");
                            txtgiay.setText(sdf2.format(date) + "");
                            Cursor cursor = dataBase.getNgayyeu();
                            getData();

                            String ngayyeu = null;
                            if (cursor != null && cursor.moveToFirst()) {
                                ngayyeu = cursor.getString(1);
                            }
                            txtNgayYeu.setText("❤" + ngayyeu + "❤");
                            RunAble runAble1 = new RunAble(1);
                            new Thread(runAble1).start();
                        }

                    }
                });


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public void getData() {
        Cursor cursor = dataBase.getSoNgayYeu();
        if (cursor.moveToNext()) {
            int datelove = cursor.getInt(1);

            double so = datelove;
            double so1 = 365;

            double nam1 = so / so1;
            int nguyen = (int) nam1;
            txtnam.setText(nguyen + "");
            double sodu = so - (nguyen * so1);

            double thang1 = sodu / 30;
            int thangnguyen = (int) thang1;
            txtthang.setText(thangnguyen + "");

            double sodungay = sodu - (thangnguyen * 30);
            int sodungay1 = (int) sodungay;
            txtngay.setText(sodungay1 + "");
        }

    }



    public void add1(){
        dateMolders=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        dateMolders.clear();
        Cursor cursor = dataBase.getNgayyeu();
        Cursor cursor1 = dataBase.getSoNgayYeu();
        calendarOne=Calendar.getInstance();
        int songayyeu = 0;
        if (cursor1 != null && cursor1.moveToFirst()) {
            songayyeu = cursor1.getInt(1);
        }
        long ngayyeu = 0;
        if (cursor != null && cursor.moveToFirst()) {
            ngayyeu = cursor.getLong(2);
            Log.e("xxx",ngayyeu+"");
        }
        //long ngay=ngayyeu+(1000*60*60*24);
        dateMolders.clear();
        dateMolders.add(new DateMolder(songayyeu+"",calendarOne.getTimeInMillis()));
        Log.e("x",calendarOne.getTimeInMillis()+"");

        for (int i=1;i<=24;i++){
            long date=calendarOne.getTimeInMillis()+(1000*60*60*24*i);
            int ngayyeu1=songayyeu+i;
            dateMolders.add(new DateMolder(ngayyeu1+"",date));
        }

        dateAdapter=new DateAdapter(dateMolders,getActivity());
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(dateAdapter);

    }
}
