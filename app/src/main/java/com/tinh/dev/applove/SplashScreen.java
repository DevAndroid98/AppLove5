package com.tinh.dev.applove;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tinh.dev.applove.DataBase.DataBase;

public class SplashScreen extends Base {
    private TextView textView;
    private ImageView imageView;
    private TextView txtDev,txtYeaar;
    private ProgressBar pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.txt_app_name);
        imageView = findViewById(R.id.img_splashscreen);
        txtDev = findViewById(R.id.dev);
        txtYeaar = findViewById(R.id.year);
        pro=findViewById(R.id.pro);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim4);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.anim5);
        typeface = Typeface.createFromAsset(getAssets(), "love_girl.ttf");
        typeface1 = Typeface.createFromAsset(getAssets(), "font_1.ttf");
        textView.setTypeface(typeface);
        txtYeaar.setTypeface(typeface1);
        txtDev.setTypeface(typeface1);
        imageView.setAnimation(animation);
        dataBase=new DataBase(this);
        RunAble runAble=new RunAble(1,this);
        new Thread(runAble).start();
        RunAble1 runAble1=new RunAble1(1,this);
        new Thread(runAble1).start();

    }


    public class RunAble implements Runnable {
        int seconds;
        Context context;

        public RunAble(int seconds, Context context) {
            this.seconds = seconds;
            this.context = context;
        }

        @Override
        public void run() {
            for (int i = 0; i <=2; i++) {
                Handler handler = new Handler(Looper.getMainLooper());
                final int intI = i;
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                       if (intI==2){
                           textView.setVisibility(View.VISIBLE);
                           txtYeaar.setVisibility(View.VISIBLE);
                           textView.setAnimation(animation1);
                           txtYeaar.setAnimation(animation1);

                       }
                    }

                });

                try {
                    Thread.sleep(450);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public class RunAble1 implements Runnable {
        int seconds;
        Context context;

        public RunAble1(int seconds, Context context) {
            this.seconds = seconds;
            this.context = context;
        }

        @Override
        public void run() {
            for (int i = 0; i <=4; i++) {
                Handler handler = new Handler(Looper.getMainLooper());
                final int intI = i;
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        if (intI==4){
                            txtDev.setVisibility(View.VISIBLE);
                            txtDev.setAnimation(animation1);
                            pro.setVisibility(View.VISIBLE);
                            startActivity();
                        }
                    }

                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public void startActivity(){
        Thread thread=new Thread(){

            @Override
            public void run() {
                try {
                    sleep(1400);
                }catch (Exception e){

                }finally {
                    Cursor cursor=dataBase.getTen();
                    if (cursor!=null&&cursor.moveToFirst()){
                        startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                        SplashScreen.this.finish();
                    }else {
                        startActivity(new Intent(SplashScreen.this,DataActivity.class));
                        SplashScreen.this.finish();
                    }

                }
            }
        };
        thread.start();
    }



}
