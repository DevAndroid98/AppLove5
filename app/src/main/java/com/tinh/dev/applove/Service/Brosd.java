package com.tinh.dev.applove.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.tinh.dev.applove.R;

public class Brosd extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer=MediaPlayer.create(context, R.raw.the_girl);
        Intent serviceIntent = new Intent(context, ExampleServicea.class);
        ContextCompat.startForegroundService(context, serviceIntent);
        RunAble runAble1 = new RunAble(5,context);
        new Thread(runAble1).start();
        Log.e("Autostart", "started");

    }
        class RunAble implements Runnable{
            int seconds;
            Context context;

            public RunAble(int seconds, Context context) {
                this.seconds = seconds;
                this.context = context;
            }

            @Override
            public void run() {
                for (int i=0;i<=24;i++){
                    final Handler handler=new Handler(Looper.getMainLooper());
                    final int intI=i;
                    handler.post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            Log.e("a",intI+"");
                            if (intI==24){
                                Toast.makeText(context, ""+intI, Toast.LENGTH_SHORT).show();
                                mediaPlayer.start();
                                Intent serviceIntent = new Intent(context, ExampleServicea.class);
                                ContextCompat.startForegroundService(context, serviceIntent);
                                RunAble  runAble1=new RunAble(5,context);
                                new Thread(runAble1).start();
                            }

                            }

                            });

                    try {
                        Thread.sleep(3600000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }



            }
        }


}
