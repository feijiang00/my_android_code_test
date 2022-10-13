package com.example.helloworld.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.helloworld.R;

/*
这里是android 动画的代码演示

桢动画有两种方式，第一种则是设置xml资源，然后将其xml作为img控件的背景，在xml种设置不停的切换，
第二种则是java代码方式，通过java代码初始化动画，然后使用控件的setImageDrawable方法，将动画传给该控件
 */
public class AnimationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        Boolean flag = true;
//        getActionBar().setTitle("帧动画演示");

        ImageView imageView = (ImageView) findViewById(R.id.animationView);
        imageView.setBackgroundResource(R.drawable.frame_anim);//在xml中取代这个android:src="@drawable/img_1

        //xml方式
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
//        imageView.setImageDrawable(drawable);
//        drawable.start();

        /*
        这里再深入讲一下这个接口抽象功能的事情
        先看源码：
        public interface OnClickListener {
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
        void onClick(View v);
        再讲下之前的runnable：
        和runnable一样，抽象出功能，thread中有run方法功能，则是使用runnable接口抽象出来
        那么这里，也是将view中的click抽象出来，当img需要的时候在进入
        总结：这里其实就是view这个对象【thread对象】有点击方法【线程方法run】，当
        子类去继承的时候，就可以重写这个方法，实现自己的逻辑，比如点击后干啥【线程里干啥】，
        因此，重点：当我们想子类有一些方法的时候，
        但是这样连接太紧密了，

    }
         */

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawable.start();
            }
        });

        //java代码方式
        AnimationDrawable drawable1 = new AnimationDrawable();
        initDrawable(drawable1);


        ImageView imageView2 = (ImageView) findViewById(R.id.animationView2);
        imageView2.setImageDrawable(drawable1);
//        imageView2.setBackgroundResource(R.drawable.frame_anim);//在xml中取代这个android:src="@drawable/img_1
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawable1.start();
//                drawable1.setExitFadeDuration(1000);

            }
        });

//        drawable1.setOneShot(false);
//        drawable1.start();

        //给java实现的增加一些参数可调
        Button stopAnim = findViewById(R.id.stopAnim);
        stopAnim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawable1.stop();

            }
        });

        Button addFade = findViewById(R.id.addenterFad);
        addFade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawable1.setEnterFadeDuration(500);
            }
        });

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initDrawable(AnimationDrawable animationDrawable) {
        //这里图片没有设置1-n，我直接一个一个添加
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kafei),200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.naicha),100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.kele),100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.shutiao),100);

        //for循环添加
//        for (int i = 1; i <= 4; i++) {
//            int id = this.getResources().getIdentifier("anim_" +i, "drawable", getPackageName());
//            Drawable drawable = getResources().getDrawable(id);
//            animationDrawable.addFrame(drawable, 200);
//        }
    }

}