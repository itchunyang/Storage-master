package com.example.sharepreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * SharedPreferences数据保存在:
     *      目录：/data/data/＜包＞/shared_prefs/***.xml
     *
     * 存入XML后的内容
     *      <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
     *          <map>
     *              <int name="lastPost" value="120" />
     *              <boolean name="isDebug" value="false" />
     *              <string name="version">V_sp5.2.3</string>
     *          </map>
     */


    public void write(View view) {
        SharedPreferences preferences = getSharedPreferences("abc",MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putString("version","V_sp5.2.3");
        editor.putBoolean("isDebug",false);
        editor.putInt("lastPost",120);
        editor.commit();
    }

    public void read(View view) {
        SharedPreferences preferences = getSharedPreferences("abc",MODE_PRIVATE);
        int lastPost = preferences.getInt("lastPost",-1);
        boolean b = preferences.getBoolean("isDebug",true);
        String version = preferences.getString("version","null");

        Log.i(TAG, "read: lastPost="+lastPost+" isDebug="+b+" version="+version);

        Log.i(TAG, "read: "+preferences.getString("version","default"));
    }

    public void delete(View view) {
        SharedPreferences preferences = getSharedPreferences("abc",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("version");
        editor.commit();
    }

    public void update(View view) {
        SharedPreferences preferences = getSharedPreferences("abc",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("version","newVersion");
        editor.commit();
    }
}
