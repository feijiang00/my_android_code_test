package com.example.helloworld.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.helloworld.R

class ServiceActivity : AppCompatActivity() {

    lateinit var downloadBinder: MyService.DownloadBinder
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }
        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        Log.d("id","activity pid："+android.os.Process.myPid())
        Log.d("id","activity tid："+android.os.Process.myTid())
        val startService:Button = findViewById(R.id.startService)

        startService.setOnClickListener(){
            val intent = Intent(this,MyService::class.java)
            startService(intent)
        }

        val finishService:Button = findViewById(R.id.finishService)
        finishService.setOnClickListener(){
            val intent = Intent(this,MyService::class.java)
            stopService(intent)
        }

        val bindService:Button = findViewById(R.id.bindServiceBtn)
        bindService.setOnClickListener(){
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE) // 绑定Service
        }

        val unbindService:Button = findViewById(R.id.unbindServiceBtn)
        unbindService.setOnClickListener(){
            unbindService(connection) // 解绑Service
        }
    }
}