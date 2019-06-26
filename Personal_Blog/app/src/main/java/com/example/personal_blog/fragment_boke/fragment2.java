package com.example.personal_blog.fragment_boke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.personal_blog.R;

import java.util.List;

public class fragment2 extends Fragment {
    private String writer_name = "steve";

    private ImageView iv_userImg;
    private ImageView iv_addBlog;
    private TextView tv_allBlog;
    private Spinner sp_CateBlog;
    private TextView tv_PublishBlog;
    private TextView tv_SketchBlog;
    private ListView lv_ListBlog;

    //blog list
    private List<BlogInfo> blog_list;
    private BlogListViewAdapter blogAdapter;

    //cate list
    private List<String> cate_List;
    private ArrayAdapter<String> cateAdapter;
    private String choose_cate;

    //handler
    private Handler handler;
    //记录显示类别
    //1表示列出所有博客，2表示按分类列出，3表示已发布博客，4表示博客草稿
    private int list_type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        ReadBlogFromDB();
        ReadCateFromDB();
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);


        Init();
        ReadBlogFromDB();
        ReadCateFromDB();

        //Init handler
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch(msg.what){
                    case 1:
                        //配置Blog的ListView
                        blogAdapter = new BlogListViewAdapter(getContext(),blog_list);
                        lv_ListBlog.setAdapter(blogAdapter);
                        break;
                    case 2:
                        //配置分类的Spinner
                        cateAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,cate_List);
                        //设置样式
                        cateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //加载适配器
                        sp_CateBlog.setAdapter(cateAdapter);
                    default:break;
                }
            }
        };
    }

    public void Init(){
        //绑定控件
        iv_userImg = (ImageView)getView().findViewById(R.id.user_img);
        iv_addBlog = (ImageView)getView().findViewById(R.id.add_blog);
        tv_allBlog = (TextView)getView().findViewById(R.id.all_blog);
        sp_CateBlog = (Spinner)getView().findViewById(R.id.cate_blog);
        tv_PublishBlog = (TextView)getView().findViewById(R.id.publish_blog);
        tv_SketchBlog = (TextView)getView().findViewById(R.id.sketch_blog);
        lv_ListBlog = (ListView)getView().findViewById(R.id.blog_listview);

//        blog_list = new ArrayList<BlogInfo>();
//        blogAdapter = new BlogListViewAdapter(getContext(),blog_list);
//        sp_CateBlog.setAdapter(blogAdapter);

        //Set OnClick Listener
        //新建博客的监听
        iv_addBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),WriteBlogActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        //选择文章总数监听
        tv_allBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_type = 1;
                ReadBlogFromDB();
            }
        });

        //下拉列表监听
        sp_CateBlog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    choose_cate = cate_List.get(position);
                    list_type = 2;
                    ReadBlogFromDB();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //已发布点击监听
        tv_PublishBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_type = 3;
                ReadBlogFromDB();
            }
        });

        //草稿点击监听
        tv_SketchBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_type = 4;
                ReadBlogFromDB();
            }
        });

        //BlogList点击某一项的监听
        lv_ListBlog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlogInfo bi = blog_list.get(position);
                Intent intent = new Intent(getContext(),WriteBlogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("blog",bi);
                intent.putExtra("type",2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //长按BlogList删除的监听
        lv_ListBlog.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final long blog_id = blog_list.get(position).getId();
               new AlertDialog.Builder(getContext())
                       .setTitle("警告")
                       .setMessage("是否删除该条数据？")
                       .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               BlogDataBase bdb = new BlogDataBase(getContext());
                               SQLiteDatabase sqLiteDatabase = bdb.getWritableDatabase();
                               bdb.DeleteBlog(sqLiteDatabase,blog_id);
                               ReadBlogFromDB();
                           }
                       })
                       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .create().show();
                return true;
            }
        });

    }

    public void ReadBlogFromDB(){
        new Thread(){
            @Override
            public void run(){
                BlogDataBase bdb = new BlogDataBase(getContext());
                SQLiteDatabase sqLiteDatabase = bdb.getReadableDatabase();
                switch(list_type){
                    case 1:
                        blog_list = bdb.QueryBlog(sqLiteDatabase,"Writer=?",
                                new String[]{writer_name});
                        break;
                    case 2:
                        blog_list = bdb.QueryBlog(sqLiteDatabase,"Writer=? and Category=?",
                                new String[]{writer_name,choose_cate});
                        break;
                    case 3:
                        blog_list = bdb.QueryBlog(sqLiteDatabase,"Writer=? and State=?",
                                new String[]{writer_name,BlogInfo.PUBLISH});
                        break;
                    case 4:blog_list = bdb.QueryBlog(sqLiteDatabase,"Writer=? and State=?",
                            new String[]{writer_name,BlogInfo.SKETCH});
                        break;
                    default:
                        break;
                }

                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    public void ReadCateFromDB(){
        new Thread(){
            @Override
            public void run(){
                BlogDataBase bdb = new BlogDataBase(getContext());
                SQLiteDatabase sqLiteDatabase = bdb.getReadableDatabase();
                cate_List = bdb.QueryCate(sqLiteDatabase);
                cate_List.add(0,"");
                handler.sendEmptyMessage(2);
            }
        }.start();
    }

}
