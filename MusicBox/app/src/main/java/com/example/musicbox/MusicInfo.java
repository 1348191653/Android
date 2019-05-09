package com.example.musicbox;

public class MusicInfo {
    private String music_title;
    private String music_name;
    private String music_path;
    private String music_artist;
    private int music_duration;
    public void setMusicTitle(String title){music_title=title;}
    public String getMusicTitle(){return music_title;}
    public void setMusicName(String name){music_name=name;}
    public String getMusicName(){return music_name;}
    public void setMusicPath(String path){music_path=path;}
    public String getMusicPath(){return music_path;}
    public void  setMusicArtist(String artist){music_artist=artist;}
    public String getMusicArtist(){return music_artist;}
    public void setMusicDuration(int duration){music_duration=duration;}
    public int getMusicDuration(){return music_duration;}
}
