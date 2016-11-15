package com.example.assetsandraw;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 1.Assets
 *      Assets是和res目录平级的
 *      Studio新建Assets方法:
 *      右键模块 > New > folder > assets Folder
 *
 * 2.raw
 *      位于res目录下
 *
 *
 * 相同点:两者目录下的文件在打包后会原封不动的保存在apk包中，不会被编译成二进制
 * 不同点:
 *      raw中的文件会被映射到R.java文件中,访问的时候直接使用资源ID
 *      assets文件夹下的文件不会被映射到R.java中，访问的时候需要AssetManager类。
 *      raw不可以有目录结构(原因是res不支持深度子目录)，而assets则可以有子目录结构
 *
 * drawable里面有个apk.png, AssetManager manager = getAssets() 他的根目录就是这个目录
 */

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private EditText et;
    private String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
        et = (EditText) findViewById(R.id.et);
    }

    public void getAssets(View view) {
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("image/chen.jpg");
//            InputStream is = assetManager.open("sex.jpg");

            iv.setImageBitmap(BitmapFactory.decodeStream(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRaw(View view) {

        try {
            InputStream is = getResources().openRawResource(R.raw.abc);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int i;
            while((i=is.read())!=-1){
                bos.write(i);
            }

            et.setText(bos.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveToSDcardFromasset(View view) {
        AssetManager manager = getAssets();

        try {
            String[] list = manager.list("image");

            for (int i = 0; i < list.length; i++) {
                String fileName = list[i];
                InputStream is = manager.open("image/"+fileName);
                FileOutputStream fos = new FileOutputStream(sdcard+"/"+fileName);

                byte[] buff = new byte[1024];
                int len;

                while ((len = is.read(buff)) != -1){
                    fos.write(buff,0,len);
                }
                fos.close();
                is.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"已经成功拷贝到SD卡",Toast.LENGTH_SHORT).show();
    }
}
