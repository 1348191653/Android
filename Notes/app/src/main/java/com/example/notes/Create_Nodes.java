package com.example.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Create_Nodes extends AppCompatActivity {
 EditText ed1,ed2;
 Button save;
 Button ret;
 MyDataBase myDataBase;
 Data data;
 int ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        ed1=(EditText)findViewById(R.id.editText1);
        ed2=(EditText)findViewById(R.id.editText2);
        save=(Button)findViewById(R.id.save);
        ret=(Button)findViewById(R.id.ret);
        myDataBase=new MyDataBase(this);
        Intent intent=this.getIntent();
        ids=intent.getIntExtra("ids",0);
        //默认为0，如果不为0则是修改数据跳转过来的
        if(ids!=0){
            data=myDataBase.getTianCon(ids);
            ed1.setText(data.getTitle());
            ed2.setText(data.getContent());
        }
        //保存按钮事件
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSave();
            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
                Date curDate=new Date(System.currentTimeMillis()) ;
                String times=formatter.format(curDate);
                String title=ed1.getText().toString();
                String content=ed2.getText().toString();
                //是修改数据
                if(ids!=0){
                    data=new Data(title,ids,content,times);
                    myDataBase.toUpdate(data);
                    Intent intent1=new Intent(Create_Nodes.this,MainActivity.class);
                    startActivity(intent1);
                    Create_Nodes.this.finish();
                }
                else{
                    if(title.equals("")&&content.equals("")){
                        Intent intent1=new Intent(Create_Nodes.this,MainActivity.class);
                        startActivity(intent1);
                        Create_Nodes.this.finish();
                    }
                    else{
                        data=new Data(title,content,times);
                        myDataBase.toInsert(data);
                        Intent intent1=new Intent(Create_Nodes.this,MainActivity.class);
                        startActivity(intent1);
                        Create_Nodes.this.finish();
                    }
                }
            }
        });
    }



    private void isSave(){
        SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String times=format.format(curDate);
        String title=ed1.getText().toString();
        String content=ed2.getText().toString();

        //是要修改数据
        if(ids!=0){
            data=new Data(title,content,times);
            myDataBase.toUpdate(data);
            Intent intent=new Intent(Create_Nodes.this,MainActivity.class);
            startActivity(intent);
            Create_Nodes.this.finish();
        }
        //新建日记
        else{
            data=new Data(title,content,times);
            myDataBase.toInsert(data);
            Intent intent=new Intent(Create_Nodes.this,MainActivity.class);
            startActivity(intent);
            Create_Nodes.this.finish();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.ac
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
