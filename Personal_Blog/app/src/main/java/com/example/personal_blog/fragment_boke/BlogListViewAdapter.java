package com.example.personal_blog.fragment_boke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.personal_blog.R;

import java.util.List;

public class BlogListViewAdapter extends BaseAdapter {
    private Context context;
    private List<BlogInfo> list;

    public BlogListViewAdapter(Context context, List<BlogInfo> list){
        this.context = context;
        this.list = list;
    }

    static class ViewHolder{
        TextView nameTV;
        TextView timeTV;
    }

    @Override
    public int getCount(){
        //返回数据总数
        return list.size();
    }

    @Override
    public Object getItem(int position){
        //返回在list中指定位置的数据的内容
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        //返回数据在list中所在的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        BlogInfo bi = list.get(position);
        final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.blog_list_view,null);
            holder = new ViewHolder();
            holder.nameTV = (TextView)convertView.findViewById(R.id.blog_list_view_name);
            holder.timeTV = (TextView)convertView.findViewById(R.id.blog_list_view_time);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.nameTV.setText(bi.getTitle());
        holder.timeTV.setText(bi.getTime());
        return convertView;
    }
}
