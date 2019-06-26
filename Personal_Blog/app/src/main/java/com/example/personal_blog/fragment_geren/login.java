package com.example.personal_blog.fragment_geren;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personal_blog.MainActivity;
import com.example.personal_blog.R;

import java.util.List;

public class login extends AppCompatActivity {
    static String aaa="sfddsfds";
    private EditText login_number,login_paswd3;
    private Button queren ;
    private user_database db1;
    private SQLiteDatabase sqLiteDatabase;
    private final String action="MyBroadcast";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db1=new user_database(login.this);
       sqLiteDatabase=db1.getReadableDatabase();
        login_number=(EditText)findViewById(R.id.login_number);
        login_paswd3=(EditText)findViewById(R.id.login_paswd3);
        queren=(Button)findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =login_number.getText().toString().trim();
               // String aaa="djfkjlsdjfkldsjkf";
                String password=login_paswd3.getText().toString().trim();
               if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    List<userInfo> data = db1.querydata(sqLiteDatabase);
                    boolean match = false;
                   for(int i=0;i<data.size();i++) {
                        userInfo user = data.get(i);
                       if (name.equals(user.getUsername()) && password.equals(user.getPaswd())){
                            match = true;
                            break;
                        }else{
                            match = false;
                        }
                    }
                    if (match) {
                       Intent intent = new Intent(login.this, MainActivity.class);
                        intent.setAction(action);
                        login.this.sendBroadcast(intent);
                        Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                      finish();//销毁此Activity
                    }else {
                        Toast.makeText(login.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
