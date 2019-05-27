package com.example.handler_timer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Message;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final  int begin=1;
    public static final   int res=3;
    public boolean flag=true;
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

    private Handler handler=new Handler(){
        public  void handleMessage(Message message){
            switch (message.what){
                case begin:
                    time.setText(""+String.format("%02d",minutes)+":"+String.format("%02d",seconds)+":"+String.format("%03d",millis));
                   break;
                case res:
                    time.setText("00:00:00");
                    break;

                   default:
                       break;
            }
        }
    };


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
        start.setOnClickListener(MainActivity.this);
        reset.setOnClickListener(MainActivity.this);
        stop.setOnClickListener(MainActivity.this);
        save.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                startTime = SystemClock.uptimeMillis();
                flag=true;
                start.setEnabled(false);
                reset.setEnabled(false);
               new Thread(new Runnable() {
               @Override
               public void run() {
                   if(flag) {
                       miliTime = SystemClock.uptimeMillis() - startTime;
                       elapseTime = pauseTime + miliTime;
                       seconds = (int) (elapseTime / 1000);
                       minutes = seconds / 60;
                       seconds = seconds % 60;
                       millis = (int) (elapseTime % 1000);
                       Message message = new Message();
                       message.what = begin;
                       handler.sendMessage(message);
                       //System.out.println("..................................");
                   }
                   else {
                       Message message = new Message();
                       message.what = begin;
                       handler.sendMessage(message);
                   }
                   //间隔0s调用此线程
                   handler.postDelayed(this,0);
               }
           }
           ).start();

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
                Message message=new Message();
                message.what=3;
                handler.sendMessage(message);
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
}
