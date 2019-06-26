package com.example.personal_blog.fragment_geren;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personal_blog.R;

public class register extends AppCompatActivity {
Button yes;
private String realCode;
private EditText login_phonenumber,login_paswd,age,login_paswd1,phoneCode;
private ImageView showCode;
private Spinner spinner;
private String select_sex="男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        showCode=(ImageView)findViewById(R.id.showCode);
        login_paswd1=(EditText)findViewById(R.id.login_paswd2);
        phoneCode=(EditText)findViewById(R.id.phoneCode);
        //将验证码用图片的形式显示出来
        showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        yes=(Button)findViewById(R.id.yes);
        login_phonenumber=(EditText)findViewById(R.id.login_phonenumber);
        login_paswd=(EditText)findViewById(R.id.login_paswd);
        age=(EditText)findViewById(R.id.age);
        spinner=(Spinner)findViewById(R.id.sex1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //获取选择的值
                select_sex=register.this.getResources().getStringArray(R.array.sex)[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=login_phonenumber.getText().toString();
                String password=login_paswd.getText().toString();
                String password2=login_paswd1.getText().toString();
                String phoneCodes=phoneCode.getText().toString();
                int age1= Integer.parseInt(age.getText().toString());
                String intro="kkkk";
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCodes) ) {
                    if (phoneCodes.equals(realCode)) {
                user_database db=new user_database(register.this);
                SQLiteDatabase sqLiteDatabase=db.getWritableDatabase();
                db.adddata(sqLiteDatabase,username,password,select_sex,age1,intro);
                Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(register.this,login.class);
               startActivity(intent);}
               else {
                            Toast.makeText(register.this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(register.this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
