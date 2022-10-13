package com.example.helloworld.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CXB on 2018/6/7.
 */

public class AttributeActivity extends AppCompatActivity {
    private TextView text_show;
    private ObjectAnimator objectAnimator;//声明ObjectAimator类，属性动画第二种方式
    private LinearLayout ll_root;
    private List<String> attrib_list = new ArrayList<>();//要修改的属性列表
    private Integer start_int;//初始值
    private Integer finish_int;//结束值
    private Integer duration;//动画持续时间
    private List<BaseInterpolator> list = new ArrayList<>();//插值器列表
    private Integer l1=0,l2=0;//list下标
    private float rotateDu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);
        text_show = findViewById(R.id.attri_show);
        ll_root = findViewById(R.id.attri_root);
        //添加几个插值器
        list.add(new AccelerateDecelerateInterpolator());
        list.add(new AccelerateInterpolator());
        list.add(new LinearInterpolator());
        //添加几个属性值
        attrib_list.add("alpha");//透明度渐变，参数一般是0,1
//        attrib_list.add("scaleY");//缩放,参数一般是：1f, 2f, 3f, 4f, 3f, 2f, 1f，表示可回退
        attrib_list.add("translationX");//位移，参数一般是float width = ll_root.getWidth();//获取当前手机的的屏幕宽高，拿到width，然后width/num
        attrib_list.add("rotation");//旋转，参数一般是0,360


    }


    public void initObjectAnimator(ObjectAnimator objectAnimator,Integer l1,Integer l2,Integer start_int,Integer finish_int,Integer duration){

    }

    public void changeA(View view){
        l1 =(l1+1)%3;
        Log.d("messagae", "list1:" + l1);
    }

    public void changeS(View view){
        l2 =(l2+1)%3;
        Log.d("messagae", "list2:" + l2);
    }
    /**
     * 播放
     * @param view
     */
    public void play(View view) {
        EditText tstart = findViewById(R.id.tstart);
        start_int = Integer.parseInt(tstart.getText().toString());
        Log.d("message", "num is :"+start_int);
        EditText tfinish = findViewById(R.id.tfinish);
        finish_int = Integer.parseInt(tfinish.getText().toString());
        EditText ttime = findViewById(R.id.ttime);
        duration = Integer.parseInt(ttime.getText().toString());

        objectAnimator = ObjectAnimator.ofFloat(text_show, attrib_list.get(l1), start_int, finish_int);
        objectAnimator.setDuration(duration);//设置持续事件
        objectAnimator.setInterpolator(list.get(l2));//设置插值器
        objectAnimator.setRepeatCount(1);//设置动画重复次数
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
        Log.d("message", "attrib:"+attrib_list.get(l1)+"\n"+"start_int:"+start_int+"\n"+
                "finish_int:"+finish_int+"\n"+"duration"+duration);
        objectAnimator.start();
    }

    /**
     * 组合动画
     *
     * @param view
     */
    public void stop(View view) {
    }
}