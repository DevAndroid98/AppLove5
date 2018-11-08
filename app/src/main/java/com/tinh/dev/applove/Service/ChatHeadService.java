package com.tinh.dev.applove.Service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tinh.dev.applove.DataBase.DataBase;
import com.tinh.dev.applove.R;


public class ChatHeadService extends Service {

    private WindowManager mWindowManager;
    private View mChatHeadView;
    private DataBase dataBase;
    private WindowManager.LayoutParams params;


    public ChatHeadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        dataBase=new DataBase(this);
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim1);
        mChatHeadView.setAnimation(animation);

        int api=Integer.valueOf(android.os.Build.VERSION.SDK);
        Log.e("Tinh",api+"");
        if (api>=26) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }else

    {
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
    }


        //Specify the chat head position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
         mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
         mWindowManager.addView(mChatHeadView, params);


         //Set the close button.
        ImageView closeButton = (ImageView) mChatHeadView.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the service and remove the chat head from the window
               stopSelf();
            }
        });

        //Drag and move chat head using user's touch action.
        final ImageView chatHeadImage = (ImageView) mChatHeadView.findViewById(R.id.chat_head_profile_iv);
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
                            //Open the chat conversation click.
                            Intent intent=new Intent(ChatHeadService.this, HinhNenService.class);
                            Cursor cursor=dataBase.getSoNgayYeu();
                            Cursor cursor1=dataBase.getTen();
                            if (cursor.moveToNext()&&cursor1.moveToNext()){
                                String nameBoy=cursor1.getString(1);
                                String nameGirl=cursor1.getString(2);
                                int i=cursor.getInt(1);
                                intent.putExtra("SoNgayYeu",i);
                                intent.putExtra("nameBoy",nameBoy);
                                intent.putExtra("nameGirl",nameGirl);
                                startService(intent);
                                stopSelf();
                            }

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


        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }
}
