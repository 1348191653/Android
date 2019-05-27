package com.example.asynctask_timer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView time=null;
    private ListView lvLaps=null;
    private Button start=null;
    private Button stop=null;
    private Button reset=null;
    private Button save=null;
    private List<String> lapTimeList;
    private String[]listitems=new String[]{};
    private ArrayAdapter<String> adapter;

     private long miliTime,startTime,pauseTime,elapseTime=0L;
     private int  minutes,seconds,millis;
     public boolean flag=true;
    private void Init()
    {
        time=(TextView)findViewById(R.id.display);
        start=(Button)findViewById(R.id.start);
        stop=(Button)findViewById(R.id.stop);
        reset=(Button)findViewById(R.id.reset);
        save=(Button)findViewById(R.id.save);
        lvLaps=(ListView)findViewById(R.id.item);
        lapTimeList=new ArrayList<String>(Arrays.asList(listitems));
        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,lapTimeList);
        lvLaps.setAdapter(adapter);

        start.setOnClickListener(MainActivity.this);
        stop.setOnClickListener(MainActivity.this);
        reset.setOnClickListener(MainActivity.this) ;
        save.setOnClickListener(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.start:
                startTime= SystemClock.uptimeMillis();
                flag=true;
                start.setEnabled(false);
                reset.setEnabled(false);
                new MyAsync().execute();
                break;
            case R.id.stop:
                pauseTime+=miliTime;
                flag=false;
                start.setEnabled(true);
                reset.setEnabled(true);
                break;

            case R.id.reset:
                miliTime=0L;
                startTime=0L;
                pauseTime=0L;
                elapseTime=0L;
                seconds=0;
                minutes=0;
                millis=0;
                time.setText("00:00:00");
                lapTimeList.clear();
                adapter.notifyDataSetChanged();
                break;

            case R.id.save:
                lapTimeList.add(time.getText().toString());
                adapter.notifyDataSetChanged();
                break;
                default:
                    break;
        }
    }

    //AsyncTask对象
    class MyAsync extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            while(flag) {
                miliTime = SystemClock.uptimeMillis() - startTime;
                elapseTime = pauseTime + miliTime;
                seconds = (int) (elapseTime / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                millis = (int) (elapseTime % 1000);
               try {
                   //sleep很重要，因为程序执行速度和刷新速度不在一个数量级上所以如果不加会出现跳动
                   Thread.sleep(1);
                   publishProgress();
               }catch (Exception e){
                   e.printStackTrace();
               }

            }
                publishProgress();
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
             time.setText(""+String.format("%02d",minutes)+":"+String.format("%02d",seconds)+":"+String.format("%03d",millis));
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
