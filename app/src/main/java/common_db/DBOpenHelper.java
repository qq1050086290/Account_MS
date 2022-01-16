package common_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DBNAME = "account.db";

    public DBOpenHelper(Context context){
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_in_account(id integer primary key, money decimal, date varchar(14), type varchar(10),"+
                "handler varchar(10), mark varchar(200))");
        db.execSQL("create table tb_out_account(id integer primary key, money decimal, date varchar(14), type varchar(10),"+
                "handler varchar(10), mark varchar(200))");
        db.execSQL("create table tb_password(password varchar(16))");
        db.execSQL("create table tb_flag(id integer(10) primary key, flag varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
