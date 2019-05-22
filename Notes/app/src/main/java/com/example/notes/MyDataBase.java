package com.example.notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

public class MyDataBase {
    Context context;
    MyOpenHelper myOpenHelper;
    SQLiteDatabase myDatabase;
    //实例化这个数据库时创建数据库
    public MyDataBase(Context context){
        this.context=context;
        myOpenHelper=new MyOpenHelper(context);
    }
    //得到ListView的数据,从数据库查找并解析
    public ArrayList<Data> getArray(){
        ArrayList<Data> array=new ArrayList<Data>();
        ArrayList<Data> array1=new ArrayList<Data>();
        myDatabase=myOpenHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select ids,title,times from mybook",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id=cursor.getInt(cursor.getColumnIndex("ids"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String times=cursor.getString(cursor.getColumnIndex("times"));
            Data data=new Data(id,title,times);
            array.add(data);
            cursor.moveToNext();
        }
        myDatabase.close();
        for(int i=array.size();i>0;i--){
            array1.add(array.get(i-1));
        }
        return array1;
    }
    //返回修改数据
    public Data getTianCon(int id){
        myDatabase=myOpenHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select title,content from mybook where ids='"+id+"'",null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex("title"));
        String content=cursor.getString(cursor.getColumnIndex("content"));
        Data data=new Data(title,content);
        myDatabase.close();
        return data;
    }
    public void toUpdate(Data data){
        myDatabase = myOpenHelper.getWritableDatabase();
        myDatabase.execSQL(
                "update mybook set title='"+ data.getTitle()+
                        "',times='"+data.getTimes()+
                        "',content='"+data.getContent() +
                        "' where ids='"+ data.getIds()+"'");
        myDatabase.close();
    }
//增加日记
public void toInsert(Data data){
    myDatabase =myOpenHelper.getWritableDatabase();
    myDatabase.execSQL("insert into mybook(title,content,times)values('"
            + data.getTitle()+"','"
            +data.getContent()+"','"
            +data.getTimes()
            +"')");
    myDatabase.close();
}
     //长按点击后选择删除日记
    public void toDelete(int ids){
        myDatabase  = myOpenHelper.getWritableDatabase();
        myDatabase.execSQL("delete from mybook where ids="+ids+"");
        myDatabase.close();
    }
}

