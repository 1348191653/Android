package com.example.app_329;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgBtnPhone=(ImageView)findViewById(R.id.img_btn_phone);
        ImageView imagBtnSms=(ImageView)findViewById(R.id.img_btn_sms);
        ImageView imgBtnUrl=(ImageView)findViewById(R.id.img_btn_internet);
        ImageView imgBtnMusic=(ImageView)findViewById(R.id.img_btn_music);
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_phone=(TextView)findViewById(R.id.tv_phone);
                String funcStr=tv_phone.getText().toString();
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",funcStr);
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this,PhoneActivity.class);
                startActivity(intent);
            }
        });
        imagBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_sms=(TextView)findViewById(R.id.tv_sms);
                String funcStr=tv_sms.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("title",funcStr);
                intent.setClass(MainActivity.this,SmsActivity.class);
                startActivity(intent);
            }
        });
        imgBtnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_url=(TextView)findViewById(R.id.tv_url);
                String funcStr=tv_url.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("title",funcStr);
                intent.setClass(MainActivity.this,UrlActivity.class);
                startActivity(intent);
            }
        });
        imgBtnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_music=(TextView)findViewById(R.id.tv_music);
                String funcStr=tv_music.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("title",funcStr);
                intent.setClass(MainActivity.this,MusicActivity.class);
                startActivity(intent);
            }
        });
    }
}
