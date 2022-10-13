package com.example.helloworld.oom;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;


public class OomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oom);

        //触发Bitmap泄漏
        Button startBitmapButton = findViewById(R.id.startBitmap);
        startBitmapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                useBitmap();
            }
        });

        //触发list强引用一直堆积 内存溢出
        Button stratListButton = findViewById(R.id.startList);
        stratListButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    useList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    内存泄漏1： Bitmap对象使用完成后不进行回收
     */
    private void useBitmap(){
        byte[] bytes = new byte[1024 * 1024];
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        //将其回收
//        if(bitmap != null && bitmap.isRecycled()){
//            bitmap.recycle();
//        }
    }

    /*
    list内存溢出
     */
    private void useList() throws InterruptedException {
        List<StringBuilder> list = new ArrayList<>();
        StringBuilder tmp = new StringBuilder(new String("a"));
        for(int i=0;i<100;i++){
            tmp.append(tmp);
            list.add(tmp);
            Thread.sleep(1000);
        }
//        List<String> list = new ArrayList<>();
//        String tmp2 ="a";
//        int j=0;
//        for(int i=0;i<100000;i++){
//            list.add(tmp2);
//            System.out.println(i++);
//        }

    }

}