package com.example.personal_blog.fragment_geren;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personal_blog.R;

import java.util.List;

public class personal_homepage extends AppCompatActivity {
    private EditText yhm,xiugaimima1,xiugaimima2,age;
    private Button btn;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_homepage);
        yhm=(EditText)findViewById(R.id.yhm);
        xiugaimima1=(EditText)findViewById(R.id.xiugai_mima);
        xiugaimima2=(EditText)findViewById(R.id.xiugaimima2);
        age=(EditText)findViewById(R.id.xiugai_age);
        btn=(Button)findViewById(R.id.queren_xiugai);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_database db=new user_database(personal_homepage.this);
                // String username=editText2.getText().toString();
                sqLiteDatabase=db.getWritableDatabase();
                String name=yhm.getText().toString();
                String nima=xiugaimima1.getText().toString();
                String nima2=xiugaimima2.getText().toString();
                int age1= Integer.parseInt(age.getText().toString());
                List<userInfo> data = db.querydata(sqLiteDatabase);
                for(int i=0;i<data.size();i++)
                {
                    userInfo user = data.get(i);
                    if(name.equals(user.getUsername()))
                    {
                        int id= user.getId();
                        db.update_Info(sqLiteDatabase,id,nima,age1);
                        Toast.makeText(personal_homepage.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }


            }
        });





    }


}
