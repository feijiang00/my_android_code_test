package com.example.helloworld.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val mBinder = DownloadBinder()

    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d("MyService", "startDownload executed")
        }
        fun getProgress(): Int {
            Log.d("MyService", "getProgress executed")
            return 0
        }
    }

    override fun onBind(intent: Intent): IBinder {

        return mBinder
    }

    //创建时调用
    override fun onCreate() {
        super.onCreate()
    }
    //启动时调用
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("id","Service pid："+android.os.Process.myPid())
        Log.d("id","Service pid："+android.os.Process.myPid())

        println("activity tid："+android.os.Process.myTid())
        return super.onStartCommand(intent, flags, startId)
    }
    //销毁时调用
    override fun onDestroy() {
        super.onDestroy()
    }

}