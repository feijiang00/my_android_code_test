package com.example.helloworld

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)
        Log.d("BaseActivity",javaClass.simpleName)

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    public fun test(){
        println("测试，是不是需要初始")
    }

}