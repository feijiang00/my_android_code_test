package com.example.helloworld.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.helloworld.R;


public class WatchView extends View {
    private static final String TAG = "MyClockView";

    private Paint mPaint;

    private int mCircleWidth = 20;
    private int radius = 500;

    // 这些下面都会重新计算
    private int lenHour = 200;
    private int lenMin = 300;
    private int lenSec = 400;

    private int mProgressSecond = 0;
    private int mProgressMin = 30;
    private int mProgressHour = 11;

    // background
    private int mBackgroundColor;
    public WatchView(Context context) {
        this(context,null);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Init();
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.WatchView);
        mProgressHour = array.getInteger(R.styleable.WatchView_hour,0);
        mProgressHour %= 12;
        mProgressMin = array.getInteger(R.styleable.WatchView_min,0);
        mProgressMin %= 60;
        mProgressSecond = array.getInteger(R.styleable.WatchView_sec,0);
        mProgressSecond %= 60;
        mBackgroundColor = array.getColor(R.styleable.WatchView_background_color,getResources().getColor(R.color.light_blue_400));
        array.recycle();

    }

//    public int getmProgressSecond() {
//        return mProgressSecond;
//    }
//
//    public void setmProgressSecond(int mProgressSecond) {
//        this.mProgressSecond = mProgressSecond % 60;
//    }
//
//    public int getmProgressMin() {
//        return mProgressMin;
//    }
//
//    public void setmProgressMin(int mProgressMin) {
//        this.mProgressMin = mProgressMin % 60;
//    }
//
//    public int getmProgressHour() {
//        return mProgressHour;
//    }
//
//    public void setmProgressHour(int mProgressHour) {
//        this.mProgressHour = mProgressHour % 12;
//    }

    private void Init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setColor(getResources().getColor(R.color.black));
        mPaint.setStrokeWidth(10);  // 圆环宽度10
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(100);
        mPaint.setTextAlign(Paint.Align.CENTER);

        // 绘图线程
        new Thread(){
            @Override
            public void run() {
                while (true){
                    mProgressSecond +=1;
                    if (mProgressSecond == 60){
                        mProgressSecond = 0;
                        mProgressMin += 1;
                        // 处理时针
                        if (mProgressMin == 60){
                            mProgressMin = 0;
                            mProgressHour += 1;
                        }
                    }

                    // 重新绘制
                    postInvalidate();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSepcSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 继承自自定义View，需要给wrap_content这种模式指定一个具体的值，否则它的大小和match_parent没有区别
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(500,500);
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(500,heightSpecSize);
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSepcSize,500);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化半径，指针长度等,这些计算不能放到初始化函数和onMeasure中完成，因为那时宽度还没有确定，getWidth = 0.
        // 也不要放到onDraw里面做，因为onDraw调用比较频繁，最好不要放太多操作进去
        int w = getWidth() - getPaddingLeft() - getPaddingRight();
        int h = getHeight() - getPaddingTop() - getPaddingBottom();
        if (w > h){
            radius = h / 2;
        }else {
            radius = w / 2;
        }
        // 确定指针长度
        lenSec = (int) ((float)(radius) * 3 / 4);
        lenMin = (int) ((float)(radius) / 2);
        lenHour = (int) ((float)(radius) / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center;
        if (getWidth() > getHeight()){
            center = getHeight() / 2;
        }else {
            center = getWidth() / 2;
        }
        // 圆心坐标
        float xCenter = center;
        float yCenter = center;
        // 表框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(getResources().getColor(R.color.black));
        canvas.drawCircle(center,center,radius,mPaint);
        // 在指针的旋转中心画一个小圈圈
        mPaint.setColor(getResources().getColor(R.color.black));
        canvas.drawCircle(xCenter,yCenter,10,mPaint);
        // 绘制背景
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(center,center,radius - mCircleWidth / 2,mPaint);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(getResources().getColor(R.color.black));


        // 绘制12个大刻度和其他小刻度
        for (int i = 0; i < 60; i++) {
            // 大刻度
            int len = 0;
            if (i%5 == 0){
                len = 30;
                mPaint.setStrokeWidth(20);
            }else {
                len = 20;
                mPaint.setStrokeWidth(10);
            }
            double hudu = i * 6 * Math.PI / 180;
            double sin1 = Math.sin(hudu);
            double cos1 = Math.cos(hudu);
            float xEnd = (float) (radius * sin1)+xCenter;
            float yEnd = -(float) (radius * cos1)+yCenter;

            float xStart = (float) ((radius-len) * sin1)+xCenter;
            float yStart = -(float) ((radius-len) * cos1)+yCenter;

            canvas.drawLine(xStart,yStart,xEnd,yEnd,mPaint);
        }
        /**
         * 下面我计算弧度时算式看起来很混乱，这是因为时分秒都是int类型的，
         * 比如计算mProgressMin/60， 我们希望得到的结果是0.5, 但是实际上是0， 所以我调整了一下计算顺序，
         * 也可以先转换类型再计算
         */
        // 绘制秒针
        mPaint.setColor(getResources().getColor(R.color.purple_500));
        mPaint.setStrokeWidth(10);
        double sec = mProgressSecond * 360 * Math.PI / 60 / 180;
        float sStart = (float) (lenSec * Math.sin(sec)) + xCenter;
        float sEnd = - (float) (lenSec * Math.cos(sec)) + yCenter;
        canvas.drawLine(xCenter, yCenter, sStart, sEnd, mPaint);

        // 绘制分针
        mPaint.setColor(getResources().getColor(R.color.teal_200));
        mPaint.setStrokeWidth(20);
        double min = mProgressMin * 360 * Math.PI / 60 / 180;
        float mStart = (float) (lenMin * Math.sin(min)) + xCenter;
        float mEnd = - (float) (lenMin * Math.cos(min)) + yCenter;
        canvas.drawLine(xCenter, yCenter, mStart, mEnd, mPaint);

        // 绘制时针,时针应该按照分针偏移一定角度
        mPaint.setColor(getResources().getColor(R.color.white));
        mPaint.setStrokeWidth(30);
        // 先计算所占的角度mProgressHour / 12 * 360, 再计算弧度 / 180 * Math.PI
        double hour = mProgressHour * 360 * Math.PI / 12 / 180;
        // 分会导致时针偏移一定的角度
        hour += mProgressMin * 30 * Math.PI / 180 / 60;
        float hStart = (float) (lenHour * Math.sin(hour)) + xCenter;
        float hEnd = - (float) (lenHour * Math.cos(hour)) + yCenter;
        canvas.drawLine(xCenter, yCenter, hStart, hEnd, mPaint);

        mPaint.setColor(getResources().getColor(R.color.black));
        // 绘制数字
        mPaint.setStrokeWidth(radius/60);
        mPaint.setTextSize((float) (radius / 3.5));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        // 绘制数字时，为了使数字居中对其，应该给y坐标一定的偏移量
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        for (int i = 0; i < 12; i++) {
            double d = (i+1) * 30 * Math.PI / 180;
            float x = (float) ((radius - 100) * Math.sin(d) + xCenter);
            float y = (float) (-(radius - 100) * Math.cos(d) + yCenter);
            float baseline = y + distance;

            canvas.drawText(String.valueOf(i+1),x,baseline,mPaint);
        }

    }

}

