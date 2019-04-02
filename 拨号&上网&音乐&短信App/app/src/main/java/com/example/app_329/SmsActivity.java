package com.example.app_329;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Intent intent=this.getIntent();
        String title=intent.getStringExtra("title");
        this.setTitle(title);
        ImageView btnStartSend=(ImageView)findViewById(R.id.btn_start_send);
        btnStartSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText smsPhoneNum=(EditText)findViewById(R.id.edit_sms_num);
                EditText editSmsContent=(EditText)findViewById(R.id.edit_sms_content);
                String smsNum=smsPhoneNum.getText().toString();
                String smsContent=editSmsContent.getText().toString();

                Uri uri=Uri.parse("smsto:"+smsNum);
                Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
                intent.putExtra("sms_body",smsContent);
                startActivity(intent);
            }
        });
    }
}
