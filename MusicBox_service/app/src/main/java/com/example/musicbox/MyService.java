package com.example.musicbox;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public MediaPlayer  mPlayer;
    private Context context;
    private int seekLength=0;
    private int currentIndex=-1;
    private int total_music;
    private List<MusicInfo> musicList=new ArrayList<MusicInfo>();
    public MyService(Context context) {
        this.context=context;
//        mPlayer=new MediaPlayer();
        ResolveMusicToList();
        InitPlayer();
    }
public MyBinder binder=new MyBinder();

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }


    public void InitPlayer(){
        mPlayer=new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setLooping(true);

    }
    public void  ResolveMusicToList (){
        //查询条件
        String selection= MediaStore.Audio.Media.IS_MUSIC+"!=0";
        //按照显示名排序
        String sortOrder=MediaStore.MediaColumns.DISPLAY_NAME+"";
        //查询的列名
        String[]projection={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        //利用ContentResolve得到用ContentPovider接口封装的开放数据
        ContentResolver contentResolver=context.getContentResolver();
        Cursor cursor=contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,selection,null,sortOrder
        );
        if(cursor!=null){
            //得到查询的音乐文件总数
            total_music=cursor.getCount();
            //遍历游标的每一条数据，按照musicInfo的方式存储，并添加到list中
            for(cursor.moveToFirst();cursor.isAfterLast()!=true;cursor.moveToNext()){
                MusicInfo mInfo=new MusicInfo();
                mInfo.setMusicTitle(cursor.getString(0));
                mInfo.setMusicArtist(cursor.getString(1));
                mInfo.setMusicName(cursor.getString(2));
                mInfo.setMusicPath(cursor.getString(3));
                mInfo.setMusicDuration(Integer.parseInt(cursor.getString(4)));
                //将当前数据添加到List当中
                musicList.add(mInfo);
            }
        }
    }
    public void setCurrentIndex(int index){currentIndex=index;}
        public int getCurrentIndex(){return currentIndex;}
    public int getTotalMusic(){return total_music;}
    public List<MusicInfo>getMusicList(){return musicList;}
    public void Release(){
        mPlayer.reset();
        mPlayer.stop();
        mPlayer.release();
    }
    public void Pause(){
        if(mPlayer.isPlaying()){
            mPlayer.pause();
            seekLength=mPlayer.getCurrentPosition();
        }
    }
    public void Resume(){
        mPlayer.seekTo(seekLength);
        mPlayer.start();
    }
    public void Play(){
        mPlayer.reset();
        Uri path=Uri.parse(musicList.get(currentIndex).getMusicPath());
        try{
            mPlayer.setDataSource(String.valueOf(path));
            mPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
        mPlayer.seekTo(seekLength);
        mPlayer.start();
    }
    void PlayNext(){
        currentIndex=currentIndex+1;
        if(currentIndex>=musicList.size()){
            currentIndex=0;
        }
        seekLength=0;
        if(mPlayer.isPlaying()){
            Play();
        }
    }
    void PlayPrev(){
        currentIndex=currentIndex-1;
        if(currentIndex<0){
            currentIndex=musicList.size()-1;
        }
        seekLength=0;
        if(mPlayer.isPlaying()){
            Play();
        }
    }
    boolean isPlaying(){
        return mPlayer.isPlaying();
    }
    int getDuration(){
        return mPlayer.getDuration();
    }
    int getCurrentPosition(){
        return mPlayer.getCurrentPosition();
    }
    void seekTo(int length){
        seekLength=length;
        mPlayer.seekTo(length);
    }
    MusicInfo getMusicInfo(int index){
        return musicList.get(index);
    }
}
