package com.example.helloworld

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

/*
大部分演示放在MainActivity2中
 */

/*创建自己的建ContentProvider*/
class MyContentProvider: ContentProvider(){
    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.example.helloword.myprovider"

    //保存数据的数据库
    private var dbHelper: MyHelp? = null

    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority, "book", bookDir)
        matcher.addURI(authority, "book/#", bookItem)
        matcher.addURI(authority, "category", categoryDir)
        matcher.addURI(authority, "category/#", categoryItem)
        matcher
    }

    /*
    onCreate()初始化ContentProvider的时候调用。通常会在这里完成对数据库的创建和
    升级等操作，返回true表示ContentProvider初始化成功，返回false则表示失败。
     */
    override fun onCreate() = context?.let {
        dbHelper = MyHelp(it, "BookStore.db", 2)
        true
    } ?: false


    /*
    query()从ContentProvider中查询数据。uri参数用于确定查询哪张表，projection
    参数用于确定查询哪些列，selection和selectionArgs参数用于约束查询哪些行，
    sortOrder参数用于对结果进行排序，查询的结果存放在Cursor对象中返回
     */
    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?) = dbHelper?.let {
        // 查询数据
        val db = it.readableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            /*后当query()方法被调用的时候，就会
           通过UriMatcher的match()方法对传入的Uri对象进行匹配，如果发现UriMatcher中某个
           内容URI格式成功匹配了该Uri对象，则会返回相应的自定义代码，然后我们就可以判断出调用
           方期望访问的到底是什么数据了。*/
               //查询全部
            bookDir -> db.query("Book", projection, selection, selectionArgs,
                null, null, sortOrder)
            //查询一行
            bookItem -> {
                val bookId = uri.pathSegments[1]
                db.query("Book", projection, "id = ?", arrayOf(bookId), null, null,
                    sortOrder)
            }
            categoryDir -> db.query("Category", projection, selection, selectionArgs,
                null, null, sortOrder)
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                db.query("Category", projection, "id = ?", arrayOf(categoryId),
                    null, null, sortOrder)
            }
            else -> null
        }
        cursor
    }

    /*
    getType()。根据传入的内容URI返回相应的MIME类型。
     */
    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        bookDir -> "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book"
        bookItem -> "vnd.android.cursor.item/vnd.com.example.databasetest.provider.book"
        categoryDir -> "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.category"
            categoryItem -> "vnd.android.cursor.item/vnd.com.example.databasetest.provider.category"
        else -> null
    }

    /*
    insert()向ContentProvider中添加一条数据。uri参数用于确定要添加到的表，待添
    加的数据保存在values参数中。添加完成后，返回一个用于表示这条新记录的URI。
     */
    override fun insert(uri: Uri, values: ContentValues?) = dbHelper?.let {
        // 添加数据
        val db = it.writableDatabase
        val uriReturn = when (uriMatcher.match(uri)) {
            bookDir, bookItem -> {
                val newBookId = db.insert("Book", null, values)
                Uri.parse("content://$authority/book/$newBookId")
            }
            categoryDir, categoryItem -> {
                val newCategoryId = db.insert("Category", null, values)
                Uri.parse("content://$authority/category/$newCategoryId")
            }
            else -> null
        }
        uriReturn
    }

    /*
    delete()。从ContentProvider中删除数据。uri参数用于确定删除哪一张表中的数据，
    selection和selectionArgs参数用于约束删除哪些行，被删除的行数将作为返回值返回。
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?)
            = dbHelper?.let {
        // 删除数据
        val db = it.writableDatabase
        val deletedRows = when (uriMatcher.match(uri)) {
            bookDir -> db.delete("Book", selection, selectionArgs)
            bookItem -> {
                val bookId = uri.pathSegments[1]
                db.delete("Book", "id = ?", arrayOf(bookId))
            }
            categoryDir -> db.delete("Category", selection, selectionArgs)
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                db.delete("Category", "id = ?", arrayOf(categoryId))
            }
            else -> 0
        }
        deletedRows
    } ?: 0


    /*
    update()更新ContentProvider中已有的数据。uri参数用于确定更新哪一张表中的数
    据，新数据保存在values参数中，selection和selectionArgs参数用于约束更新哪些行，
    受影响的行数将作为返回值返回。
     */
    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?) = dbHelper?.let {
        // 更新数据
        val db = it.writableDatabase
        val updatedRows = when (uriMatcher.match(uri)) {
            bookDir -> db.update("Book", values, selection, selectionArgs)
            bookItem -> {
                val bookId = uri.pathSegments[1]
                db.update("Book", values, "id = ?", arrayOf(bookId))
            }
            categoryDir -> db.update("Category", values, selection, selectionArgs)
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                db.update("Category", values, "id = ?", arrayOf(categoryId))
            }
            else -> 0
        }
        updatedRows
    } ?: 0

}