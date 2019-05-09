package com.example.musicbox;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private List<MusicInfo>musicList=new ArrayList<MusicInfo>();
private ListAdapter mlistAdapter;
private ListView mListView;

private ImageView btnPrevious;
private ImageView btnNext;
private ImageView btnPlay;

private TextView listTile;
private TextView playingName;
private SeekBar musicSeekBar;

//private MusicManager musicManager=null;
private boolean isPlaying=false;

private  MyService musicService;
private boolean tag1=false;
private  boolean tag2=false;
private  void bindServiceConnection(){
    Intent intent=new Intent(MainActivity.this,MyService.class);
    startService(intent);
    bindService(intent,serviceConnection,this.BIND_AUTO_CREATE);
}
private ServiceConnection serviceConnection=new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        musicService=((MyService.MyBinder)(service)).getService();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
       musicService=null;
    }
};

private Handler handler=new Handler();
private Runnable updateThread=new Runnable() {
    @Override
    public void run() {
        if( musicService!=null){
            try{
                if( musicService.isPlaying()){
                    int Duration= musicService.getDuration();
                    int currentPos= musicService.getCurrentPosition();
                    musicSeekBar.setMax(Duration);
                    musicSeekBar.setProgress(currentPos);
                    //将当前进度与总进度转换为秒进行比较
                    //目的是避免稍微的毫秒误差导致无法满足条件
                    int prg_see=currentPos/1000;
                    int max_see=Duration/1000;
                    if(prg_see==max_see){
                        musicService.PlayNext();
                        updateState();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        handler.post(updateThread);
    }
};
public void updateState(){
    int index= musicService.getCurrentIndex();
    mlistAdapter.setFocusItemPos( musicService.getCurrentIndex());
    String currentMusicName=musicList.get(index).getMusicTitle();
    playingName.setText(currentMusicName);
    btnPlay.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pause));
    isPlaying=true;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listTile=(TextView)findViewById(R.id.music_list_title);
        playingName=(TextView)findViewById(R.id.music_name);
        musicSeekBar=(SeekBar)findViewById(R.id.music_seek_bar);

        btnPrevious=(ImageView)findViewById(R.id.btn_previous);
        btnNext=(ImageView)findViewById(R.id.btn_next);
        btnPlay=(ImageView)findViewById(R.id.btn_play);

//        musicManager=new MusicManager(MainActivity.this);
        musicService=new MyService(MainActivity.this);
        musicList= musicService.getMusicList();
        String title=getResources().getString(R.string.title_string).toString();
        title=title+"(总数： "+ musicService.getTotalMusic()+")";
        listTile.setText(title);
        mlistAdapter=new ListAdapter(MainActivity.this,musicList);
        mListView=(ListView)findViewById(R.id.music_list);
        mListView.setAdapter(mlistAdapter);

        handler.post(updateThread);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListView.showContextMenu();
                return true;
            }
        });
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               musicService.setCurrentIndex(i);
               mlistAdapter.setFocusItemPos(i);
               musicService.Play();
               updateState();
           }
       });
       btnPrevious.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               musicService.PlayPrev();
               mlistAdapter.setFocusItemPos( musicService.getCurrentIndex());
               updateState();
           }
       });
       btnNext.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               musicService.PlayNext();
               mlistAdapter.setFocusItemPos( musicService.getCurrentIndex());
               updateState();
           }
       });
       btnPlay.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               if(isPlaying==true){
                   btnPlay.setImageBitmap(
                           BitmapFactory.decodeResource(getResources(),R.drawable.play)
                   );
                   isPlaying=false;
                   musicService.Pause();
                   return ;
               }
               if(isPlaying==false){
                   if( musicService.getCurrentIndex()==-1){
                       musicService.setCurrentIndex(0);
                       mlistAdapter.setFocusItemPos(0);
                       musicService.Play();
                       updateState();
                   }
                   btnPlay.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pause));
                   isPlaying=true;
                   musicService.Resume();
               }
           }
       });
       musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               if(b==true){
                   musicService.mPlayer.seekTo(i);
               }
           }
           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {}
           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {
                  if( musicService.mPlayer !=null){
                      try{
                          musicService.mPlayer.seekTo(seekBar.getProgress());
                      }catch (Exception e){
                          e.printStackTrace();
                      }
                  }
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    boolean retValue=super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.menu_item,menu);
    return  retValue;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId()==R.id.item_about){
        StringBuilder msgBuilder=new StringBuilder();
        msgBuilder.append("MusicBox v1.0.0\n");
        msgBuilder.append("作者：曾 伟(8000116434)\n");
        msgBuilder.append("(C)2019 南昌大学软件学院");
        String title="关于";
        new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.note).setTitle(title).setMessage(msgBuilder.toString()).setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }
    if(item.getItemId()==R.id.item_exit){
        onBackPressed();
    }
    return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    String title="提示";
    new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.note).setTitle(title).setMessage("确定退出？").setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            musicService.Release();
            handler.removeCallbacks(updateThread);
            unbindService(serviceConnection);
            Intent intent=new Intent(MainActivity.this,MyService.class);
            stopService(intent);
            try {
                MainActivity.this.finish();
            }catch (Exception e){

            }
        }
    }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    }).create().show();
    super.onBackPressed();
    }
}
