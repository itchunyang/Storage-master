package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sqlite.util.DbUtil;

public class MainActivity extends AppCompatActivity {

    private static final String DB_NAME = "abc.db";
    public static final String TABLE = "person";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //打开或创建数据库，有两种方法
    public void openOrCreate(View view) {
        //DbUtil.openOrCreate(this,DB_NAME);
        db = DbUtil.openOrCreate(this,DB_NAME);
    }


    public void add(View view) {
        ContentValues values = new ContentValues();

        for (int i = 10; i < 15; i++) {
            values.put("_name","lcy"+i);
            values.put("_age",i);
            values.put("_email","lcyseek@gmail.com"+i);

            //nullColumnHack：代表强行插入null值的数据列的列名 仅在第三个参数不合法时被使用，为了保证sql语句的合法
            //id 返回新添记录的行号，该行号是一个内部直，与主键id无关，发生错误返回-1
            long id = db.insert(TABLE,null,values);

//            if(id == -1){
//                Toast.makeText(this,"插入失败",Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(this,"插入成功 id="+id,Toast.LENGTH_LONG).show();
//            }

        }
    }

    public void delete(View view) {
        int count = db.delete(TABLE,"_age=?",new String[]{"23"});
        Toast.makeText(this,"受影响的行数"+count,Toast.LENGTH_SHORT).show();
    }

    public void update(View view) {
        ContentValues values = new ContentValues();
        values.put("_age",100);
        int count = db.update(TABLE,values,"_id=? or _id=?",new String[]{"5","4"});
        Toast.makeText(this,"更新了"+count+"行数据",Toast.LENGTH_SHORT).show();
    }

    public void query(View view) {

        String [] columns = {"_name","_age","_email"};//需要查询那些字段
        String  selection = "_email=?";//查询条件
        String [] selectionArgs = {"lcyseek@gmail.com11"};//查询args

        String name,email;
        int age;

        //groupBy：分组  having：分组条件  orderBy：排序类  limit：分页查询的限制
        Cursor cursor = db.query(TABLE,columns,selection,selectionArgs,null,null,"_age desc");
        //db.rawQuery()这个要自己组织sql语句。

        while (cursor.moveToNext()){
            age = cursor.getInt(cursor.getColumnIndex("_age"));
            name = cursor.getString(cursor.getColumnIndex("_name"));
            email = cursor.getString(cursor.getColumnIndex("_email"));
            System.out.println("age="+age+" name="+name+" email="+email);
        }
        cursor.close();
    }

    public void transaction(View view) {
        //开启一个事务
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put("_name","lcy0");
            values.put("_age",240);
            values.put("_email","test0@gmail.com");
            db.insert("person",null,values);

            // 方法设置事务的标志为成功，如果不调用setTransactionSuccessful() 方法，默认会回滚事务
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //检查事务的标志是否为成功，如果为成功则提交事务，否则回滚事务
            db.endTransaction();
        }
    }

    public void closeDb(View view) {
        db.close();
    }

    public void deleteDb(View view) {
        boolean b = deleteDatabase(DB_NAME);
        Toast.makeText(this,"删除"+b,Toast.LENGTH_SHORT).show();
    }


}
