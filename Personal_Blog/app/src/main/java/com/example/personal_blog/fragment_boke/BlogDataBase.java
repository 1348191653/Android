package com.example.personal_blog.fragment_boke;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BlogDataBase extends SQLiteOpenHelper {
    //在这个博客数据库中，有两个表，一个存储博客信息，一个存储分类
    private static final String BlogTableName = "Blog";
    private static final String CateTableName = "Category";

    //博客信息表的列表名
    private static String col_title = "Title";
    private static String col_content = "Content";
    private static String col_time = "Time";
    private static String col_writer = "Writer";
    private static String col_state = "State";
    private static String col_cate = "Category";
    //分类表的列表名
    private static String col_cate_name = "Cate_Name";

    public BlogDataBase(Context context){
        super(context,"Blog_db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //建博客信息表语句
        String sql1 = "create table " + BlogTableName +
                "(id integer primary key autoincrement," +
                col_title + " text," +
                col_content + " text," +
                col_time + " text," +
                col_writer + " text," +
                col_state + " text," +
                col_cate + " text)";
        sqLiteDatabase.execSQL(sql1);
        //建分类信息表语句
        String sql2 = "create table " + CateTableName +
                "(id integer primary key autoincrement," +
                col_cate_name + " text unique)";
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldVersion,int newVersion){
        //无需更新
    }

    //博客信息表的增删改查
    //增
    public long InsertBlog(SQLiteDatabase sqLiteDatabase, BlogInfo blogInfo){
        long back_id;
        ContentValues values = new ContentValues();
        values.put(col_title,blogInfo.getTitle());
        values.put(col_content,blogInfo.getContent());
        values.put(col_time,blogInfo.getTime());
        values.put(col_writer,blogInfo.getWriter());
        values.put(col_state,blogInfo.getState());
        values.put(col_cate,blogInfo.getCategory());
        back_id = sqLiteDatabase.insert(BlogTableName,null,values);
        sqLiteDatabase.close();
        return back_id;
    }
    //删
    public void DeleteBlog(SQLiteDatabase sqLiteDatabase, long id){
        sqLiteDatabase.delete(BlogTableName,"id=?", new String[]{id + ""});
        sqLiteDatabase.close();
    }
    //改
    public void UpdateBlog(SQLiteDatabase sqLiteDatabase,BlogInfo blogInfo){
        long id = blogInfo.getId();
        ContentValues values = new ContentValues();
        values.put(col_title,blogInfo.getTitle());
        values.put(col_content,blogInfo.getContent());
        values.put(col_time,blogInfo.getTime());
        values.put(col_writer,blogInfo.getWriter());
        values.put(col_state,blogInfo.getState());
        values.put(col_cate,blogInfo.getCategory());

        sqLiteDatabase.update(BlogTableName,values,"id=?",
                new String[]{id + ""});
        sqLiteDatabase.close();
    }
    //查
    public List<BlogInfo> QueryBlog(SQLiteDatabase sqLiteDatabase,String selection,
                                    String[] selectionArgs ){
        Cursor cursor = sqLiteDatabase.query(BlogTableName,null, selection,
                selectionArgs,null,null, "id DESC");
        List<BlogInfo> list = new ArrayList<BlogInfo>();
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex(col_title));
            String content = cursor.getString(cursor.getColumnIndex(col_content));
            String time = cursor.getString(cursor.getColumnIndex(col_time));
            String writer = cursor.getString(cursor.getColumnIndex(col_writer));
            String state = cursor.getString(cursor.getColumnIndex(col_state));
            String cate = cursor.getString(cursor.getColumnIndex(col_cate));
            BlogInfo blogInfo = new BlogInfo(id,title,content,time,writer,state,cate);
            list.add(blogInfo);
        }

        sqLiteDatabase.close();
        cursor.close();
        return list;
    }

    //分类表的增删改查
    //增
    public long InsertCate(SQLiteDatabase sqLiteDatabase, String cate_name){
        long back_id;
        ContentValues values = new ContentValues();
        values.put(col_cate_name,cate_name);
        back_id = sqLiteDatabase.insert(CateTableName,null,values);
        return back_id;
    }
    //删
    public void DeleteCate(SQLiteDatabase sqLiteDatabase, String cate_name){
        sqLiteDatabase.delete(CateTableName,col_cate_name + "=?",
                new String[]{cate_name});
        sqLiteDatabase.close();
    }
    //改
    public void UpdateCate(SQLiteDatabase sqLiteDatabase,long id){
        //不需要用到改。可以直接删除原数据然后添加新数据
    }
    //查，返回所有数据
    public List<String> QueryCate(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query(CateTableName,null,null,null,null,
        null,"id ASC");
        List<String> list = new ArrayList<String>();
        while(cursor.moveToNext()){
            String c = cursor.getString(cursor.getColumnIndex(col_cate_name));
            list.add(c);
        }

        sqLiteDatabase.close();
        cursor.close();
        return list;
    }

}
