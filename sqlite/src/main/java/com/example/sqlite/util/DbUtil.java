package com.example.sqlite.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lcy on 2016/6/23.
 */
public class  DbUtil {

    //创建数据库方法
    //1.Context.openOrCreateDatabase
    public static SQLiteDatabase openOrCreate(Context context,String dbName){
        SQLiteDatabase db = context.openOrCreateDatabase(dbName,Context.MODE_PRIVATE,null);

        //多次创建表会报错(table [person] already exists)，需要自己手动判断表是否存在。
        //从这点来看，还是SQLiteOpenHelper方法好一点
        String sql = "CREATE TABLE [person]("
                + "[_id] INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "[_name] VARCHAR(50) UNIQUE NOT NULL,"
                + "[_age] INT NOT NULL DEFAULT 15,"
                + "[_email] VARCHAR(50)"
                + ")";

        db.execSQL(sql);
        return db;
    }

    public static SQLiteDatabase openOrCreate(Context context,String dbName,int version){

        //直接这句话不会创建数据库,只有调用getWritableDatabase()、getReadableDatabase()才会打开或创建
        DbHelper dbHelper = new DbHelper(context,dbName,null,version);


        //注意getReadableDatabase()并不是仅仅获取可读的数据库，他仍尝试获取可写操作的数据库对象，当出现意外时，
        //例如磁盘空间已满，则只获取可读的数据库
        return dbHelper.getWritableDatabase();
    }


    static class DbHelper extends SQLiteOpenHelper{

        public final String TAG = DbHelper.class.getSimpleName();
        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "onCreate: ");
            String sql = "CREATE TABLE [person]("
                    +"[_id] INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"[_name] VARCHAR(50) UNIQUE NOT NULL,"
                    +"[_age] INT NOT NULL DEFAULT 15,"
                    +"[_email] VARCHAR(50)"
                    +")";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "onUpgrade: "+" oldVersion="+oldVersion+" newVersion="+newVersion);
        }
    }

}
