package com.example.helloworld.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.helloworld.R

import com.example.helloworld.view.clock.MyClockView
class ClockActivity : AppCompatActivity() {
    lateinit var mcv_myClock:MyClockView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        mcv_myClock=findViewById(R.id.mcv_myClock)
        mcv_myClock.start()
    }
}