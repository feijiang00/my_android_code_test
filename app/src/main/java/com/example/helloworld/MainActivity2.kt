package com.example.helloworld

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf

/*
    一些延时方法
    一、使用线程的休眠实现延时操作
    new Thread(new Runnable() {
            @Override
            public void run() {

                Thread.sleep(1000); // 休眠1秒

                /**
                 * 延时执行的代码
                 */

            }
        }).start();

     二、使用TimerTask实现延时操作
             Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                /**
                 * 延时执行的代码
                 */

            }
        },1000); // 延时1秒

     三、使用Handler的postDelayed()方法实现延时操作
     new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /**
                 * 延时执行的代码
                 */

            }
        },1000); // 延时1秒

 */
class MainActivity2 : AppCompatActivity() {
    //存储联系人变量
    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //演示延时休眠对onCreate()方法的影响
//        Thread.sleep(5000); // 休眠1秒

        //演示创建数据库
        val dbHelper = MyHelp(this,"BookStore",1)
        val createDb: Button = findViewById(R.id.createDatabase)
        createDb.setOnClickListener(){
            dbHelper.writableDatabase
        }

        //调用打电话
        val makeCall:Button = findViewById(R.id.makecall)
        makeCall.setOnClickListener {
            //判断用户是否授权，第一次参数为Context，第二个参数为具体的权限，打电话的权限：Manifest.permission.CALL_PHONE
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //没有授权，那么申请授权，第二个参数为String数组，这里给要申请的权限放进去
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE), 1)
            } else {
                //授权了，直接调用
                call()
            }
        }

        //获取联系人
        val getPeople:Button = findViewById(R.id.getpeople)
        getPeople.setOnClickListener(){
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
            val contactsView:ListView = findViewById(R.id.contactsView)
            contactsView.adapter = adapter
            Log.d("adapter","$adapter")
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS), 1)
            } else {
                readContacts()
            }
        }

        //获取我的ContentProvider
        val getMyProvider:Button = findViewById(R.id.getmyprovider)
        getMyProvider.setOnClickListener(){
            //我不需要授权，因为这是我自己的ContentProvider，因此直接调用插入方法，然后拿到值去显示
            insertProvider() //插入数据
            Toast.makeText(this,"插入数据成功", Toast.LENGTH_SHORT).show()
            getMyProvider()
        }

    }

    /*下面是打电话逻辑代码，申请权限*/
    //requestPermissions()方法后，都会回调到这个方法中，授权的结果则会封装在grantResults参数中
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                //用户授权了
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    call()
                    readContacts()
                } else {
                    //用户没授权
                    Toast.makeText(this, "You denied the permission",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //拨打电话
    private fun call() {
        Log.d("message","call()运行了")
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    /*下面是获取联系人演示*/
    @SuppressLint("Range")
    private fun readContacts() {
        Log.d("message","readContacts()运行了")
        // 查询联系人数据
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)?.apply {
            while (moveToNext()) {
                // 获取联系人姓名
                val displayName = getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                // 获取联系人手机号
                val number = getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$displayName\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }

    /*下面演示获取我自己的ContentProvider*/
    //添加数据
    private fun insertProvider(){
        Log.d("message","insertProvider()运行了")
        val uri = Uri.parse("content://com.example.helloword.myprovider/book")
        val values = contentValuesOf("author" to "xiaojiang", "author" to "dajiang")
        /*
        E/SQLiteLog: (1) table Book has no column named column1 in "INSERT INTO Book(column1,column2) VALUES (?,?)"
        E/SQLiteDatabase: Error inserting column1=text column2=hihihiandroid.database.sqlite.SQLiteException:
         table Book has no column named column1 (code 1 SQLITE_ERROR): , while compiling: INSERT INTO Book(column1,column2) VALUES (?,?)
         */
        contentResolver.insert(uri,values)
    }

    //获取数据
    @SuppressLint("Range")
    private fun getMyProvider(){
        Log.d("message","getMyProvider()运行了")
        val uri = Uri.parse("content://com.example.helloword.myprovider/book")
        contentResolver.query(
            uri,
            null, null, null, null)?.apply {
            while (moveToNext()) {
                // 获取key = column1 的所有数据
                val data = getString(getColumnIndex("column1"))
                // 获取联系人手机号
                contactsList.add(data)
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }

    override fun onPause() {
        super.onPause()
//        Thread.sleep(5000); // 休眠1秒
    }



}