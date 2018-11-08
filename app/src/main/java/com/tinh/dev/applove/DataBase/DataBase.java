package com.tinh.dev.applove.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {

    private final String DB_NAME = "Yeu";
    private final String TB_TEN = "Ten";
    private final String TB_NGAYYEUNHAU = "NgayYeu";
    private final String TB_SONGAYYEUNHAU = "SoNgayYeu";
    private final String TB_LINK_ANH = "LinkAnh";
    private final String TB_LINK_ANH1 = "LinkAnh1";
    private final String TB_THONGDIEP="ThongDiep";
    private final String TB_PASSWORD="PassWord";
    private final String TB_LINKS="Links";
    private final int DB_VERSION = 1;
    private SQLiteDatabase database;

    public DataBase(Context context) {
        OpenHelper helper = new OpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String ten = "CREATE TABLE IF NOT EXISTS Ten(_id INTEGER PRIMARY KEY AUTOINCREMENT,TenBanNam NVARCHAR,TenBanNu NVARCHAR)";
            String link = "CREATE TABLE IF NOT EXISTS Links(_id INTEGER PRIMARY KEY AUTOINCREMENT,nhac INTEGER)";
            String ngayyeu = "CREATE TABLE IF NOT EXISTS NgayYeu(_id INTEGER PRIMARY KEY AUTOINCREMENT,NgayYeu NVARCHAR,Ngay LONG)";
            String songayyeu = "CREATE TABLE IF NOT EXISTS SoNgayYeu(_id INTEGER PRIMARY KEY AUTOINCREMENT,SoNgayYeu INTEGER)";
            String ninkanh = "CREATE TABLE IF NOT EXISTS LinkAnh(_id INTEGER PRIMARY KEY AUTOINCREMENT,Link NVARCHAR)";
            String ninkanh1 = "CREATE TABLE IF NOT EXISTS LinkAnh1(_id INTEGER PRIMARY KEY AUTOINCREMENT,Link1 NVARCHAR)";
            String thongdiep = "CREATE TABLE IF NOT EXISTS ThongDiep(_id INTEGER PRIMARY KEY AUTOINCREMENT,thongdiep INTEGER)";
            String pass = "CREATE TABLE IF NOT EXISTS PassWord(_id INTEGER PRIMARY KEY AUTOINCREMENT,password INTEGER)";
            db.execSQL(ten);
            db.execSQL(ngayyeu);
            db.execSQL(songayyeu);
            db.execSQL(ninkanh);
            db.execSQL(ninkanh1);
            db.execSQL(thongdiep);
            db.execSQL(pass);
            db.execSQL(link);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS Ten");
            db.execSQL("DROP TABLE IF EXISTS NgayYen");
            db.execSQL("DROP TABLE IF EXISTS SoNgayYeu");
            onCreate(db);
        }
    }




    public void insertTen(String tenNam, String tenNu) {
        ContentValues values = new ContentValues();
        values.put("TenBanNam", tenNam);
        values.put("TenBanNu", tenNu);
        database.insert(TB_TEN, null, values);

    }

    public void insertNgayYeu(String ngayyeu,long ngay) {
        ContentValues values = new ContentValues();
        values.put("NgayYeu", ngayyeu);
        values.put("Ngay", ngay);
        database.insert(TB_NGAYYEUNHAU, null, values);
    }



    public void insertSoNgayYeu(int songayyeu) {
        ContentValues values = new ContentValues();
        values.put("SoNgayYeu", songayyeu);
        database.insert(TB_SONGAYYEUNHAU, null, values);
    }

    public void updateTen(String tenNam, String tenNu, int id) {
        ContentValues values = new ContentValues();
        values.put("TenBanNam", tenNam);
        values.put("TenBanNu", tenNu);
        database.update(TB_TEN, values, "_id=" + id, null);
    }



    public void updateNgayYeu(String ngayyeu,long ngay, int id) {
        ContentValues values = new ContentValues();
        values.put("NgayYeu", ngayyeu);
        values.put("Ngay", ngay);
        database.update(TB_NGAYYEUNHAU, values, "_id=" + id, null);
    }



    public void updateSoNgayYeu(int songayyeu, int id) {
        ContentValues values = new ContentValues();
        values.put("SoNgayYeu", songayyeu);
        database.update(TB_SONGAYYEUNHAU, values, "_id=" + id, null);
    }





    public Cursor getLink() {
        return database.query(TB_LINK_ANH, null,
                null,
                null,
                null,
                null,
                null);
    }




    public void PassWord(int so){
        ContentValues values = new ContentValues();
        values.put("password", so);
        database.insert(TB_PASSWORD, null, values);
    }


    public void updatethongdiep(int so,int id){
        ContentValues values = new ContentValues();
        values.put("thongdiep", so);
              database.update(TB_THONGDIEP,values,"_id="+id,null);
    }
    public void updatetPassWord(int so,int id){
        ContentValues values = new ContentValues();
        values.put("password", so);
        database.update(TB_PASSWORD,values,"_id="+id,null);
    }




    public Cursor getTen() {
        return database.query(TB_TEN, null,
                null,
                null,
                null,
                null,
                null);
    }

    ;

    public Cursor getNgayyeu() {
        return database.query(TB_NGAYYEUNHAU, null,
                null,
                null,
                null,
                null,
                null);
    }

    ;

    public Cursor getSoNgayYeu() {
        return database.query(TB_SONGAYYEUNHAU, null,
                null,
                null,
                null,
                null,
                null);
    }
    ;



        public Cursor getPassWord(){
        return database.query(TB_PASSWORD, null,
                null,
                null,
                null,
                null,
                null);
    };

    public Cursor getLinks(){
        return database.query(TB_LINKS, null,
                null,
                null,
                null,
                null,
                null);
    };
}
