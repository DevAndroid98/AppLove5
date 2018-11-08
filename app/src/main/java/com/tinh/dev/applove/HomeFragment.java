package com.tinh.dev.applove;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.tinh.dev.applove.DataBase.BaseNgaySinhNhat;
import com.tinh.dev.applove.DataBase.DataBase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.itangqi.waveloadingview.WaveLoadingView;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Base_Fragment {
    private TextView txtAppName;
    private View view;
    private WaveLoadingView loaddingWave;
    private LinearLayout linearLayout;
    private CircularImageView imgBoy;
    private ProgressBar pro1;
    private ImageView love;
    private TextView txtHanhPhuc;
    private CircularImageView imgGirl;
    private ProgressBar pro2;
    private TextView txtNameBoy;
    private TextView txtNameGirl;
    private RelativeLayout card1;
    private TextView txtAgeBoy;
    private RelativeLayout card2;
    private RelativeLayout card3;
    private TextView txtAgeGirl;
    private RelativeLayout card4;
    private TextView cung1;
    private TextView cung2;
    private TextView txtTopwawe;
    private TextView txtCenterwawe;
    private TextView txtBottomwawe;
    private Animation anim;
    private Dialog dialog;
    private int ngay;
    private  long tong;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefragment_layout, container, false);
        anhXa();
        font_text();
        onClick();
        requestRead();
        getDataName();
        getDataDate();
        updateDate();
        love.setAnimation(anim);
        int i = sharedPreferences.getInt("i", 0);
        lovex(i);
        cungHoangDao(cung1, 1);
        cungHoangDao(cung2, 2);
        int l = sharedPreferences.getInt("2", 0);
        if (l != 0) {
            getcolor(l, card2);
        }
        int x = sharedPreferences.getInt("4", 0);
        if (x != 0) {
            getcolor(x, card4);
        }
        int font = sharedPreferences.getInt("font", 0);
        xetfont(font, cung1);
        int font1 = sharedPreferences.getInt("font1", 0);
        xetfont(font1, cung2);

        if (font == 9 || font == 10) {
            cung1.setTextSize(13);
        } else {
            cung1.setTextSize(15.5f);
        }
        if (font1 == 9 || font1 == 10) {
            cung2.setTextSize(13);
        } else {
            cung2.setTextSize(15.5f);
        }

        int text = sharedPreferences.getInt("text", 0);
        Log.e("color", text + "");
        if (text != 0) {
            getColorText(cung1, text);
        }
        int text1 = sharedPreferences.getInt("text1", 0);
        if (text1 != 0) {
            getColorText(cung2, text1);
        }

        int c = sharedPreferences.getInt("card1", 0);
        if (c != 0) {
            getcolor(c, card1);
        }

        int c1 = sharedPreferences.getInt("card2", 0);
        if (c1 != 0) {
            getcolor(c1, card3);
        }
        String top=sharedPreferences.getString("top","");
        if (!top.equals("")){
            txtTopwawe.setText(top);
        }

        return view;
    }


    private void anhXa() {
        txtAppName = view.findViewById(R.id.txt_app_name);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "love_girl.ttf");
        txtAppName = view.findViewById(R.id.txt_app_name);
        loaddingWave = view.findViewById(R.id.loaddingWave);
        linearLayout = view.findViewById(R.id.linearLayout);
        imgBoy = view.findViewById(R.id.imgBoy);
        pro1 = view.findViewById(R.id.pro1);
        love = view.findViewById(R.id.love);
        txtHanhPhuc = view.findViewById(R.id.txtHanhPhuc);
        imgGirl = view.findViewById(R.id.imgGirl);
        pro2 = view.findViewById(R.id.pro2);

        txtNameBoy = view.findViewById(R.id.txtNameBoy);
        txtNameGirl = view.findViewById(R.id.txtNameGirl);
        card1 = view.findViewById(R.id.card_1);
        txtAgeBoy = view.findViewById(R.id.txtAgeBoy);
        card2 = view.findViewById(R.id.card_2);
        card3 = view.findViewById(R.id.card_3);
        txtAgeGirl = view.findViewById(R.id.txtAgeGirl);
        card4 = view.findViewById(R.id.card_4);
        cung1 = view.findViewById(R.id.cung1);
        cung2 = view.findViewById(R.id.cung2);
        txtTopwawe = view.findViewById(R.id.txtTopwawe);
        txtCenterwawe = view.findViewById(R.id.txtCenterwawe);
        txtBottomwawe = view.findViewById(R.id.txtBottomwawe);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim9);
        sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dataBase = new DataBase(getActivity());
        baseNgaySinhNhat = new BaseNgaySinhNhat(getActivity());
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        font_1 = Typeface.createFromAsset(getActivity().getAssets(), "font_1.ttf");
        font_2 = Typeface.createFromAsset(getActivity().getAssets(), "font_2.ttf");
        font_3 = Typeface.createFromAsset(getActivity().getAssets(), "font_3.ttf");
        font_4 = Typeface.createFromAsset(getActivity().getAssets(), "font_4.TTF");
        font_5 = Typeface.createFromAsset(getActivity().getAssets(), "font_5.TTF");
        font_6 = Typeface.createFromAsset(getActivity().getAssets(), "font_6.ttf");
        font_7 = Typeface.createFromAsset(getActivity().getAssets(), "font_7.ttf");
        font_8 = Typeface.createFromAsset(getActivity().getAssets(), "font_8.otf");
        font_9 = Typeface.createFromAsset(getActivity().getAssets(), "font_9.ttf");
        font_10 = Typeface.createFromAsset(getActivity().getAssets(), "font_holidays.ttf");
        font_11 = Typeface.createFromAsset(getActivity().getAssets(), "font_11.ttf");

    }

    private void font_text() {
        txtAppName.setTypeface(typeface);
        txtHanhPhuc.setTypeface(typeface);
        txtNameGirl.setTypeface(typeface);
        txtNameBoy.setTypeface(typeface);
        cung1.setTypeface(typeface);
        cung2.setTypeface(typeface);
        txtTopwawe.setTypeface(typeface);
        txtCenterwawe.setTypeface(font_1);
        txtBottomwawe.setTypeface(typeface);

    }

    private void onClick() {
        imgBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBoy();
            }
        });
        imgGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgGirl();
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popLove();
                }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAge(card2, "(Nam)", "a", 1, cung1, "2", 80, "font", "text");
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAge(card4, "(Nữ)", "b", 2, cung2, "4", 80, "font1", "text1");
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color1(card1, "card1", 90);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color1(card3, "card2", 90);
            }
        });

       loaddingWave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               popwawe();
           }
       });

       txtNameBoy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               updateTen(1);
           }
       });

        txtNameGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTen(2);
            }
        });
    }

    public void imgBoy() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_FOLDER);
    }


    public void imgGirl() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_FOLDER1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uriBoy = data.getData();
            imgBoy.setImageURI(uriBoy);
            pro1.setVisibility(View.INVISIBLE);
            editor.putString("uriBoy", getRealPathFromURI(uriBoy));
            editor.commit();
        }

        if (requestCode == REQUEST_CODE_FOLDER1 && resultCode == RESULT_OK && data != null) {
            Uri uriGirl = data.getData();
            imgGirl.setImageURI(uriGirl);
            pro2.setVisibility(View.INVISIBLE);
            editor.putString("uriGirl", getRealPathFromURI(uriGirl));
            editor.commit();
        }
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (Settings.canDrawOverlays(getActivity())) {
                ////settings.setVisibility(View.GONE);
            } else {
                Toast.makeText(getActivity(),
                        "Cửa sổ sẽ không hoạt động được",
                        Toast.LENGTH_LONG).show();
            }


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    public void requestRead() {

        uriBoy = sharedPreferences.getString("uriBoy", "");
        uriGirl = sharedPreferences.getString("uriGirl", "");
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            try {

                if (uriBoy.equals("")) {
                    pro1.setVisibility(View.VISIBLE);
                    imgBoy.setImageResource(R.drawable.background);
                } else {

                    pro1.setVisibility(View.INVISIBLE);
                    imgBoy.setImageURI(Uri.parse(uriBoy));
                }

                if (uriGirl.equals("")) {
                    pro2.setVisibility(View.VISIBLE);
                    imgGirl.setImageResource(R.drawable.background);
                } else {

                    pro2.setVisibility(View.INVISIBLE);
                    imgGirl.setImageURI(Uri.parse(uriGirl));
                }
            } catch (Exception e) {

            }


        }

    }

    private void color(final RelativeLayout linearLayout, final String data, final int radius) {

        changeBackgroundColor(currentBackgroundColor);
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle("Chọn màu")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {

                    }
                })
                .setPositiveButton("Oki", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] integers) {
                        editor = sharedPreferences.edit();
                        editor.putInt(data, i);
                        GradientDrawable shape = new GradientDrawable();
                        shape.setCornerRadius(radius);
                        shape.setColor(i);
                        linearLayout.setBackground(shape);
                        editor.commit();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).showColorEdit(true)
                .setColorEditTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_bright))
                .build()
                .show();
    }


    private void color1(final RelativeLayout linearLayout, final String data, final int radius) {

        changeBackgroundColor(currentBackgroundColor);
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle("Chọn màu")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {

                    }
                })
                .setPositiveButton("Oki", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] integers) {
                        editor = sharedPreferences.edit();
                        editor.putInt(data, i);
                        GradientDrawable shape = new GradientDrawable();
                        shape.setCornerRadius(radius);
                        shape.setColor(i);
                        linearLayout.setBackground(shape);
                        editor.commit();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).showColorEdit(true)
                .setColorEditTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_bright))
                .build()
                .show();
    }

    private void colorText(final TextView textView, final String data) {

        changeBackgroundColor(currentBackgroundColor);
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle("Chọn màu")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {

                    }
                })
                .setPositiveButton("Oki", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] integers) {
                        editor = sharedPreferences.edit();
                        editor.putInt(data, i);
                        textView.setTextColor(i);
                        Log.e("xxx", i + "");
                        editor.commit();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).showColorEdit(true)
                .setColorEditTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_bright))
                .build()
                .show();
    }

    public void getcolor(int i, RelativeLayout linearLayout) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(80);
        shape.setColor(i);
        linearLayout.setBackground(shape);
    }

    private void changeBackgroundColor(int selectedColor) {
        currentBackgroundColor = selectedColor;

    }

    private void xetfont(int i, TextView txt) {
        if (i == 0) {
            txt.setTypeface(typeface);
        }
        if (i == 1) {
            txt.setTypeface(font_1);
        }
        if (i == 2) {
            txt.setTypeface(font_2);
        }
        if (i == 3) {
            txt.setTypeface(font_3);
        }
        if (i == 4) {
            txt.setTypeface(font_4);
        }
        if (i == 5) {
            txt.setTypeface(font_5);
        }
        if (i == 6) {
            txt.setTypeface(font_6);
        }
        if (i == 7) {
            txt.setTypeface(font_7);
        }
        if (i == 8) {
            txt.setTypeface(font_8);
        }
        if (i == 9) {
            txt.setTypeface(font_9);
        }
        if (i == 10) {
            txt.setTypeface(font_10);
        }
        if (i == 11) {
            txt.setTypeface(font_11);
        }
    }

    private void getDataName() {
        Cursor cursor = dataBase.getTen();
        if (cursor.moveToNext()) {
            String nameboy = cursor.getString(1);
            String namegirl = cursor.getString(2);
            txtNameBoy.setText(nameboy);
            txtNameGirl.setText(namegirl);
        }
    }

    private void getColorText(TextView textView, int i) {

        textView.setTextColor(i);

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

    public void lovex(int i) {

        if (i == 0) {
            love.setImageResource(R.drawable.love7);
        }
        if (i == 1) {
            love.setImageResource(R.drawable.love1);
        }
        if (i == 2) {
            love.setImageResource(R.drawable.love2);
        }
        if (i == 3) {
            love.setImageResource(R.drawable.love3);
        }
        if (i == 4) {
            love.setImageResource(R.drawable.love4);
        }
        if (i == 5) {
            love.setImageResource(R.drawable.love5);
        }
        if (i == 6) {
            love.setImageResource(R.drawable.love6);
        }
    }

    public void popLove() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), love);
        popupMenu.getMenuInflater().inflate(R.menu.love_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                editor = sharedPreferences.edit();
                switch (item.getItemId()) {
                    case R.id.lvhong:
                        love.setImageResource(R.drawable.love7);
                        editor.putInt("i", 0);
                        editor.commit();
                        break;

                    case R.id.lvvang:
                        love.setImageResource(R.drawable.love1);
                        editor.putInt("i", 1);
                        editor.commit();
                        break;

                    case R.id.lvdo:
                        love.setImageResource(R.drawable.love2);
                        editor.putInt("i", 2);
                        editor.commit();
                        break;

                    case R.id.lvdo1:
                        love.setImageResource(R.drawable.love4);
                        editor.putInt("i", 4);
                        editor.commit();
                        break;

                    case R.id.lvtim:
                        love.setImageResource(R.drawable.love3);
                        editor.putInt("i", 3);
                        editor.commit();
                        break;

                    case R.id.lvxanh:
                        love.setImageResource(R.drawable.love5);
                        editor.putInt("i", 5);
                        editor.commit();
                        break;

                    case R.id.lvxanh1:
                        love.setImageResource(R.drawable.love6);
                        editor.putInt("i", 6);
                        editor.commit();
                        break;
                }


                return true;
            }
        });
        popupMenu.show();

    }

    public void popAge(final RelativeLayout linearLayout, final String male, final String a, final int i, final TextView textView, final String data, final int radius, final String font, final String text) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), linearLayout);
        popupMenu.getMenuInflater().inflate(R.menu.popup_age_color, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.age:
                        birthday(male, a, textView, i);
                        break;
                    case R.id.colorbackground:
                        color(linearLayout, data, radius);
                        break;
                    case R.id.font:
                        dialogfont(textView, font);
                        break;
                    case R.id.colortext:
                        colorText(textView, text);
                        break;
                }

                return true;
            }
        });

        popupMenu.show();

    }

    private void birthday(String male, String a, final TextView textView, final int i) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_age);
        dialog.getActionBar();
        dialog.setCancelable(false);
        dialog.setTitle("Thêm");
        Button chonngay1 = dialog.findViewById(R.id.chonngay1);
        TextView txtLogo = dialog.findViewById(R.id.txtLogo);

        final EditText ngay1;
        Button luu = dialog.findViewById(R.id.luu);
        Button huy = dialog.findViewById(R.id.huybo);
        ngay1 = dialog.findViewById(R.id.ngaysinh1);

        ngay1.setEnabled(false);


        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim3);
        chonngay1.setAnimation(animation);
        ngay1.setAnimation(animation);

        luu.setAnimation(animation);
        huy.setAnimation(animation);

        Cursor b = null;

        if (a.equals("a")) {
            b = baseNgaySinhNhat.getNam();
            if (b.moveToNext()) {
                int ngay = b.getInt(1);
                int thang = b.getInt(2);
                int nam = b.getInt(3);
                ngay1.setText(ngay + "-" + thang + "-" + nam + male);
                txtLogo.setText(getString(R.string.birthday));
            }
            chonngay1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar3 = Calendar.getInstance();
                    int ngay = calendar3.get(Calendar.DATE);
                    int thang = calendar3.get(Calendar.MONTH);
                    int nam = calendar3.get(Calendar.YEAR);
                    DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    calendar3.set(i, i1, i2);
                                    Cursor a = baseNgaySinhNhat.getNam();
                                    if (!a.moveToNext()) {
                                        baseNgaySinhNhat.insertNgaySinhNhatNam(i2, i1 + 1, i);
                                    } else {
                                        baseNgaySinhNhat.updateNgaySinhNhatNam(i2, i1 + 1, i, 1);
                                    }
                                    ngay1.setText(simpleDateFormat.format(calendar3.getTime()));

                                }
                            }, nam, thang, ngay);

                    pickerDialog.show();
                }
            });
        }

        if (a.equals("b")) {
            b = baseNgaySinhNhat.getNu();
            if (b.moveToNext()) {
                int ngay = b.getInt(1);
                int thang = b.getInt(2);
                int nam = b.getInt(3);
                ngay1.setText(ngay + "-" + thang + "-" + nam + male);
                txtLogo.setText(getString(R.string.birthday));
            }
            chonngay1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar3 = Calendar.getInstance();
                    int ngay = calendar3.get(Calendar.DATE);
                    int thang = calendar3.get(Calendar.MONTH);
                    int nam = calendar3.get(Calendar.YEAR);
                    DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    calendar3.set(i, i1, i2);
                                    Cursor a = baseNgaySinhNhat.getNu();
                                    if (!a.moveToNext()) {
                                        baseNgaySinhNhat.insertNgaySinhNhatNu(i2, i1 + 1, i);
                                    } else {
                                        baseNgaySinhNhat.updateNgaySinhNhatNu(i2, i1 + 1, i, 1);
                                    }
                                    ngay1.setText(simpleDateFormat.format(calendar3.getTime()));

                                }
                            }, nam, thang, ngay);

                    pickerDialog.show();
                }
            });
        }


        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });


        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ngay1.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Chưa có gì để thêm", Toast.LENGTH_SHORT).show();
                } else {

                    cungHoangDao(textView, i);
                    dialog.dismiss();
                }

            }

        });

        dialog.show();


    }


    private void cungHoangDao(TextView textView, int i) {
        calendarOne = Calendar.getInstance();
        int namhientai = calendarOne.get(Calendar.YEAR);
        Cursor b = null;
        int ngay = 0;
        int thang = 0;
        int nam = 0;

        if (i == 1) {
            b = baseNgaySinhNhat.getNam();
            if (b != null && b.moveToNext()) {
                ngay = b.getInt(1);
                thang = b.getInt(2);
                nam = b.getInt(3);
                int tuoiNam = namhientai - nam;

                if (tuoiNam == 0) {
                    txtAgeBoy.setText("?");
                } else {
                    txtAgeBoy.setText(tuoiNam + "");
                }
            } else {
                return;
            }
        }
        if (i == 2) {
            b = baseNgaySinhNhat.getNu();
            if (b != null && b.moveToNext()) {
                ngay = b.getInt(1);
                thang = b.getInt(2);
                nam = b.getInt(3);
                int tuoiNu = namhientai - nam;
                if (tuoiNu == 0) {
                    txtAgeGirl.setText("?");
                } else {
                    txtAgeGirl.setText(tuoiNu + "");
                }
            } else {
                return;
            }
        }


        if (ngay >= 21 && ngay <= 31 && thang == 3) {
            textView.setText("Bạch Dương");
        }

        if (ngay >= 1 && ngay <= 20 && thang == 4) {
            textView.setText("Bạch Dương");
        }

        if (ngay >= 21 && ngay <= 31 && thang == 4) {
            textView.setText("Kim Ngưu");
        }

        if (ngay >= 1 && ngay <= 20 && thang == 5) {
            textView.setText("Kim Ngưu");
        }

        if (ngay >= 21 && ngay <= 31 && thang == 5) {
            textView.setText("Song Tử");
        }

        if (ngay >= 1 && ngay <= 21 && thang == 6) {
            textView.setText("Song Tử");
        }

        if (ngay >= 22 && ngay <= 31 && thang == 6) {
            textView.setText("Cự Giải");
        }

        if (ngay >= 1 && ngay <= 22 && thang == 7) {
            textView.setText("Cự Giải");
        }

        if (ngay >= 23 && ngay <= 31 && thang == 7) {
            textView.setText("Sư Tử");
        }

        if (ngay >= 1 && ngay <= 22 && thang == 8) {
            textView.setText("Sư Tử");
        }

        if (ngay >= 23 && ngay <= 31 && thang == 8) {
            textView.setText("Xử Nữ");
        }

        if (ngay >= 1 && ngay <= 22 && thang == 9) {
            textView.setText("Xử Nữ");
        }

        if (ngay >= 23 && ngay <= 31 && thang == 9) {
            textView.setText("Thiên Bình");
        }

        if (ngay >= 1 && ngay <= 23 && thang == 10) {
            textView.setText("Thiên Bình");
        }

        if (ngay >= 24 && ngay <= 31 && thang == 10) {
            textView.setText("Bọ Cạp");
        }

        if (ngay >= 1 && ngay <= 22 && thang == 11) {
            textView.setText("Bọ Cạp");
        }

        if (ngay >= 23 && ngay <= 31 && thang == 11) {
            textView.setText("Nhân Mã");
        }

        if (ngay >= 1 && ngay <= 21 && thang == 12) {
            textView.setText("Nhân Mã");
        }
        if (ngay >= 22 && ngay <= 31 && thang == 12) {
            textView.setText("Ma Kết");
        }

        if (ngay >= 1 && ngay <= 19 && thang == 1) {
            textView.setText("Ma Kết");
        }

        if (ngay >= 20 && ngay <= 31 && thang == 1) {
            textView.setText("Bảo Bình");
        }

        if (ngay >= 1 && ngay <= 18 && thang == 2) {
            textView.setText("Bảo Bình");
        }

        if (ngay >= 19 && ngay <= 31 && thang == 2) {
            textView.setText("Song Ngư");
        }

        if (ngay >= 1 && ngay <= 20 && thang == 3) {
            textView.setText("Song Ngư");
        }

    }

    public void font() {
        font0.setTypeface(typeface);
        fontA.setTypeface(font_1);
        fontB.setTypeface(font_2);
        fontC.setTypeface(font_3);
        fontD.setTypeface(font_4);
        fontE.setTypeface(font_5);
        fontF.setTypeface(font_6);
        fontG.setTypeface(font_7);
        fontH.setTypeface(font_8);
        fontI.setTypeface(font_9);
        fontK.setTypeface(font_10);
        fontL.setTypeface(font_11);

    }

    private void dialogfont(TextView textView, String font) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_card_font);
        anhxaDialog();
        font();
        clickfont(textView, font);
        dialog.show();
    }

    public void clickfont(final TextView textView, final String font) {
        view0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 0);
                textView.setTextSize(15f);
                textView.setTypeface(typeface);
                editor.commit();
                dialog.dismiss();
            }
        });
        viewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 1);
                textView.setTypeface(font_1);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 2);
                textView.setTypeface(font_2);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 3);
                textView.setTypeface(font_3);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 4);
                textView.setTextSize(15f);
                textView.setTypeface(font_4);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 5);
                textView.setTypeface(font_5);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 6);
                textView.setTypeface(font_6);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 7);
                textView.setTypeface(font_7);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 8);
                textView.setTypeface(font_8);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

        viewI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 9);
                textView.setTypeface(font_9);
                textView.setTextSize(13f);
                editor.commit();
                dialog.dismiss();
            }
        });
        viewK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 10);
                textView.setTypeface(font_10);
                textView.setTextSize(13f);
                editor.commit();
                dialog.dismiss();
            }
        });
        viewL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putInt(font, 11);
                textView.setTypeface(font_11);
                textView.setTextSize(15f);
                editor.commit();
                dialog.dismiss();
            }
        });

    }

    private void anhxaDialog() {
        viewA = dialog.findViewById(R.id.view_a);
        view0 = dialog.findViewById(R.id.view_0);
        fontA = dialog.findViewById(R.id.font_a);
        font0 = dialog.findViewById(R.id.font_0);
        viewB = dialog.findViewById(R.id.view_b);
        fontB = dialog.findViewById(R.id.font_b);
        viewC = dialog.findViewById(R.id.view_c);
        fontC = dialog.findViewById(R.id.font_c);
        viewD = dialog.findViewById(R.id.view_d);
        fontD = dialog.findViewById(R.id.font_d);
        viewE = dialog.findViewById(R.id.view_e);
        fontE = dialog.findViewById(R.id.font_e);
        viewF = dialog.findViewById(R.id.view_f);
        fontF = dialog.findViewById(R.id.font_f);
        viewG = dialog.findViewById(R.id.view_g);
        fontG = dialog.findViewById(R.id.font_g);
        viewH = dialog.findViewById(R.id.view_h);
        fontH = dialog.findViewById(R.id.font_h);
        viewI = dialog.findViewById(R.id.view_i);
        fontI = dialog.findViewById(R.id.font_i);
        viewK = dialog.findViewById(R.id.view_k);
        fontK = dialog.findViewById(R.id.font_k);
        viewL = dialog.findViewById(R.id.view_l);
        fontL = dialog.findViewById(R.id.font_l);
    }

    public void popwawe() {

        PopupMenu popupMenu = new PopupMenu(getActivity(), loaddingWave);
        popupMenu.getMenuInflater().inflate(R.menu.popup_name_font, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.name:
                        dialogname(txtTopwawe.getText().toString().trim());
                        break;
                    case R.id.doingayyeu:
                        dialogdate();
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }

    public void dialogname(final String name) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_name);
        final EditText txtName;
        Button thaydoi;

        txtName = dialog.findViewById(R.id.txtName);
        thaydoi = dialog.findViewById(R.id.thaydoi);
        txtName.setHint(name);

        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtName.getText().toString().trim().equals(name)) {
                    Toast.makeText(getActivity(), "Không thay đổi", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                if (txtName.getText().toString().trim().equals("")) {
                    txtName.setError(getString(R.string.error));
                    return;
                }

                if (txtName.getText().toString().trim().length() > 10) {
                    txtName.setError(getString(R.string.error2));
                    return;
                }

                txtTopwawe.setText(txtName.getText().toString().trim());
                editor = sharedPreferences.edit();
                editor.putString("top", txtName.getText().toString().trim());
                editor.commit();
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void dialogdate(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_datelove);
         final EditText txtdatelove;
         final ImageView date;
         Button thaydoi;

        txtdatelove =  dialog.findViewById(R.id.txtdatelove);
        date =  dialog.findViewById(R.id.date);
        thaydoi = dialog.findViewById(R.id.thaydoi);
        txtdatelove.setEnabled(false);
        Cursor cursor=dataBase.getNgayyeu();
        if (cursor.moveToNext()){
            String ngayyeu=cursor.getString(1);

            txtdatelove.setText(ngayyeu);
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonngay(txtdatelove);
                }
        });

        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (txtdatelove.getText().toString().trim().equals("")){
                  txtdatelove.setError(getString(R.string.error));
                  return;
              }
                long day=calendarTwo.getTimeInMillis()-tong;
              dataBase.updateSoNgayYeu(ngay,1);
              dataBase.updateNgayYeu(txtdatelove.getText().toString().trim(),day,1);
              getDataDate();
              dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void chonngay(final EditText editText) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialogpicker);
        final TextView textView=dialog.findViewById(R.id.text);
        final CalendarView simpleCalendarView =dialog.findViewById(R.id.simpleCalendarView);
        calendarTwo=Calendar.getInstance();
        Cursor cursor=dataBase.getNgayyeu();

        final long selectedDate = simpleCalendarView.getDate();
        if (cursor!=null && cursor.moveToFirst()) {
            long i = cursor.getLong(2);
            simpleCalendarView.setDate(i+(60*60*1000));
        }
        simpleCalendarView.setMaxDate(calendarTwo.getTimeInMillis());
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
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

                int gio= Integer.parseInt(sdf.format(date));
                int phut= Integer.parseInt(sdf1.format(date));
                int giay= Integer.parseInt(sdf2.format(date));
                tong=(gio*60*60*1000)+(phut*1000*60)+giay;
                calendarTwo.set(year,month,dayOfMonth);

                ngay= (int) ((selectedDate-(calendarTwo.getTimeInMillis()-(1000*60*60*24)))/(1000*60*60*24))+1;
                textView.setText("Ngày yêu nhau:"+ngay);
                editText.setText(simpleDateFormat.format(calendarTwo.getTimeInMillis())+"");
            }
        });
        TextView textView1=dialog.findViewById(R.id.ok);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void updateDate(){
        calendarOne=Calendar.getInstance();
        calendarTwo=Calendar.getInstance();
        Cursor cursor=dataBase.getNgayyeu();
        if (cursor!=null && cursor.moveToFirst()){
            long i=cursor.getLong(2);
            ngay= (int) ((calendarOne.getTimeInMillis()-(i-(1000*60*60*24)))/(1000*60*60*24));
            dataBase.updateSoNgayYeu(ngay,1);
            getDataDate();




            }
    }

   @SuppressLint("ResourceAsColor")
   public void updateTen(final int i){


             final Dialog dialog=new Dialog(getActivity());
             dialog.setContentView(R.layout.dialog_name);
             final EditText txtName;
             Button thaydoi;
             final String ten;
             final Cursor cursor=dataBase.getTen();
            txtName =  dialog.findViewById(R.id.txtName);
            thaydoi =  dialog.findViewById(R.id.thaydoi);

          if (cursor!=null&& cursor.moveToFirst()) {
           ten = cursor.getString(i);
           txtName.setHint(ten);
           txtName.setHintTextColor(R.color.d81b60);
       }




       thaydoi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (txtName.getText().toString().trim().equals("")){
                   txtName.setError(getString(R.string.error));
                   return;
               }
               if (txtName.getText().toString().trim().length()>20){
                   txtName.setError(getString(R.string.error4));
                   return;
               }
               if (i==1){
                   dataBase.updateTen(txtName.getText().toString().trim(),cursor.getString(2),1);
                   getDataName();
               }else {
                   dataBase.updateTen(cursor.getString(1),txtName.getText().toString().trim(),1);
                   getDataName();
               }
               dialog.dismiss();
           }
       });

       dialog.show();
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
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
