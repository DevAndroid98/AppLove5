package com.tinh.dev.applove;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tinh.dev.applove.DataBase.BaseNgaySinhNhat;
import com.tinh.dev.applove.DataBase.DataBase;
import com.tinh.dev.applove.adapter.NhatKiAdapter;
import com.tinh.dev.applove.model.NhatKiMolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Diary_Fragment extends Base_Fragment {
    private RecyclerView recyclerview1;
    private FloatingActionButton floatbutton;
    private ImageView imgAnh;
    private NhatKiAdapter nhatKiAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<NhatKiMolder> arrayList;
    private ImageView imageButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.diaryfrangment_layout,container,false);
        recyclerview1 = view.findViewById(R.id.recyclerview_1);
        floatbutton =  view.findViewById(R.id.floatbutton);
        baseNgaySinhNhat=new BaseNgaySinhNhat(getActivity());
        dataBase=new DataBase(getActivity());
        sharedPreferences=getActivity().getSharedPreferences("tt", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        loinhan =  view.findViewById(R.id.loinhan);
        loinhan1 = view.findViewById(R.id.loinhan1);
        loinhan2 = view.findViewById(R.id.loinhan2);
        loinhan3 = view.findViewById(R.id.loinhan3);
        imageButton=view.findViewById(R.id.chuyendoi);
        anhxa();
        addRecyclerview();
        final String ab=sharedPreferences.getString("traodoi","");
        Cursor cursor = dataBase.getTen();

        if (cursor.moveToNext()) {
            String nameboy = cursor.getString(1);
            String namegirl = cursor.getString(2);
            if (ab.equals("")){
                loinhan.setText("\tGửi\t"+nameboy+",");
                loinhan2.setText("Mãi yêu,"+namegirl);

            }else {
                loinhan.setText("\tGửi\t"+namegirl+",");
                loinhan2.setText("Mãi yêu,"+nameboy);
                }
        }
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cursor cursor = dataBase.getTen();
                editor=sharedPreferences.edit();
                String a=sharedPreferences.getString("traodoi","");
                if (cursor.moveToNext()) {
                    String nameboy = cursor.getString(1);
                    String namegirl = cursor.getString(2);
                    if (a.equals("")){
                        loinhan.setText("\tGửi\t"+namegirl+",");
                        loinhan2.setText("Mãi yêu,"+nameboy);
                        editor.putString("traodoi","q");
                        editor.commit();
                    }else {
                        loinhan.setText("\tGửi\t"+nameboy+",");
                        loinhan2.setText("Mãi yêu,"+namegirl);
                        editor.putString("traodoi","");
                        editor.commit();
                    }
                }

            }
        });
        String gioithieu=sharedPreferences.getString("gioithieu","");
        if (gioithieu.equals("")){
            gioithieu();
        }
        floatbutton.setImageResource(R.drawable.add);
        floatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"love_girl.ttf");
        loinhan.setTypeface(typeface);
        loinhan1.setTypeface(typeface);
        loinhan2.setTypeface(typeface);
        loinhan3.setTypeface(typeface);
        RunAble runAble1 = new RunAble(1);
        new Thread(runAble1).start();
        return view;
    }
 public void anhxa(){
        arrayList=new ArrayList<>();
        nhatKiAdapter=new NhatKiAdapter(arrayList,Diary_Fragment.this);
        linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
     Cursor cursor=baseNgaySinhNhat.getLink();
     arrayList.clear();
     if (cursor!=null&&cursor.moveToFirst()){
         do {
             long ngay=cursor.getLong(1);
             String nhatki=cursor.getString(2);
             String links=cursor.getString(3);
             int id=cursor.getInt(0);
             arrayList.add(new NhatKiMolder(ngay,nhatki,links,id));

         }while (cursor.moveToNext());
         nhatKiAdapter.notifyDataSetChanged();
     }

 }
 public void addRecyclerview(){
        recyclerview1.setLayoutManager(linearLayoutManager);
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setAdapter(nhatKiAdapter);
 }
 public void add(){
     final Dialog dialog=new Dialog(getActivity());
     dialog.setContentView(R.layout.dialog_nhatkhi);
      final EditText edtdate;
      ImageView btnPicker;
      final EditText edtNhatKi;
      final Button click;
      Button them;
      Button thoat;




     edtdate = dialog.findViewById(R.id.edtdate);
     btnPicker =  dialog.findViewById(R.id.btnPicker);
     edtNhatKi = dialog.findViewById(R.id.edtNhatKi);
     click = dialog.findViewById(R.id.click);
     imgAnh = dialog.findViewById(R.id.img_anh);
     them = dialog.findViewById(R.id.them);
     thoat = dialog.findViewById(R.id.thoat);

     btnPicker.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             chonngay(edtdate,1,null);
         }
     });
     click.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           imgbackground();
         }
     });


     them.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String links=sharedPreferences.getString("uri","");
            String ngay=edtdate.getText().toString().trim();
            String nhatki=edtNhatKi.getText().toString().trim();
            if (ngay.equals("")){
                edtdate.setError(getString(R.string.error));
                return;
            }
            if (nhatki.equals("")){
        edtNhatKi.setError(getString(R.string.error));
        return;
    }
            if (nhatki.length()>80){
        edtNhatKi.setError(getString(R.string.error1));
        return;
    }
            if (links.equals("")){
        click.setError(getString(R.string.error));
        return;
    }

            baseNgaySinhNhat.insertNhatKi(calendarOne.getTimeInMillis(),nhatki,links);
            editor.putString("uri","");
            editor.commit();
    anhxa();
    addRecyclerview();
            dialog.dismiss();

}
     });






     thoat.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             dialog.cancel();
         }
     });
     dialog.show();

 }

    private void chonngay(final TextView textViewx,int i, NhatKiMolder nhatKiMolde) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialogpicker);
        final CalendarView simpleCalendarView =dialog.findViewById(R.id.simpleCalendarView);
        calendarOne= Calendar.getInstance();

        simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        if (i==1){
            simpleCalendarView.setDate(calendarOne.getTimeInMillis());
        }
        if (i==2){
            simpleCalendarView.setDate(nhatKiMolde.getNgay());
        }
        final TextView textView=dialog.findViewById(R.id.text);
        simpleCalendarView.setMaxDate(calendarOne.getTimeInMillis());
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                calendarOne.set(year,month,dayOfMonth);
                Log.e("Tinh",calendarOne.getTimeInMillis()+"");
                textViewx.setText(simpleDateFormat.format(calendarOne.getTimeInMillis())+"");
                textView.setText("Thời gian:"+simpleDateFormat.format(calendarOne.getTimeInMillis())+"");

                textViewx.setEnabled(false);
            }
        });
        Button textView1=dialog.findViewById(R.id.ok);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void imgbackground() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_BACKGROUND);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_BACKGROUND && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            imgAnh.setImageURI(uri);
            editor.putString("uri", getRealPathFromURI(uri
            ));
            editor.commit();
            }
    }

    public void update(final int i, final int a, final NhatKiMolder nhatKiMolder){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_nhatkhi);
        final EditText edtdate;
        ImageView btnPicker;
        final EditText edtNhatKi;
        final Button click;
        Button them;
        Button thoat;
        Button xoa;
        xoa = dialog.findViewById(R.id.xoa);
        xoa.setVisibility(View.VISIBLE);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseNgaySinhNhat.delete(i);
                arrayList.remove(a);
                anhxa();
                addRecyclerview();
                editor.putString("uri","");
                editor.commit();
                dialog.dismiss();

            }
        });

         calendarOne=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        edtdate = dialog.findViewById(R.id.edtdate);
        btnPicker =  dialog.findViewById(R.id.btnPicker);
        edtNhatKi = dialog.findViewById(R.id.edtNhatKi);
        click = dialog.findViewById(R.id.click);
        imgAnh = dialog.findViewById(R.id.img_anh);
        them = dialog.findViewById(R.id.them);
        thoat = dialog.findViewById(R.id.thoat);
        edtdate.setText(simpleDateFormat.format(nhatKiMolder.getNgay())+"");
        edtdate.setEnabled(false);
        edtNhatKi.setText(nhatKiMolder.getNhatki());
        imgAnh.setImageURI(Uri.parse(Uri.decode(nhatKiMolder.getLinks())));
        calendarOne.setTimeInMillis(nhatKiMolder.getNgay());
        editor.putString("uri",nhatKiMolder.getLinks());
        editor.commit();
        them.setText("Sửa");
        btnPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonngay(edtdate,2,nhatKiMolder);
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbackground();
            }
        });


        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String links=sharedPreferences.getString("uri","");
                String ngay=edtdate.getText().toString().trim();
                String nhatki=edtNhatKi.getText().toString().trim();
                if (ngay.equals("")){
                    edtdate.setError(getString(R.string.error));
                    return;
                }
                if (nhatki.equals("")){
                    edtNhatKi.setError(getString(R.string.error));
                    return;
                }
                if (nhatki.length()>80){
                    edtNhatKi.setError(getString(R.string.error1));
                    return;
                }
                if (links.equals("")){
                    click.setError(getString(R.string.error));
                    return;
                }

                baseNgaySinhNhat.updateNhatKi(calendarOne.getTimeInMillis(),nhatki,links,i);
                editor.putString("uri","");
                editor.commit();
                anhxa();
                addRecyclerview();
                dialog.dismiss();

            }
        });






        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void gioithieu(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Giới thiệu");
        builder.setMessage("-Ứng dụng:Đếm ngày yêu"+"\n"+"-Phiên bản:1.8.0"+"\n"+"-Hoàn thành:31-10-2018"+"\n"+"Design:NguyenTinh Developer");
        builder.setPositiveButton("OKI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor=sharedPreferences.edit();
                editor.putString("gioithieu","123");
                editor.commit();
            }
        });
        builder.show();
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
                            int datelove = 0;
                            String strDateFormat24 = "HH";
                            String strDateFormat25 = "mm";
                            String strDateFormat26 = "ss";
                            SimpleDateFormat sdf = null;
                            SimpleDateFormat sdf1 = null;
                            SimpleDateFormat sdf2 = null;
                            Cursor cursor = dataBase.getSoNgayYeu();
                            if (cursor.moveToNext()) {
                                datelove= cursor.getInt(1);

                            }
                            sdf = new SimpleDateFormat(strDateFormat24);
                            sdf1 = new SimpleDateFormat(strDateFormat25);
                            sdf2 = new SimpleDateFormat(strDateFormat26);
                            double so = datelove;
                            double so1 = 365;

                            double nam1 = so / so1;
                            int nguyen = (int) nam1;
                            //txtnam.setText(nguyen + "");
                            double sodu = so - (nguyen * so1);

                            double thang1 = sodu / 30;
                            int thangnguyen = (int) thang1;
                            //txtthang.setText(thangnguyen + "");

                            double sodungay = sodu - (thangnguyen * 30);
                            int sodungay1 = (int) sodungay;
                            //txtngay.setText(sodungay1 + "");
                            if (nguyen==0){
                                if (thangnguyen==0){
                                    if (sodungay1>0){
                                        loinhan1.setText("\tHạnh phúc quá!Chúng mình đã yêu nhau được\n"+"\t"+sodungay1+"\tngày,"+sdf.format(date)+"\tgiờ,"+sdf1.format(date)+"\tphút,"+sdf2.format(date)+"\tgiây.\n\tRồi đấy...Luôn bên nhau nhé");
                                    }
                                }else if (thangnguyen>0){
                                    loinhan1.setText("\tHạnh phúc quá!Chúng mình đã yêu nhau được\n"+"\t"+thangnguyen+"\ttháng,"+sodungay1+"\tngày,"+sdf.format(date)+"\tgiờ,"+sdf1.format(date)+"\tphút,"+sdf2.format(date)+"\tgiây.\n\tRồi đấy...Luôn bên nhau nhé");
                                }
                            }else if (nguyen>0){
                                loinhan1.setText("\tHạnh phúc quá!Chúng mình đã yêu nhau được\n"+"\t"+nguyen+"\tnăm,"+thangnguyen+"\ttháng,"+sodungay1+"\tngày,"+sdf.format(date)+"\tgiờ,"+sdf1.format(date)+"\tphút,"+sdf2.format(date)+"\tgiây.\n\tRồi đấy...Luôn bên nhau nhé");
                            }


                            RunAble runAble1=new RunAble(1);
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

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
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
