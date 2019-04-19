package com.example.app_329;

//import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class music_class extends Fragment {
    private ListView listview;
    private List musicList;
    private ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         View musicLayout=inflater.inflate(R.layout.activity_music,container,false);
        Intent intent=getActivity().getIntent();
        String title=intent.getStringExtra("title");
        getActivity().setTitle(title);
        loadMusicList();
        listview=(ListView) musicLayout.findViewById(R.id.music_list);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,musicList);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String musicName=listview.getItemAtPosition(position).toString();
                PlayMusic(musicName);
            }
        });
         return musicLayout;
    }
    public void PlayMusic(String fileName) {
        String internalSdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator;
        String fileFullPath = internalSdcard + fileName;
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            File file = new File(fileFullPath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("ERROR", "startActivity Error");
            }
        }
    }
    public void loadMusicList(){
        String internalSdcard=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        File directory=new File(internalSdcard);
        File[] files=directory.listFiles();
        if(files.length>0){
            musicList=new ArrayList<String>();
            for(int i=0;i<files.length;i++){
                musicList.add(files[i].getName());

            }
        }
        Log.e("music",""+musicList.size());
    }
}
