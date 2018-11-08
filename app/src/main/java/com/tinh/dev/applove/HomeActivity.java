package com.tinh.dev.applove;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.BackgroundToForegroundTransformer;
import com.eftimoff.viewpagertransformers.CubeInTransformer;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.tinh.dev.applove.DataBase.DataBase;
import com.tinh.dev.applove.Service.Brosd;
import com.tinh.dev.applove.Service.ChatHeadService;
import com.tinh.dev.applove.Service.DongHoService;
import com.tinh.dev.applove.Service.MusicService;
import com.tinh.dev.applove.adapter.FragmentAdapter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.tinh.dev.applove.Base_Fragment.CODE_DRAW_OVER_OTHER_APP_PERMISSION;

public class HomeActivity extends Base implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPaer;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private int STORAGE_PERMISSION_CODE = 1;
    private NavigationView navigationView;
    private DataBase dataBase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean t = false;
    private String uribackground, uriGirl, uriBoy, hp;
    private DrawerLayout drawerLayout;
    public ImageView background;
    private ImageView img_Music;
    private Intent intent;
    private boolean test=false;
    private  String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.backgroundxx);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.draw);
        img_Music = findViewById(R.id.img_Music);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + this.getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {

        }
        intent = new Intent(getApplicationContext(), MusicService.class);
        if (test == false) {
            startService(intent);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mapped();
        setquyen();
        amnhac();
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("aa", MODE_PRIVATE);
        uri = sharedPreferences.getString("xx", "");
        SharedPreferences sharedPreferences1 = getSharedPreferences("Data", Context.MODE_PRIVATE);
        uribackground = sharedPreferences1.getString("uribackground", "");
        uriBoy = sharedPreferences1.getString("uriBoy", "");
        uriGirl = sharedPreferences1.getString("uriGirl", "");

        if (!uri.equals("")){
            try{
                background.setImageURI(Uri.parse(uri));
                Log.e("a",uri);
            }catch (Exception e){

            }
        }
    }

    private void mapped(){
        viewPaer=findViewById(R.id.viewPaer);
        background=findViewById(R.id.backgroundxx);
        fragmentPagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        viewPaer.setAdapter(fragmentPagerAdapter);
        viewPaer.setPageTransformer(true, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position < -1){

                    page.setAlpha(0);

                }
                else if (position <= 0){
                    page.setAlpha(1);
                    page.setTranslationX(0);
                    page.setScaleX(1);
                    page.setScaleY(1);

                }
                else if (position <= 1){
                    page.setTranslationX(-position*page.getWidth());
                    page.setAlpha(1-Math.abs(position));
                    page.setScaleX(1-Math.abs(position));
                    page.setScaleY(1-Math.abs(position));

                }
                else {

                    page.setAlpha(0);

                }
            }
        });

        }

    private void setquyen(){
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
            requestStoragePermission();
        }
    }



    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {





            } else {

            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.hinhnen:
                imgbackground();
                break;
            case R.id.thongbao:
                this.sendBroadcast(new Intent(HomeActivity.this, Brosd.class));
                break;
            case R.id.cuasonoi:
                Intent intenta = new Intent(this, ChatHeadService.class);
                intent.putExtra("a", uribackground);
                intent.putExtra("b", uriBoy);
                intent.putExtra("c", uriGirl);
                startService(intenta);
                break;
            case R.id.dongho:
                startService(new Intent(this, DongHoService.class));
                break;
            case R.id.amnhac:
                img_Music.setVisibility(View.VISIBLE);
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thoát");
                builder.setMessage("Bạn có muốn thoát không?");
                builder.setIcon(R.drawable.logo);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

    public void imgbackground() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_BACKGROUND);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_BACKGROUND && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            background.setImageURI(uri);
            editor=sharedPreferences.edit();
            editor.putString("xx",getRealPathFromURI(uri));
            editor.commit();
        }

        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {

            } else {
                Toast.makeText(this,
                        "Cửa sổ sẽ không hoạt động được",
                        Toast.LENGTH_LONG).show();
                return;
            }


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void amnhac(){
        final RunAble1 runAble1=new RunAble1(5,this);
        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim2);
        img_Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test==false){
                    img_Music.setImageResource(R.drawable.ic_vol_of);
                    img_Music.setAnimation(animation);
                    stopService(intent);
                    new Thread(runAble1).start();
                    test=true;
                }else{
                    img_Music.setImageResource(R.drawable.ic_vol_on);
                    img_Music.setAnimation(animation);
                    startService(intent);
                    new Thread(runAble1).start();
                    test=false;
                }
            }
        });
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
            for (int i = 0; i <=2; i++) {
                Handler handler = new Handler(Looper.getMainLooper());
                final int intI = i;
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        if (intI==2){
                            img_Music.setVisibility(View.GONE);
                        }
                    }

                });

                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(intent);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
