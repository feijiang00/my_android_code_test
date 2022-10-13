package com.example.helloworld

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.widget.Toast

class guangbo : AppCompatActivity() {
    //创建BroadcastReceiver子类实例
    lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guangbo)
        //实例对象
        timeChangeReceiver = TimeChangeReceiver()

        //意图过滤器，作为触发器第二参数
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")

        // 触发器就是需要registerReceiver()，方法传入实例对象，和intentFilter对象
        //也就是对谁触发，触发什么？
        registerReceiver(timeChangeReceiver,intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        //退出页面的时候销毁实例，节省内存
        unregisterReceiver(timeChangeReceiver)
    }

    //需要继承BroadcastReceiver
    inner class TimeChangeReceiver:  BroadcastReceiver(){
        //触发事件
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context,"现在时间改变了",Toast.LENGTH_SHORT).show()
        }

    }
}