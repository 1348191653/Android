package com.example.app_329;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class PhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        String title=bundle.getString("title");
        this.setTitle(title);
        ImageView btnStartDial=(ImageView)findViewById(R.id.btn_start_dial);
        btnStartDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editPhoneNum=(EditText)findViewById(R.id.edit_phone_num);
                String phoneNum=editPhoneNum.getText().toString();
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
                startActivity(intent);
            }
        });
    }
}
