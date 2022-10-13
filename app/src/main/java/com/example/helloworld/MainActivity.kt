package com.example.helloworld

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.helloworld.animation.AnimationActivity
import com.example.helloworld.animation.AttributeActivity
import com.example.helloworld.oom.OomActivity
import com.example.helloworld.service.MyService
import com.example.helloworld.service.ServiceActivity
import com.example.helloworld.view.ClockActivity
import com.example.helloworld.view.WatchView
import com.example.helloworld.view.event.EventActivity

/*
   Log.d("stack", Log.getStackTraceString(Throwable())) //打印本地调用堆栈
 */
class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myButton: Button = findViewById(R.id.button)
        Log.d(tag, "onCreate")
        setContentView(R.layout.activity_main)


        myButton.setOnClickListener{
            Log.d("message","我应该是被点了")
//            Toast.makeText(this,"hello,you clicked button ？？",Toast.LENGTH_SHORT).show()

            // 显示
//            val intent = Intent(this,MainActivity2::class.java)

            //隐式
//            val intent = Intent("com.example.MainActivity2.ACTION_START")
//            intent.addCategory("com.example.MainActivity2.MY_CATEGORY")

            //其他程序的activity
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.baidu.com")
            startActivity(intent)
        }


        val startNormalActivity:Button = findViewById(R.id.startNormalActivity)
        startNormalActivity.setOnClickListener() {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }
        val startDialogActivity:Button = findViewById(R.id.startDialogActivity)
        startDialogActivity.setOnClickListener(){
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        //横竖屏切换按钮
        val btoH:Button = findViewById(R.id.toHengPing)
        btoH.setOnClickListener(){
            Log.d("message","我应该是被点了")
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//反向横屏：ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        }
        val btoS:Button = findViewById(R.id.toShuPing)
        btoS.setOnClickListener(){
            Log.d("message","我应该是被点了")
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        //去时钟
        val toClock:Button = findViewById(R.id.toClock)
        toClock.setOnClickListener(){
            val intent = Intent(this, ClockActivity::class.java)
            startActivity(intent)
        }

        //去service
        val toservice:Button = findViewById(R.id.toMyservice)
        toservice.setOnClickListener(){
            val intent = Intent(this, ServiceActivity::class.java)
            startActivity(intent)
        }

        //去事件分发
        val toEvent:Button = findViewById(R.id.toMyEvent)
        toEvent.setOnClickListener(){
            val intent = Intent(this,EventActivity::class.java)
            startActivity(intent)
        }

        //去动画
        val toMyAnimationButton:Button =findViewById(R.id.toanimation)
        toMyAnimationButton.setOnClickListener(){
            val intent = Intent(this, AnimationActivity::class.java)
            startActivity(intent)
        }

        //去属性动画
        val toAttributeAnimation:Button =findViewById(R.id.toAttributeAnimation)
        toAttributeAnimation.setOnClickListener(){
            val intent = Intent(this,AttributeActivity::class.java)
            startActivity(intent)
        }

        //去oom
        val toOomButton:Button = findViewById(R.id.tooom)
        toOomButton.setOnClickListener(){
            val intent = Intent(this,OomActivity::class.java)
            startActivity(intent)
        }
    }

    /*
    菜单实现
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> Toast.makeText(this, "You clicked Add",
                Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "You clicked Remove",
                Toast.LENGTH_SHORT).show()
        }
        return true
    }

    /*
    下面的方法针对activity的生命周期进行log打印证实
     */

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }


}