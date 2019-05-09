package com.example.musicbox;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Handler;
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

private MusicManager musicManager=null;
private boolean isPlaying=false;

private Handler handler=new Handler();
private Runnable updateThread=new Runnable() {
    @Override
    public void run() {
        if(musicManager!=null){
            try{
                if(musicManager.isPlaying()){
                    int Duration=musicManager.getDuration();
                    int currentPos=musicManager.getCurrentPosition();
                    musicSeekBar.setMax(Duration);
                    musicSeekBar.setProgress(currentPos);
                    //将当前进度与总进度转换为秒进行比较
                    //目的是避免稍微的毫秒误差导致无法满足条件
                    int prg_see=currentPos/1000;
                    int max_see=Duration/1000;
                    if(prg_see==max_see){
                        musicManager.PlayNext();
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
    int index=musicManager.getCurrentIndex();
    mlistAdapter.setFocusItemPos(musicManager.getCurrentIndex());
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

        musicManager=new MusicManager(MainActivity.this);

        musicList=musicManager.getMusicList();
        String title=getResources().getString(R.string.title_string).toString();
        title=title+"(总数： "+musicManager.getTotalMusic()+")";
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
               musicManager.setCurrentIndex(i);
               mlistAdapter.setFocusItemPos(i);
               musicManager.Play();
               updateState();
           }
       });
       btnPrevious.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               musicManager.PlayPrev();
               mlistAdapter.setFocusItemPos(musicManager.getCurrentIndex());
               updateState();
           }
       });
       btnNext.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               musicManager.PlayNext();
               mlistAdapter.setFocusItemPos(musicManager.getCurrentIndex());
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
                   musicManager.Pause();
                   return ;
               }
               if(isPlaying==false){
                   if(musicManager.getCurrentIndex()==-1){
                       musicManager.setCurrentIndex(0);
                       mlistAdapter.setFocusItemPos(0);
                       musicManager.Play();
                       updateState();
                   }
                   btnPlay.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pause));
                   isPlaying=true;
                   musicManager.Resume();
               }
           }
       });
       musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {}
           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {
                  if(musicManager !=null){
                      try{
                          musicManager.seekTo(seekBar.getProgress());
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
            musicManager.Release();
            finish();
        }
    }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    }).create().show();
    super.onBackPressed();
    }
}
