package com.example.personal_blog.fragment_geren;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personal_blog.R;

import java.util.List;

public class update_Info extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private EditText editText2;
    private TextView textView;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__info);
        button=(Button)findViewById(R.id.update_queren);
        editText=(EditText)findViewById(R.id.gerenjieshao);
        editText2=(EditText)findViewById(R.id.login_number) ;
        textView=(TextView)findViewById(R.id.textview1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_database db=new user_database(update_Info.this);
                String intro7=editText.getText().toString();
               // String username=editText2.getText().toString();
                sqLiteDatabase=db.getWritableDatabase();
              List<userInfo> data = db.querydata(sqLiteDatabase);
               int i=data.size()-1;
                   userInfo user1 = data.get(i);
                  int id=user1.getId();
                 /// int age=user1.getAge();
                String pas=user1.getPaswd();

                db.update_intro(sqLiteDatabase,id,intro7);
                //sqLiteDatabase.execSQL("update user set age=? where id=?",new Object[]{12, id});
                 String iii=user1.getIntro();

              // textView.setText();

            }
        });
    }
}
