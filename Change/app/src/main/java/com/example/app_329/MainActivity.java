package com.example.app_329;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends  AppCompatActivity implements View.OnClickListener {
     //fragment
     private message_class messageFragment;
     private call_class callFragment;
     private music_class musicFragment;
     private  browse_class browseFragment;

     //view


    //导航栏显示图标
    private ImageView messageImage;
    private  ImageView callImage;
    private  ImageView musicImage;
    private  ImageView browseImage;

    //导航栏显示文字
    private TextView messageText;
    private TextView callText;
    private  TextView musicText;
    private  TextView browseText;


    //对Fragment进行管理
    private  FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
//          View  messageLayout=(View)findViewById(R.layout.activity_sms);
//          View  callLayout;
//          View musicLayout;
//          View browseLayout;
         messageImage=(ImageView)findViewById(R.id.message_image);
         callImage=(ImageView)findViewById(R.id.contacts_image);
         musicImage=(ImageView)findViewById(R.id.music_image);
         browseImage=(ImageView)findViewById(R.id.browse_image);
        initViews();
        fragmentManager=getSupportFragmentManager();
        setTabSelection(0);
    }
    private void initViews(){
//         ImageView imgBtnPhone=(ImageView)findViewById(R.id.img_btn_phone);
//         ImageView imagBtnSms=(ImageView)findViewById(R.id.img_btn_sms);
//         ImageView imgBtnUrl=(ImageView)findViewById(R.id.img_btn_internet);
//         ImageView imgBtnMusic=(ImageView)findViewById(R.id.img_btn_music);
         messageImage.setOnClickListener(this);
         callImage.setOnClickListener(this);
         musicImage.setOnClickListener(this);
         browseImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.message_image:
               setTabSelection(0);
               break;
           case R.id.contacts_image:
               setTabSelection(1);
               break;
           case R.id.music_image:
               setTabSelection(2);
               break;
           case R.id.browse_image:
               setTabSelection(3);
               break;
               default:
                   break;
       }
    }
    private void setTabSelection(int index){
      clearSelection();
      FragmentTransaction transaction=fragmentManager.beginTransaction();
      hideFragment(transaction);
      switch (index){
          case 0:
              if(messageFragment==null){
                  messageFragment=new message_class();
                  transaction.add(R.id.content,messageFragment);
              }else {
                  transaction.show(messageFragment);
              }
              break;
          case 1:
              if(callFragment==null){
                  callFragment=new call_class();
                  transaction.add(R.id.content,callFragment);
              }else{
                  transaction.show(callFragment);
              }
              break;
          case 2:
              if(musicFragment==null){
                  musicFragment=new music_class();
                  transaction.add(R.id.content,musicFragment);
              }else {
                  transaction.show(musicFragment);
              }
              break;
          case 3:
              if(browseFragment==null){
                  browseFragment=new browse_class();
                  transaction.add(R.id.content,browseFragment);
              }else {
                  transaction.show(browseFragment);
              }
              break;
      }
      transaction.commit();
    }
    private void clearSelection(){

    }
    private void hideFragment(FragmentTransaction transaction){
       if(messageFragment!=null){
           transaction.hide(messageFragment);
       }
       if(callFragment!=null){
           transaction.hide(callFragment);
       }
       if(musicFragment!=null){
           transaction.hide(musicFragment);
       }
       if (browseFragment!=null){
           transaction.hide(browseFragment);
       }
    }
//public class MainActivity extends  AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ImageView imgBtnPhone = (ImageView) findViewById(R.id.img_btn_phone);
//        ImageView imagBtnSms = (ImageView) findViewById(R.id.img_btn_sms);
//        ImageView imgBtnUrl = (ImageView) findViewById(R.id.img_btn_internet);
//        ImageView imgBtnMusic = (ImageView) findViewById(R.id.img_btn_music);
//        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
//                String funcStr = tv_phone.getText().toString();
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("title", funcStr);
//                intent.putExtras(bundle);
//                intent.setClass(MainActivity.this, PhoneActivity.class);
//                startActivity(intent);
//            }
//        });
//        imagBtnSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView tv_sms = (TextView) findViewById(R.id.tv_sms);
//                String funcStr = tv_sms.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtra("title", funcStr);
//                intent.setClass(MainActivity.this, SmsActivity.class);
//                startActivity(intent);
//            }
//        });
//        imgBtnUrl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView tv_url = (TextView) findViewById(R.id.tv_url);
//                String funcStr = tv_url.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtra("title", funcStr);
//                intent.setClass(MainActivity.this, UrlActivity.class);
//                startActivity(intent);
//            }
//        });
//        imgBtnMusic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView tv_music = (TextView) findViewById(R.id.tv_music);
//                String funcStr = tv_music.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtra("title", funcStr);
//                intent.setClass(MainActivity.this, MusicActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}

