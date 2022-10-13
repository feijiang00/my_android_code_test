package com.example.helloworld.view.event;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.helloworld.R;

public class EventActivity extends AppCompatActivity {

    Button button1,button2;
    ViewGroup myLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        button1 = (Button)findViewById(R.id.eventbutton1);
        button2 = (Button)findViewById(R.id.eventbutton2);
        myLayout = (LinearLayout)findViewById(R.id.eventmy_layout);

        // 1.为ViewGroup布局设置监听事件
        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "点击了ViewGroup");
            }


            void onTourchEvent(){

            }
        });


//        button1.dispatchTouchEvent(MotionEvent event){
//
//        }

        // 2. 为按钮1设置监听事件
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "点击了button1");
            }
        });

        button1.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("TAG", "点击了");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("TAG", "移动了");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("TAG", "抬起来了");
                        break;

                }
                return true; //这里返回true，则表示事件到此结束了，不会在触发下一个setOnClickListener()函数
            }
        });

        // 3. 为按钮2设置监听事件
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "点击了button2");
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("TAG", "点击了");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("TAG", "移动了");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("TAG", "抬起来了");
                        break;

                }
                return false;//这里返回false，虽然事件处理完了，还会继续下一个流程，会在触发下一个setOnClickListener()函数
            }
        });

    }
}
