package com.example.musicbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class  ViewHolder{
      public ImageView itemIcon;
      public TextView itemMusicName;
      public TextView itemMusicSinger;
      public int defaultTextColor;
      View itemView;
      public ViewHolder(View itemView){
          if(itemView==null){
              throw new IllegalArgumentException("itemView can not be null");
          }
          this.itemView=itemView;
          itemIcon=(ImageView)itemView.findViewById(R.id.rand_icon);
          itemMusicName=(TextView)itemView.findViewById(R.id.item_music_name);
          itemMusicSinger=(TextView)itemView.findViewById(R.id.item_music_singer);
          defaultTextColor=itemMusicName.getCurrentTextColor();
      }
}
public class ListAdapter extends BaseAdapter{
    private List<MusicInfo>musicList;
    private LayoutInflater layoutInflater;
    private Context context;
    private int currentPos=-1;
    private ViewHolder holder=null;
    public ListAdapter(Context context,List<MusicInfo>musicList){
        this.musicList=musicList;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    public void setFocusItemPos(int pos){
        currentPos=pos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicList.get(i).getMusicTitle();
    }

    @Override
    public long getItemId(int i) {
         return i;
    }

    public void remove(int index){
        musicList.remove(index);
    }
    public void refreshDataSet(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=layoutInflater.inflate(R.layout.item_layout,null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder)view.getTag();
        }
        if (i==currentPos){
            holder.itemIcon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.arrow));
            holder.itemMusicName.setTextColor(Color.RED);
            holder.itemMusicSinger.setTextColor(Color.RED);
        }
        else {
            holder.itemIcon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.music));
            holder.itemMusicName.setTextColor(holder.defaultTextColor);
            holder.itemMusicSinger.setTextColor(holder.defaultTextColor);
        }
        holder.itemMusicName.setText(musicList.get(i).getMusicTitle());
        holder.itemMusicSinger.setText(musicList.get(i).getMusicArtist());
        return view;
    }
}
