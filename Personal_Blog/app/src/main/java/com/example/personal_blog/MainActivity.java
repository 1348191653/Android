package com.example.personal_blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personal_blog.fragment_boke.fragment2;
import com.example.personal_blog.fragment_geren.Info_center;
import com.example.personal_blog.fragment_geren.fragment3;
import com.example.personal_blog.fragment_shouye.fragment1;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //user_name
    private String user_name;

    private fragment1 souye_fragment;
    private fragment2 boke_fragment;
    private com.example.personal_blog.fragment_geren.fragment3 geren_fragment;
    private ImageView souye_imageview;
    private  ImageView boke_imageview;
    private  ImageView geren_imageview;
    private TextView souye_textview;
    private  TextView boke_textview;
    private  TextView geren_textview;
    private FragmentManager fragmentManager;

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitViews();
        fragmentManager=getSupportFragmentManager();
        setTabSelection(0);
        setSelected(0);
    }


    public void InitViews()
    {
        souye_imageview=(ImageView)findViewById(R.id.souye_img);
        souye_textview=(TextView)findViewById(R.id.souye_tv);
        boke_imageview=(ImageView)findViewById(R.id.boke_img);
        boke_textview=(TextView)findViewById(R.id.boke_tv);
        geren_imageview=(ImageView)findViewById(R.id.geren_img);
        geren_textview=(TextView)findViewById(R.id.geren_tv);

        souye_imageview.setOnClickListener(this);
        boke_imageview.setOnClickListener(this);
        geren_imageview.setOnClickListener(this);

        //get user_name
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.souye_img:
                setTabSelection(0);
                setSelected(0);
                break;
            case R.id.boke_img:
                setTabSelection(1);
                setSelected(1);
                break;
            case R.id.geren_img:
                setTabSelection(2);
                setSelected(2);
                break;
            default:
                break;
        }
    }


    private void setTabSelection(int index){
        clearSelection();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragment(transaction);
        initSelected();
        switch (index){
            case 0:
                if(souye_fragment==null){

                    souye_fragment=new fragment1();
//                    transaction.add(R.id.content, souye_fragment);
                    transaction.add(R.id.content,souye_fragment);
                }else {
                    transaction.show(souye_fragment);
                }
//
                break;

            case 1:
                if( boke_fragment==null){
                    boke_fragment=new fragment2();
                    transaction.add(R.id.content,boke_fragment);
                }else {
                    transaction.show(boke_fragment);
                }
                break;

            case 2:
                if( geren_fragment==null){
                    geren_fragment=new fragment3();
                    transaction.add(R.id.content,geren_fragment);
                }else{
                    transaction.show(geren_fragment);
                }
                Intent intent=new Intent(MainActivity.this, Info_center.class);
                startActivity(intent);
                break;

        }
        transaction.commit();
    }

    private void clearSelection(){
    }
    private void setSelected(int index){
        switch (index){
            case 0:
                souye_imageview.setSelected(true);
                boke_imageview.setSelected(false);
                geren_imageview.setSelected(false);
                souye_textview.setSelected(true);
                boke_textview.setSelected(false);
                geren_textview.setSelected(false);
                break;
            case 1:
                souye_imageview.setSelected(false);
                boke_imageview.setSelected(true);
                geren_imageview.setSelected(false);
                souye_textview.setSelected(false);
                boke_textview.setSelected(true);
                geren_textview.setSelected(false);
                break;
            case 2:
                souye_imageview.setSelected(false);
                boke_imageview.setSelected(false);
                geren_imageview.setSelected(true);
                souye_textview.setSelected(false);
                boke_textview.setSelected(false);
                geren_textview.setSelected(true);
                        }
    }
    private void initSelected(){
        souye_imageview.setSelected(false);
        boke_imageview.setSelected(false);
        geren_imageview.setSelected(false);
        souye_textview.setSelected(false);
        boke_textview.setSelected(false);
        geren_textview.setSelected(false);
    }
    private void hideFragment(FragmentTransaction transaction){
        if( souye_fragment!=null){
            transaction.hide(souye_fragment);
        }
        if(boke_fragment!=null){
            transaction.hide(boke_fragment);
        }
        if( geren_fragment!=null){
            transaction.hide(geren_fragment);
        }
    }

}
