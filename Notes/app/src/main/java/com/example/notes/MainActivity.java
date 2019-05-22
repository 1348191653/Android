package com.example.notes;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     Button create;
    ListView listview;
    LayoutInflater inflater;
    ArrayList<Data> array;
    MyDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=(ListView)findViewById(R.id.lv_bwlList);
        create=(Button)findViewById(R.id.Create);
        inflater=getLayoutInflater();
        db=new MyDataBase(this);
        array=db.getArray();
        final MyAdapter adapter=new MyAdapter(inflater,array);
        listview.setAdapter(adapter);

        //点击item修改日记
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getApplicationContext(),Create_Nodes.class);
                intent.putExtra("ids",array.get(position).getIds());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        //删除日记（长按)
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               new AlertDialog.Builder(MainActivity.this)
                       .setTitle("删除")
                       .setMessage("是否删除笔记？")
                       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               db.toDelete(array.get(position).getIds());
                               array=db.getArray();
                               MyAdapter adapter1=new MyAdapter(inflater,array);
                               listview.setAdapter(adapter1);
                           }
                       }).create().show();
               return true;
            }
        });
        //新建日记
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Create_Nodes.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

}
