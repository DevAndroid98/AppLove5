package com.tinh.dev.applove.Service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.tinh.dev.applove.DataBase.BaseNgaySinhNhat;
import com.tinh.dev.applove.DataBase.DataBase;
import com.tinh.dev.applove.R;
import java.util.Calendar;

import me.itangqi.waveloadingview.WaveLoadingView;
import pl.droidsonroids.gif.GifImageView;

public class HinhNenService extends Service {
    private WindowManager mWindowManager;
    private View mChatHeadView;
    String nameBoy;
    String nameGirl;
    int i;
    private DataBase dataBase;



    ImageView a;
    ImageView aa;
    WaveLoadingView loaddingWave;
    LinearLayout linearLayout;
    CircularImageView imgBoy;
    TextView txtHanhPhuc;
    CircularImageView imgGirl;
    LinearLayout linearLayout1;
    TextView txtNameBoy;
    TextView txtNameGirl;
    ImageView closeBtn;
    LinearLayout relativeLayout;
    CardView test;
    TextView txtAgeBoy;
    TextView txtCungBoy;
    TextView txtAgeGirl;
    TextView txtCungGirl;

    TextView txtTopwawe;
    TextView txtCenterwawe;
    TextView txtBottomwawe;
    private Cursor cursor, cursor1, cursor3, cursor4;
    private BaseNgaySinhNhat baseNgaySinhNhat;
    private WindowManager.LayoutParams params;
    private String uribackground, uriGirl, uriBoy;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    public HinhNenService() {
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {

       return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim3);
        mChatHeadView.setAnimation(animation);
        //Add the view to the window.
        int api=Integer.valueOf(android.os.Build.VERSION.SDK);
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
//TYPE_APPLICATION_OVERLAY
        //Specify the chat head position
//Initially view will be added to top-left corner
        params.gravity = Gravity.CENTER | Gravity.CENTER;
        params.x = 50;
        params.y = 50;
        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mChatHeadView, params);



        dataBase=new DataBase(this);

        baseNgaySinhNhat=new BaseNgaySinhNhat(this);



       ImageView imageView=mChatHeadView.findViewById(R.id.cc);

        aa =  mChatHeadView.findViewById(R.id.aa);
        a = (ImageView) mChatHeadView.findViewById(R.id.a);
        loaddingWave = (WaveLoadingView) mChatHeadView.findViewById(R.id.loaddingWave);
        txtTopwawe = (TextView) mChatHeadView.findViewById(R.id.txtTopwawe);
        txtCenterwawe = (TextView) mChatHeadView.findViewById(R.id.txtCenterwawe);
        txtBottomwawe = (TextView) mChatHeadView.findViewById(R.id.txtBottomwawe);
        linearLayout = (LinearLayout) mChatHeadView.findViewById(R.id.linearLayout);
        imgBoy = (CircularImageView) mChatHeadView.findViewById(R.id.imgBoy);
        txtHanhPhuc = (TextView) mChatHeadView.findViewById(R.id.txtHanhPhuc);
        imgGirl = (CircularImageView) mChatHeadView.findViewById(R.id.imgGirl);
        linearLayout1 = (LinearLayout) mChatHeadView.findViewById(R.id.linearLayout1);
        txtNameBoy = (TextView) mChatHeadView.findViewById(R.id.txtNameBoy);
        txtNameGirl = (TextView) mChatHeadView.findViewById(R.id.txtNameGirl);
        closeBtn = (ImageView) mChatHeadView.findViewById(R.id.close_btn);
        relativeLayout = (LinearLayout) mChatHeadView.findViewById(R.id.relativeLayout);
        test = (CardView) mChatHeadView.findViewById(R.id.test);
        txtAgeBoy = (TextView) mChatHeadView.findViewById(R.id.txtAgeBoy);
        txtCungBoy = (TextView) mChatHeadView.findViewById(R.id.txtCungBoy);
        txtAgeGirl = (TextView) mChatHeadView.findViewById(R.id.txtAgeGirl);
        txtCungGirl = (TextView) mChatHeadView.findViewById(R.id.txtCungGirl);


