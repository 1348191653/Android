package com.example.timer;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
  private    TextView time=null;
  private ListView lvLaps=null;
  private   Button start=null;
  private    Button stop=null;
  private     Button reset=null;
  private    Button save=null;
  private List<String>lapTimeList;
  private String[]listitems=new String[]{};
  private ArrayAdapter<String>adapter;

  private long miliTime,startTime,pauseTime,elapseTime=0L;
  private int  minutes,seconds,millis;
  private Timer timer;
  public TimerTask timerTask;
  public Runnable runnable;
  private Handler handler;

  private void GetView(){
         time=(TextView)findViewById(R.id.display);
         start=(Button)findViewById(R.id.start);
         stop=(Button)findViewById(R.id.stop);
         reset=(Button)findViewById(R.id.reset);
         save=(Button)findViewById(R.id.save);
         lvLaps=(ListView)findViewById(R.id.item);
         lapTimeList=new ArrayList<String>(Arrays.asList(listitems));
         adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,lapTimeList);
         lvLaps.setAdapter(adapter);
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetView();
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                miliTime=SystemClock.uptimeMillis()-startTime;
                elapseTime=pauseTime+miliTime;
                seconds=(int)(elapseTime/1000);
                minutes=seconds/60;
                seconds=seconds%60;
                millis=(int)(elapseTime%1000);
                time.setText(""+String.format("%02d",minutes)+":"+String.format("%02d",seconds)+":"+String.format("%03d",millis));
                handler.postDelayed(this,0);
            }
        };
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime= SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                //                if(timer!=null){
//                    timer.cancel();
//                }
//                timerTask=new TimerTask() {
////                    @Override
//                    public void run() {
//                        miliTime=SystemClock.uptimeMillis()-startTime;
//                        elapseTime=pauseTime+miliTime;
//                        seconds=(int)(elapseTime/1000);
//                        minutes=seconds/60;
//                        seconds=seconds%60;
//                        millis=(int)(elapseTime%1000);
//                        time.setText(""+String.format("%02d",minutes)+":"+String.format("%02d",seconds)+":"+String.format("%03d",millis));
//                    }
//                };
//                timer=new Timer();
//                timer.scheduleAtFixedRate(timerTask,0,1);
                start.setEnabled(false);
                reset.setEnabled(false);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTime+=miliTime;
//                timer.cancel();
                handler.removeCallbacks(runnable);
                start.setEnabled(true);
                reset.setEnabled(true);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
  save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          lapTimeList.add(time.getText().toString());
          adapter.notifyDataSetChanged();
      }
  });
    }
}
