package com.example.personal_blog.fragment_boke;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personal_blog.MainActivity;
import com.example.personal_blog.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteBlogActivity extends AppCompatActivity {

    private ImageView iv_Back;
    private TextView tv_blog_main;
    private ImageView iv_Save;
    private Spinner sp_Cate;
    private TextView tv_addCate;
    private TextView tv_deleteCate;
    private TextView tv_Time;
    private EditText et_Title;
    private TextView tv_Title;
    private EditText et_Content;
    private TextView tv_Content;
    private ImageView btn_Publish;

    private BlogInfo blog;
    private String writer_name = "steve";
    //类别list
    private List<String> cate_List;
    private ArrayAdapter<String> cateAdapter;
    private int choose_cate_id;

    //记录时间
    private String choose_cate;
    private String now_time;
    private String str_title;
    private String str_content;
    private String state;

    //判断标志。主要判断这条博客是新建还是修改，防止重复存储同一条数据
    //true表明已插入数据库，以后保存、发布操作都是更新数据
    private boolean isSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_write_blog);

        Init();

        Intent intent = getIntent();
        int type = intent.getIntExtra("type",0);
        if(type == 2){
            isSave = true;
            Bundle bundle = intent.getExtras();
            blog = (BlogInfo) bundle.getSerializable("blog");
            //已发布
            if(blog.getState().equals(BlogInfo.PUBLISH)){
                writer_name = blog.getWriter();
                choose_cate = blog.getCategory();
                str_title = blog.getTitle();
                str_content = blog.getContent();
                now_time = blog.getTime();
                state = blog.getState();

                //隐藏控件，该条博客无法再修改
                iv_Save.setVisibility(View.INVISIBLE);
                sp_Cate.setVisibility(View.INVISIBLE);
                tv_deleteCate.setVisibility(View.INVISIBLE);
                et_Title.setVisibility(View.INVISIBLE);
                et_Content.setVisibility(View.INVISIBLE);
                btn_Publish.setVisibility(View.INVISIBLE);

                //显示控件并设置值
                tv_blog_main.setText("查看博客");
                tv_addCate.setText(choose_cate);
                tv_addCate.setClickable(false);
                tv_Title.setVisibility(View.VISIBLE);
                tv_Title.setText(str_title);
                tv_Content.setVisibility(View.VISIBLE);
                tv_Content.setText(str_content);
                tv_Time.setText(now_time);
            }
            //草稿
            else {
                choose_cate = blog.getCategory();
                str_title = blog.getTitle();
                str_content = blog.getContent();
                now_time = blog.getTime();
                state = blog.getState();
                writer_name = blog.getWriter();

                tv_Time.setText(now_time);
                et_Title.setText(str_title);
                et_Content.setText(str_content);
                for(int i=0;i<cate_List.size();i++){
                    if(choose_cate.equals(cate_List.get(i))){
                        choose_cate_id = i;
                        break;
                    }
                }
                sp_Cate.setSelection(choose_cate_id);

                //隐藏控件
                tv_Title.setVisibility(View.GONE);
                tv_Content.setVisibility(View.GONE);
            }
        }
        else{
            //初始化一些控件
            Date now = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
            now_time = ft.format(now);
            tv_Time.setText(now_time);

            blog = new BlogInfo();

            //隐藏控件
            tv_Title.setVisibility(View.GONE);
            tv_Content.setVisibility(View.GONE);
        }
    }

    public void Init(){
        //绑定控件
        iv_Back = (ImageView)findViewById(R.id.blog_back);
        tv_blog_main = (TextView)findViewById(R.id.blog_main);
        iv_Save = (ImageView)findViewById(R.id.save_blog);
        sp_Cate = (Spinner)findViewById(R.id.blog_cate);
        tv_addCate = (TextView)findViewById(R.id.add_cate);
        tv_deleteCate = (TextView)findViewById(R.id.delete_cate);
        tv_Time = (TextView)findViewById(R.id.blog_time);
        et_Title = (EditText)findViewById(R.id.blog_title);
        tv_Title = (TextView)findViewById(R.id.tv_blog_title);
        et_Content = (EditText)findViewById(R.id.blog_content);
        tv_Content = (TextView)findViewById(R.id.tv_blog_content);
        btn_Publish = (ImageView)findViewById(R.id.btn_publish);

        //设置类别的下拉列表
        BlogDataBase bdb = new BlogDataBase(WriteBlogActivity.this);
        SQLiteDatabase sqLiteDatabase = bdb.getReadableDatabase();
        cate_List = bdb.QueryCate(sqLiteDatabase);
        if(cate_List.size()!=0){
            choose_cate = cate_List.get(0);
            choose_cate_id = 0;
        }
        else{
            choose_cate = "";
            choose_cate_id = -1;
        }
        //适配器
        cateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cate_List);
        //设置样式
        cateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_Cate.setAdapter(cateAdapter);

        //Set OnClick Listener
        //返回到上一个Activity的监听
        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteBlogActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //保存草稿的监听
        iv_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckInput()) {
                    str_title = et_Title.getText().toString();
                    str_content = et_Content.getText().toString();
                    Date now = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                    now_time = ft.format(now);
                    tv_Time.setText(now_time);
                    state = BlogInfo.SKETCH;
                    if(!isSave){
                        InsertBlogToDB();
                    }
                    else{
                        UpdateBlogInDB();
                    }
                    Toast.makeText(WriteBlogActivity.this,"保存成功",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //选择分类下拉列表的监听
        sp_Cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose_cate = parent.getItemAtPosition(position).toString();
                choose_cate_id = position;
                Log.e("Select Cate ",choose_cate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //新建分类的监听
        tv_addCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputCate = new EditText(WriteBlogActivity.this);
                new AlertDialog.Builder(WriteBlogActivity.this)
                        .setView(inputCate)
                        .setTitle("请输入新分类")
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newCate = inputCate.getText().toString();
                                BlogDataBase bdb = new BlogDataBase(WriteBlogActivity.this);
                                SQLiteDatabase sqLiteDatabase = bdb.getWritableDatabase();
                                bdb.InsertCate(sqLiteDatabase,newCate);
                                cate_List.add(newCate);
                                cateAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });

        //删除分类的监听
        tv_deleteCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WriteBlogActivity.this)
                        .setTitle("警告")
                        .setMessage("确定要删除 "+choose_cate+" 这个类别吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BlogDataBase bdb = new BlogDataBase(WriteBlogActivity.this);
                                SQLiteDatabase sqLiteDatabase = bdb.getWritableDatabase();
                                bdb.DeleteCate(sqLiteDatabase,choose_cate);
                                cate_List.remove(choose_cate_id);
                                if(cate_List.size() == 0){
                                    choose_cate = "";
                                    choose_cate_id = -1;
                                }
                                else if(choose_cate_id < cate_List.size()){
                                    choose_cate = cate_List.get(choose_cate_id);
                                }
                                cateAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
        });

        //发布按钮的监听
        btn_Publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckInput()) {
                    str_title = et_Title.getText().toString();
                    str_content = et_Content.getText().toString();
                    Date now = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                    now_time = ft.format(now);
                    tv_Time.setText(now_time);
                    state = BlogInfo.PUBLISH;
                    if(!isSave){
                        InsertBlogToDB();
                    }
                    else{
                        UpdateBlogInDB();
                    }
                    Toast.makeText(WriteBlogActivity.this,"发布成功",
                            Toast.LENGTH_SHORT).show();

                    //隐藏控件，该条博客无法再修改
                    iv_Save.setVisibility(View.INVISIBLE);
                    sp_Cate.setVisibility(View.INVISIBLE);
                    tv_deleteCate.setVisibility(View.INVISIBLE);
                    et_Title.setVisibility(View.INVISIBLE);
                    et_Content.setVisibility(View.INVISIBLE);
                    btn_Publish.setVisibility(View.INVISIBLE);

                    //显示控件并设置值
                    tv_blog_main.setText("查看博客");
                    tv_addCate.setText(choose_cate);
                    tv_addCate.setClickable(false);
                    tv_Title.setVisibility(View.VISIBLE);
                    tv_Title.setText(str_title);
                    tv_Content.setVisibility(View.VISIBLE);
                    tv_Content.setText(str_content);
                }
            }
        });
    }

    //检查保存时或发布时，是否有内容未填
    public boolean CheckInput(){
        if(choose_cate.equals("")){
            Toast.makeText(WriteBlogActivity.this, "请选择分类",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_Title.getText().toString().equals("")){
            Toast.makeText(WriteBlogActivity.this, "请输入标题",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_Content.getText().toString().equals("")){
            Toast.makeText(WriteBlogActivity.this, "请输入内容",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void InsertBlogToDB(){
        new Thread(){
            @Override
            public void run(){
                blog.setTitle(str_title);
                blog.setContent(str_content);
                blog.setTime(now_time);
                blog.setWriter(writer_name);
                blog.setState(state);
                blog.setCategory(choose_cate);
                BlogDataBase bdb = new BlogDataBase(WriteBlogActivity.this);
                SQLiteDatabase sqLiteDatabase = bdb.getWritableDatabase();
                long id = bdb.InsertBlog(sqLiteDatabase,blog);
                blog.setId(id);
                isSave = true;
            }
        }.start();
    }

    public void UpdateBlogInDB(){
        new Thread(){
            @Override
            public void run(){
                blog.setTitle(str_title);
                blog.setContent(str_content);
                blog.setTime(now_time);
                blog.setWriter(writer_name);
                blog.setState(state);
                blog.setCategory(choose_cate);
                BlogDataBase bdb = new BlogDataBase(WriteBlogActivity.this);
                SQLiteDatabase sqLiteDatabase = bdb.getWritableDatabase();
                bdb.UpdateBlog(sqLiteDatabase,blog);
            }
        }.start();
    }

}
