package com.tinh.dev.applove.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tinh.dev.applove.DataBase.DataBase;
import com.tinh.dev.applove.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DongHoTinhYeuService extends Service {
    private WindowManager mWindowManager;
    private View mChatHeadView;
    private TextView nam,thang,ngay,gio,phut,giay,txtNoiDung;
    private TextView txtnam;
    private TextView txtthang;
    private TextView txtngay;
    private TextView txtgio;
    private TextView txtphut;
    private TextView txtgiay;
    private DataBase dataBase;
    private int datelove;
    private LinearLayout linearLayout;
    boolean a=false;
     private   LinearLayout date;
     private ImageView thoat;
     private int id;
    private WindowManager.LayoutParams params;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.dialog_dongho, null);
        dataBase=new DataBase(this);

        int api=Integer.valueOf(Build.VERSION.SDK);
        Log.e("Tinh",api+"");
        if (api>=26) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }


        params.gravity = Gravity.CENTER | Gravity.CENTER;
        params.x = 50;
        params.y = 50;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mChatHeadView, params);
        txtnam=mChatHeadView.findViewById(R.id.txtnam);
        txtthang=mChatHeadView.findViewById(R.id.txtthang);
        txtngay=mChatHeadView.findViewById(R.id.txtngay);
        txtgio=mChatHeadView.findViewById(R.id.txtgio);
        txtphut=mChatHeadView.findViewById(R.id.txtphut);
        txtgiay=mChatHeadView.findViewById(R.id.txtgiay);

        nam=mChatHeadView.findViewById(R.id.nam);
        thang=mChatHeadView.findViewById(R.id.thang);
        ngay=mChatHeadView.findViewById(R.id.ngay);
        gio=mChatHeadView.findViewById(R.id.gio);
        phut=mChatHeadView.findViewById(R.id.phut);
        giay=mChatHeadView.findViewById(R.id.giay);
        txtNoiDung=mChatHeadView.findViewById(R.id.txtnoidung);
        linearLayout=mChatHeadView.findViewById(R.id.xxx);
        thoat=mChatHeadView.findViewById(R.id.close);
         final RelativeLayout relativeLayout=mChatHeadView.findViewById(R.id.activity_chat);
         thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(DongHoTinhYeuService.this,DongHoService.class));
                mChatHeadView.setVisibility(View.GONE);
                stopSelf();

            }
        });


         final RelativeLayout chatHeadImage = mChatHeadView.findViewById(R.id.activity_chat);
        chatHeadImage.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;


                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {


                        }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:

                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        mWindowManager.updateViewLayout(mChatHeadView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"love_girl.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"love_girl.ttf");
        txtNoiDung.setTypeface(typeface);
        nam.setTypeface(typeface);
        thang.setTypeface(typeface);
        ngay.setTypeface(typeface);
        gio.setTypeface(typeface);
        phut.setTypeface(typeface);
        giay.setTypeface(typeface);
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
        txtgio.setText(sdf.format(date)+"");
        txtphut.setText(sdf1.format(date)+"" );
         RunAble runAble=new RunAble(1,this);
         new Thread(runAble).start();
         updateDate();
         getDataName();

         return START_STICKY;

    }
    class RunAble implements Runnable {
        int seconds;
        Context context;

        public RunAble(int seconds, Context context) {
            this.seconds = seconds;
            this.context = context;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 6000; i++) {
                Handler handler = new Handler(Looper.getMainLooper());
                final int intI = i;
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        txtgiay.setText(intI+"");
                        if (intI == 6000) {
                            txtphut.setText(Integer.parseInt(txtphut.getText().toString().trim())+1+"");
                            RunAble runAble=new RunAble(1,context);
                            new Thread(runAble).start();
                        }
                    }

                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void updateDate(){
       Calendar calendarOne=Calendar.getInstance();
        Cursor cursor=dataBase.getNgayyeu();
        if (cursor!=null && cursor.moveToFirst()){
            long i=cursor.getLong(2);
           int ngay= (int) ((calendarOne.getTimeInMillis()-(i-(1000*60*60*24)))/(1000*60*60*24));
            dataBase.updateSoNgayYeu(ngay,1);
           getData();
        }
    }

    private void getData(){
        Cursor cursor = dataBase.getSoNgayYeu();
        if (cursor.moveToNext()){
            datelove=cursor.getInt(1);

            double so = datelove;
            double so1 = 365;

            double nam1 = so / so1;
            int nguyen = (int) nam1;
            txtnam.setText(nguyen+"");
            double sodu = so - (nguyen * so1);

            double thang1 = sodu / 30;
            int thangnguyen = (int) thang1;
            txtthang.setText(thangnguyen + "");

            double sodungay = sodu - (thangnguyen * 30);
            int sodungay1 = (int) sodungay;
            txtngay.setText(sodungay1 + "");
        }

    }

    private void getDataName() {
        Cursor cursor = dataBase.getTen();
        if (cursor.moveToNext()) {
            String nameboy = cursor.getString(1);
            String namegirl = cursor.getString(2);
            txtNoiDung.setText(nameboy+"❤"+datelove+"_day_love❤"+namegirl);
        }
    }



}