         Animation animation1=AnimationUtils.loadAnimation(this,R.anim.anim9);
         imageView.setAnimation(animation1);
        a=mChatHeadView.findViewById(R.id.aa);
        imgBoy.setAnimation(animation);
        imgGirl.setAnimation(animation);
        linearLayout.setAnimation(animation);
        txtHanhPhuc.setAnimation(animation);
        txtNameBoy.setAnimation(animation);
        txtNameGirl.setAnimation(animation);
        relativeLayout.setAnimation(animation);
        test.setAnimation(animation);



        Typeface typeface=Typeface.createFromAsset(getAssets(),"love_girl.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"font_holidays.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"font_1.ttf");
        txtNameBoy.setTypeface(typeface);
        txtNameGirl.setTypeface(typeface);
        txtHanhPhuc.setTypeface(typeface);
          txtBottomwawe.setTypeface(typeface);
          txtCenterwawe.setTypeface(typeface2);
          txtTopwawe.setTypeface(typeface);
        txtAgeBoy.setTypeface(typeface1);
        txtCungBoy.setTypeface(typeface1);
        txtAgeGirl.setTypeface(typeface1);
        txtCungGirl.setTypeface(typeface1);





        ImageView closeButton = (ImageView) mChatHeadView.findViewById(R.id.close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(HinhNenService.this, ChatHeadService.class));
                stopSelf();
                mChatHeadView.setVisibility(View.GONE);

            }
        });

        //Drag and move chat head using user's touch action.
        final ImageView chatHeadImage = (ImageView) mChatHeadView.findViewById(R.id.a);
        chatHeadImage.setAnimation(animation);
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

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if (lastAction == MotionEvent.ACTION_DOWN) {

                            }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
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

         nameBoy=intent.getStringExtra("nameBoy");
         nameGirl=intent.getStringExtra("nameGirl");
         i=intent.getIntExtra("SoNgayYeu",0);
         txtNameBoy.setText(nameBoy);
         txtNameGirl.setText(nameGirl);
         updateDate();
         CungHoangDao();
         requestRead();
         return super.onStartCommand(intent, flags, startId);
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
            getDataDate();
        }
    }

    public void getDataDate() {
        Cursor cursor = dataBase.getSoNgayYeu();
        if (cursor.moveToNext()) {
            int datelove = cursor.getInt(1);

            if (datelove > 0 && datelove <= 1000) {
                if (datelove < 300) {
                    loaddingWave.setProgressValue(25);
                    txtCenterwawe.setText(String.valueOf(datelove));
                } else if (datelove < 600) {

                    loaddingWave.setProgressValue(50);
                    txtCenterwawe.setText(String.valueOf(datelove));

                } else if (datelove < 900) {

                    loaddingWave.setProgressValue(75);
                    txtCenterwawe.setText(String.valueOf(datelove));

                } else if (datelove <= 1000) {

                    loaddingWave.setProgressValue(99);
                    txtCenterwawe.setText(String.valueOf(datelove));

                }

            } else if (datelove <= 2000) {
                if (datelove < 1300) {
                    loaddingWave.setProgressValue(25);
                    txtCenterwawe.setText(String.valueOf(datelove));
                } else if (datelove < 1600) {

                    loaddingWave.setProgressValue(50);
                    txtCenterwawe.setText(String.valueOf(datelove));

                } else if (datelove < 1900) {

                    loaddingWave.setProgressValue(75);
                    txtCenterwawe.setText(String.valueOf(datelove));

                } else if (datelove <= 2000) {

                    loaddingWave.setProgressValue(99);
                    txtCenterwawe.setText(String.valueOf(datelove));

                }


            } else if (datelove <= 3000) {
                if (datelove < 2300) {
                    loaddingWave.setProgressValue(25);
                    txtCenterwawe.setText(String.valueOf(datelove));
                } else if (datelove < 2600) {

                    loaddingWave.setProgressValue(50);
                    txtCenterwawe.setText(String.valueOf(datelove));

                } else if (datelove < 2900) {

                    loaddingWave.setProgressValue(75);
                    txtCenterwawe.setText(String.valueOf(datelove));

                } else if (datelove <= 3000) {

                    loaddingWave.setProgressValue(99);
                    txtCenterwawe.setText(String.valueOf(datelove));

                }


            } else {
                loaddingWave.setProgressValue(50);
                txtCenterwawe.setText(String.valueOf(datelove));
            }


        }


    }





    private void CungHoangDao() {
       Calendar calendarOne = Calendar.getInstance();
        int namhientai = calendarOne.get(Calendar.YEAR);
        Cursor b = baseNgaySinhNhat.getNam();
        Cursor c = baseNgaySinhNhat.getNu();
        if (b.moveToNext() && c.moveToNext()) {
            int ngay = b.getInt(1);
            int thang = b.getInt(2);
            int nam = b.getInt(3);
            int ngay3 = c.getInt(1);
            int thang3 = c.getInt(2);
            int nam3 = c.getInt(3);
            int tuoiNam = namhientai - nam;
            int tuoiNu = namhientai - nam3;
            if (tuoiNam == 0) {
                txtAgeBoy.setText("?");
            } else {
                txtAgeBoy.setText(tuoiNam + "");
            }
            if (tuoiNu == 0) {
                txtAgeGirl.setText("?");
            } else {
                txtAgeGirl.setText(tuoiNu + "");
            }
            if (ngay >= 21 && ngay <= 31 && thang == 3) {
                txtCungBoy.setText("Bạch Dương");
            }

            if (ngay >= 1 && ngay <= 20 && thang == 4) {
                txtCungBoy.setText("Bạch Dương");
            }

            if (ngay >= 21 && ngay <= 31 && thang == 4) {
                txtCungBoy.setText("Kim Ngưu");
            }

            if (ngay >= 1 && ngay <= 20 && thang == 5) {
                txtCungBoy.setText("Kim Ngưu");
            }

            if (ngay >= 21 && ngay <= 31 && thang == 5) {
                txtCungBoy.setText("Song Tử");
            }

            if (ngay >= 1 && ngay <= 21 && thang == 6) {
                txtCungBoy.setText("Song Tử");
            }

            if (ngay >= 22 && ngay <= 31 && thang == 6) {
                txtCungBoy.setText("Cự Giải");
            }

            if (ngay >= 1 && ngay <= 22 && thang == 7) {
                txtCungBoy.setText("Cự Giải");
            }

            if (ngay >= 23 && ngay <= 31 && thang == 7) {
                txtCungBoy.setText("Sư Tử");
            }

            if (ngay >= 1 && ngay <= 22 && thang == 8) {
                txtCungBoy.setText("Sư Tử");
            }

            if (ngay >= 23 && ngay <= 31 && thang == 8) {
                txtCungBoy.setText("Xử Nữ");
            }

            if (ngay >= 1 && ngay <= 22 && thang == 9) {
                txtCungBoy.setText("Xử Nữ");
            }

            if (ngay >= 23 && ngay <= 31 && thang == 9) {
                txtCungBoy.setText("Thiên Bình");
            }

            if (ngay >= 1 && ngay <= 23 && thang == 10) {
                txtCungBoy.setText("Thiên Bình");
            }

            if (ngay >= 24 && ngay <= 31 && thang == 10) {
                txtCungBoy.setText("Bọ Cạp");
            }

            if (ngay >= 1 && ngay <= 22 && thang == 11) {
                txtCungBoy.setText("Bọ Cạp");
            }

            if (ngay >= 23 && ngay <= 31 && thang == 11) {
                txtCungBoy.setText("Nhân Mã");
            }

            if (ngay >= 1 && ngay <= 21 && thang == 12) {
                txtCungBoy.setText("Nhân Mã");
            }
            if (ngay >= 22 && ngay <= 31 && thang == 12) {
                txtCungBoy.setText("Ma Kết");
            }

            if (ngay >= 1 && ngay <= 19 && thang == 1) {
                txtCungBoy.setText("Ma Kết");
            }

            if (ngay >= 20 && ngay <= 31 && thang == 1) {
                txtCungBoy.setText("Bảo Bình");
            }

            if (ngay >= 1 && ngay <= 18 && thang == 2) {
                txtCungBoy.setText("Bảo Bình");
            }

            if (ngay >= 19 && ngay <= 31 && thang == 2) {
                txtCungBoy.setText("Song Ngư");
            }

            if (ngay >= 1 && ngay <= 20 && thang == 3) {
                txtCungBoy.setText("Song Ngư");
            }


            if (ngay3 >= 21 && ngay3 <= 31 && thang3 == 3) {
                txtCungGirl.setText("Bạch Dương");
            }

            if (ngay3 >= 1 && ngay3 <= 20 && thang3 == 4) {
                txtCungGirl.setText("Bạch Dương");
            }

            if (ngay3 >= 21 && ngay3 <= 31 && thang3 == 4) {
                txtCungGirl.setText("Kim Ngưu");
            }

            if (ngay3 >= 1 && ngay3 <= 20 && thang3 == 5) {
                txtCungGirl.setText("Kim Ngưu");
            }

            if (ngay3 >= 21 && ngay3 <= 31 && thang3 == 5) {
                txtCungGirl.setText("Song Tử");
            }

            if (ngay3 >= 1 && ngay3 <= 21 && thang3 == 6) {
                txtCungGirl.setText("Song Tử");
            }

            if (ngay3 >= 22 && ngay3 <= 31 && thang3 == 6) {
                txtCungGirl.setText("Cự Giải");
            }

            if (ngay3 >= 1 && ngay3 <= 22 && thang3 == 7) {
                txtCungGirl.setText("Cự Giải");
            }

            if (ngay3 >= 23 && ngay3 <= 31 && thang3 == 7) {
                txtCungGirl.setText("Sư Tử");
            }

            if (ngay3 >= 1 && ngay3 <= 22 && thang3 == 8) {
                txtCungGirl.setText("Sư Tử");
            }

            if (ngay3 >= 23 && ngay3 <= 31 && thang3 == 8) {
                txtCungGirl.setText("Xử Nữ");
            }

            if (ngay3 >= 1 && ngay3 <= 22 && thang3 == 9) {
                txtCungGirl.setText("Xử Nữ");
            }

            if (ngay3 >= 23 && ngay3 <= 31 && thang3 == 9) {
                txtCungGirl.setText("Thiên Bình");
            }

            if (ngay3 >= 1 && ngay3 <= 23 && thang3 == 10) {
                txtCungGirl.setText("Thiên Bình");
            }

            if (ngay3 >= 24 && ngay3 <= 31 && thang3 == 10) {
                txtCungGirl.setText("Bọ Cạp");
            }

            if (ngay3 >= 1 && ngay3 <= 22 && thang3 == 11) {
                txtCungGirl.setText("Bọ Cạp");
            }

            if (ngay3 >= 23 && ngay3 <= 31 && thang3 == 11) {
                txtCungGirl.setText("Nhân Mã");
            }

            if (ngay3 >= 1 && ngay3 <= 21 && thang3 == 12) {
                txtCungGirl.setText("Nhân Mã");
            }
            if (ngay3 >= 22 && ngay3 <= 31 && thang3 == 12) {
                txtCungGirl.setText("Ma Kết");
            }

            if (ngay3 >= 1 && ngay3 <= 19 && thang3 == 1) {
                txtCungGirl.setText("Ma Kết");
            }

            if (ngay3 >= 20 && ngay3 <= 31 && thang3 == 1) {
                txtCungGirl.setText("Bảo Bình");
            }

            if (ngay3 >= 1 && ngay3 <= 18 && thang3 == 2) {
                txtCungGirl.setText("Bảo Bình");
            }

            if (ngay3 >= 19 && ngay3 <= 31 && thang3 == 2) {
                txtCungGirl.setText("Song Ngư");
            }

            if (ngay3 >= 1 && ngay3 <= 20 && thang3 == 3) {
                txtCungGirl.setText("Song Ngư");
            }

        }

    }
    public void requestRead() {
        SharedPreferences sharedPreferences1=this.getSharedPreferences("aa",MODE_PRIVATE);
        SharedPreferences sharedPreferences = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        uribackground = sharedPreferences1.getString("xx", "");
        uriBoy = sharedPreferences.getString("uriBoy", "");
        uriGirl = sharedPreferences.getString("uriGirl", "");
        Log.e("Tin", uribackground);
        if (ContextCompat.checkSelfPermission(HinhNenService.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
           try{
               if (uribackground.equals("")) {
                   aa.setImageResource(R.drawable.background);
               } else {
                   aa.setImageURI(Uri.parse(Uri.decode(uribackground)));
               }
               if (uriBoy.equals("")) {

                   imgBoy.setImageResource(R.drawable.background);
               } else {

                   imgBoy.setImageURI(Uri.parse(Uri.decode(uriBoy)));
               }

               if (uriGirl.equals("")) {

                   imgGirl.setImageResource(R.drawable.background);
               } else {

                   imgGirl.setImageURI(Uri.parse(Uri.decode(uriGirl)));
               }

           }catch (Exception e){

           }

        }
    }



}
