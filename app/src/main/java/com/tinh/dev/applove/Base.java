package com.tinh.dev.applove;


import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

import com.tinh.dev.applove.DataBase.DataBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Base extends AppCompatActivity {

    public Typeface typeface, typeface1;
    public Animation animation1, animation,animation3;
    public DataBase dataBase;
    public Calendar calendarOne, calendarTwo;
    public SimpleDateFormat simpleDateFormat;
    public Cursor cursorData, cursorData1;
    public int REQUEST_CODE_BACKGROUND = 789;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
